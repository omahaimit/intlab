package com.zju.webapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zju.webapp.model.TestDescribe;
import com.zju.webapp.model.TestResult;

public class TestResultDaoImpl implements TestResultDao {

	private JdbcTemplate jdbcTemplate;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat pdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<TestResult> getTestResultFromZ1(Date date) {
		
		String strDate = sdf.format(date);
		String sql = "SELECT * FROM l_testresult WHERE sampleno like '" + strDate + "%' and teststatus>=5 and cloudmark=0 and rownum<10000";
		//String sql = "SELECT * FROM l_testresult WHERE sampleno like '" + strDate + "CBC00%'";
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	@Override
	public List<TestResult> getTestResultByMeasureTime(Date date) {

		String strDate = sdf.format(date);
		String preStrDate = pdf.format(date);
		String sql = "SELECT t.* FROM l_patientinfo p left join l_testresult t on p.sampleno=t.sampleno WHERE " 
				+ "p.cloudmark=0 and p.resultstatus>=5 and t.measuretime between to_date(?,?) and to_date(?,?) and p.sampleno not like '" + strDate + "%'";
		Object[] args = new Object[] { preStrDate + " 00:00:00", DATEFORMAT, preStrDate + " 23:59:59", DATEFORMAT };
		return jdbcTemplate.query(sql, args, rowMapper);
	}

	private RowMapper<TestResult> rowMapper = new RowMapper<TestResult>(){

		@Override
		public TestResult mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			TestResult result = new TestResult();
			result.setSampleNo(rs.getString("SAMPLENO"));
			result.setTestId(rs.getString("TESTID"));
			result.setTestResult(rs.getString("TESTRESULT"));
			result.setTestStatus(rs.getInt("TESTSTATUS"));
			try {
				result.setResultFlag(rs.getString("RESULTFLAG"));
				result.setRefLo(rs.getString("REFLO"));
				result.setRefHi(rs.getString("REFHI"));
				result.setUnit(rs.getString("UNIT"));
				result.setMeasureTime(rs.getTimestamp("MEASURETIME"));
				result.setSampleType(rs.getString("SAMPLETYPE").charAt(0));
				
			} catch (Exception e) {
			}
			return result;
		}
		
	};

	@Override
	public void updateClodMark(final List<TestResult> results) {

		String sql = "UPDATE l_testresult SET cloudmark=1 WHERE sampleno=? and testid=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, results.get(i).getSampleNo());
				ps.setString(2, results.get(i).getTestId());
			}
			
			@Override
			public int getBatchSize() {
				return results.size();
			}
		});
	}

	@Override
	public List<TestDescribe> getAllDescribe() {
		
		String sql = "SELECT TESTID,CHINESENAME FROM l_testdescribe";
		return jdbcTemplate.query(sql, new RowMapper<TestDescribe>(){

			@Override
			public TestDescribe mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				TestDescribe td = new TestDescribe();
				td.setTestId(rs.getString(1));
				td.setName(rs.getString(2));
				return td;
			}});
	}
}
