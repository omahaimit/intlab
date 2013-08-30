package com.zju.dao.spring;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zju.dao.BayesDao;
import com.zju.model.Distribute;

public class BayesJdbc implements BayesDao {

	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Distribute> getDistribute(String testId) {
		String sql = "select * from RESULTDISTRIBUTE where TESTID='" + testId + "'";
		return jdbcTemplate.query(sql, new RowMapper<Distribute>() {

			@Override
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

	@Override
	public void update(final List<Distribute> disList) {
		String sql = "update RESULTDISTRIBUTE set PASSCOUNT=?,UNPASSCOUNT=? where TESTID=? and SCOPENO=?";
		
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Distribute dis = disList.get(i);
				ps.setInt(1, dis.getPASSCOUNT());
				ps.setInt(2, dis.getUNPASSCOUNT());
				ps.setString(3, dis.getTESTID());
				ps.setInt(4, dis.getSCOPENO());
			}
			
			public int getBatchSize() {
				return disList.size();
			}
		});
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
