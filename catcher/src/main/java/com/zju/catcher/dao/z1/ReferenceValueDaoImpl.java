package com.zju.catcher.dao.z1;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.zju.catcher.entity.z1.ReferenceValue;

@Repository
public class ReferenceValueDaoImpl implements ReferenceValueDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<ReferenceValue> getAll() {
		String sql = "select * from l_reference";
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	public List<String> getIdList() {
		String sql = "select distinct testid from l_reference order by testid";
		return jdbcTemplate.queryForList(sql, String.class);
	}

	protected RowMapper<ReferenceValue> rowMapper = new RowMapper<ReferenceValue>() {

        public ReferenceValue mapRow(ResultSet rs, int rowNum) throws SQLException {

        	ReferenceValue refValue = new ReferenceValue();
            // 反射构建PatientInfo实体
            Field[] fields = refValue.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String type = field.getType().getName();

                try {
                    if (type.equals(String.class.getName())) {
                        field.set(refValue, rs.getString(name));
                    } else if (type.equals(int.class.getName())) {
                        field.setInt(refValue, rs.getInt(name));
                    } else if (type.equals(Date.class.getName())) {
                        field.set(refValue, new java.util.Date(rs.getTimestamp(name).getTime()));
                    } else if (type.equals(long.class.getName())) {
                        field.setLong(refValue, rs.getLong(name));
                    } else if (type.equals(char.class.getName())) {
                        String sampleType = rs.getString(name);
                        if (sampleType != null && sampleType.length() > 0)
                            field.setChar(refValue, sampleType.charAt(0));
                    } else {
                        field.set(refValue, rs.getObject(name));
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }
                field.setAccessible(false);
            }
            return refValue;
        }
    };
}
