package com.zju.catcher.dao.local;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.zju.catcher.entity.local.Distribute;
import com.zju.catcher.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplateForZ1;

	public List<String> exsitUserList() {
		return jdbcTemplate.queryForList("select USERNAME from INTLAB_USER", String.class);
	}
	
	public void insertUser(final User user) {
		
		// ªÒ»°ID
		final long id = jdbcTemplate.queryForLong("select max(ID) from INTLAB_USER") + 1;
		final String sql = "insert into INTLAB_USER(ID,USERNAME,FIRST_NAME,LAST_NAME,PASSWORD) values (?,?,?,?,?)";
		jdbcTemplate.update(sql, new PreparedStatementSetter(){

			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, id);
				ps.setString(2, user.getDm());
				ps.setString(3, user.getDm());
				ps.setString(4, user.getName());
				ps.setString(5, user.getPwd());
			}
			
		});
		jdbcTemplate.update("insert into INTLAB_USER_ROLE values(?,?)", id, 3);
	}
	
	public List<User> getAllUser() {
		return jdbcTemplateForZ1.query("select YHDM,XM,BMZ from GY_YHXX", new RowMapper<User>(){

			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setDm(rs.getString("YHDM"));
				user.setName(rs.getString("XM"));
				user.setPwd(rs.getString("BMZ"));
				return user;
			}
			
		});
	}
	
	public List<Distribute> getDistribute(String testId) {
		return jdbcTemplate.query("select * from RESULTDISTRIBUTE where TESTID='"+testId+"'", new RowMapper<Distribute>() {

			public Distribute mapRow(ResultSet rs, int rowNum) throws SQLException {
				Distribute dis = new Distribute();
				dis.setTESTID(rs.getString("TESTID"));
				dis.setSCOPENO(rs.getInt("SCOPENO"));
				dis.setSCOPELO(rs.getFloat("SCOPELO"));
				dis.setSCOPEHI(rs.getFloat("SCOPEHI"));
				dis.setSEX(rs.getInt("SEX"));
				dis.setPASSCOUNT(rs.getInt("PASSCOUNT"));
				dis.setUNPASSCOUNT(rs.getInt("UNPASSCOUNT"));
				return dis;
			}
			
		});
	}
	
	public void updateDistribute(final List<Distribute> list) {
		String sql = "update RESULTDISTRIBUTE set PASSCOUNT=?,UNPASSCOUNT=? where TESTID=? and SCOPENO=?";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Distribute dis = list.get(i);
				ps.setInt(1, dis.getPASSCOUNT());
				ps.setInt(2, dis.getUNPASSCOUNT());
				ps.setString(3, dis.getTESTID());
				ps.setInt(4, dis.getSCOPENO());
			}
			
			public int getBatchSize() {
				return list.size();
			}
		});
	}

	public void saveDistribute(final List<Distribute> list) {
		String sql = "insert into RESULTDISTRIBUTE(TESTID,SCOPENO,SCOPELO,SCOPEHI,SEX) values(?,?,?,?,?)";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Distribute dis = list.get(i);
				ps.setString(1, dis.getTESTID());
				ps.setInt(2, dis.getSCOPENO());
				ps.setFloat(3, getF3(dis.getSCOPELO()));
				ps.setFloat(4, getF3(dis.getSCOPEHI()));
				ps.setInt(5, dis.getSEX());
			}
			
			public int getBatchSize() {
				return list.size();
			}
		});
	}
	
	private final float getF3(float f) {
		return (float) (Math.round(f * 1000)) / 1000;
	}
}
