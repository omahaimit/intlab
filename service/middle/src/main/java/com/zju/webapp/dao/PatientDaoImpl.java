package com.zju.webapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zju.webapp.model.Patient;

public class PatientDaoImpl implements PatientDao {

	private JdbcTemplate jdbcTemplate;
	private JdbcTemplate jdbcTemplateZ1;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Autowired
	public void setJdbcTemplateZ1(JdbcTemplate jdbcTemplateZ1) {
		this.jdbcTemplateZ1 = jdbcTemplateZ1;
	}
	
	@Override
	public List<Patient> getPatients(String inSql) {
		
		if ("()".equals(inSql)) {
			return new ArrayList<Patient>();
		}
		String sql = "SELECT JZKH,JZCS,XM,XB,CSRQ,SFZH,LXDH,LDRQ,YBKH,BAH FROM GY_BRJBXXK WHERE JZKH in " + inSql;
		return jdbcTemplateZ1.query(sql, new RowMapper<Patient>() {

			@Override
			public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Patient patient = new Patient();
				patient.setJZKH(rs.getString("JZKH"));
				patient.setJZCS(rs.getInt("JZCS"));
				patient.setXM(rs.getString("XM"));
				patient.setXB(rs.getInt("XB"));
				patient.setCSRQ(rs.getDate("CSRQ"));
				patient.setSFZH(rs.getString("SFZH"));
				patient.setLXDH(rs.getString("LXDH"));
				patient.setLDRQ(rs.getTimestamp("LDRQ"));
				patient.setYBKH(rs.getString("YBKH"));
				patient.setBAH(rs.getString("BAH"));
				return patient;
			}
			
		});
	}

	@Override
	public List<String> exsitPatients(String inSql) {
		
		if ("()".equals(inSql)) {
			return new ArrayList<String>();
		}
		
		String sql = "SELECT JZKH FROM l_patient WHERE JZKH in " + inSql;
		return jdbcTemplate.queryForList(sql, String.class);
	}

	@Override
	public void batchInsert(final List<Patient> patients) {
		
		String sql = "INSERT INTO l_patient values(?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Patient p = patients.get(i);
				ps.setString(1, p.getJZKH());
				ps.setInt(2, p.getJZCS());
				ps.setString(3, p.getXM());
				ps.setInt(4, p.getXB());
				try {
					ps.setTimestamp(5, new java.sql.Timestamp(p.getCSRQ().getTime()));
				} catch (Exception e) {
					ps.setNull(5, Types.TIMESTAMP);
				}
				ps.setString(6, p.getSFZH());
				ps.setString(7, p.getLXDH());
				try {
					ps.setTimestamp(8, new java.sql.Timestamp(p.getLDRQ().getTime()));
				} catch (Exception e) {
					ps.setNull(5, Types.TIMESTAMP);
				}
				ps.setString(9, p.getYBKH());
				ps.setString(10, p.getBAH());
			}
			
			@Override
			public int getBatchSize() {
				return patients.size();
			}
		});
	}

}
