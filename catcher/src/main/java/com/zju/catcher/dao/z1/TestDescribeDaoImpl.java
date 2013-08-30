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

import com.zju.catcher.entity.z1.TestDescribe;

@Repository
public class TestDescribeDaoImpl implements TestDescribeDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
    
    protected RowMapper<TestDescribe> rowMapper = new RowMapper<TestDescribe>() {
    	
        public TestDescribe mapRow(ResultSet rs, int rowNum) throws SQLException {
        	TestDescribe result = new TestDescribe();
            // 反射构建PatientInfo实体
            Field[] fields = result.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String type = field.getType().getName();
                try {
                    if (type.equals(String.class.getName())) {
                        field.set(result, rs.getString(name));
                    } else if (type.equals(int.class.getName())) {
                        field.setInt(result, rs.getInt(name));
                    } else if (type.equals(Date.class.getName())) {
                        field.set(result, new java.util.Date(rs.getTimestamp(name).getTime()));
                    } else if (type.equals(char.class.getName())) {
                        String sampleType = rs.getString(name);
                        if (sampleType != null && sampleType.length() > 0)
                            field.setChar(result, sampleType.charAt(0));
                    } else {
                        field.set(result, rs.getObject(name));
                    }
               /* } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();*/
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                field.setAccessible(false);
            }
            return result;
        }
    };
    
	public List<TestDescribe> getAll() {
		String sql = "select TESTID, CHINESENAME, SAMPLETYPE, UNIT, PRINTORD, YLXH, ISPRINT, WARNLO1, WARNHI1, WARNLO2, WARNHI2, WARNLO3, WARNHI3 from l_testdescribe";
		return jdbcTemplate.query(sql, rowMapper);
	}

}
