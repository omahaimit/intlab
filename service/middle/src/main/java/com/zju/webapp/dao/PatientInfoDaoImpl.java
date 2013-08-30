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

import com.zju.webapp.model.PatientInfo;
import com.zju.webapp.model.PatientInfoWithResult;
import com.zju.webapp.model.TestResult;

public class PatientInfoDaoImpl implements PatientInfoDao {

	private JdbcTemplate jdbcTemplate;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat pdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<PatientInfo> getPatientInfoFromZ1(Date date) {
		
		String strDate = sdf.format(date);
		String sql = "SELECT DOCTADVISENO,RECEIVETIME,STAYHOSPITALMODE,PATIENTID,PATIENTNAME,DIAGNOSTIC,SAMPLETYPE,EXAMINAIM,SAMPLENO,RESULTSTATUS,RULEIDS,ISPRINT,BLH FROM l_patientinfo WHERE " 
				+ "cloudmark=0 and resultstatus>=5 and sampleno like '" + strDate + "%'";
		//String sql = "SELECT DOCTADVISENO,RECEIVETIME,STAYHOSPITALMODE,PATIENTID,PATIENTNAME,DIAGNOSTIC,SAMPLETYPE,EXAMINAIM,SAMPLENO,RESULTSTATUS,RULEIDS,ISPRINT,BLH FROM l_patientinfo WHERE " 
		//		+ "sampleno like '" + strDate + "CBC00%'";
		return jdbcTemplate.query(sql, patientRowMapper);  
	}
	
	public List<PatientInfoWithResult> getPatientInfoWithResultByMeasureTime(Date date) {
		
		String strDate = sdf.format(date);
		String preStrDate = pdf.format(date);
		String sql = "SELECT p.DOCTADVISENO,p.RECEIVETIME,p.STAYHOSPITALMODE,p.PATIENTID,p.PATIENTNAME,p.DIAGNOSTIC,p.SAMPLETYPE,p.EXAMINAIM,p.SAMPLENO,p.RESULTSTATUS,p.RULEIDS,p.ISPRINT,p.BLH,t.*" 
				+ " FROM l_patientinfo p left join l_testresult t on p.sampleno=t.sampleno WHERE " 
				+ "p.cloudmark=0 and p.resultstatus>=5 and t.measuretime between to_date(?,?) and to_date(?,?) and p.sampleno not like '" + strDate + "%'";
		Object[] args = new Object[] { preStrDate + " 00:00:00", DATEFORMAT, preStrDate + " 23:59:59", DATEFORMAT };
		return jdbcTemplate.query(sql, args, rowMapper);
	}
	
	@Override
	public List<PatientInfo> getPatientInfoByMeasureTime(Date date) {
		
		String strDate = sdf.format(date);
		String preStrDate = pdf.format(date);
		String sql = "SELECT DISTINCT p.DOCTADVISENO,p.RECEIVETIME,p.STAYHOSPITALMODE,p.PATIENTID,p.PATIENTNAME,p.DIAGNOSTIC,p.SAMPLETYPE,p.EXAMINAIM,p.SAMPLENO,p.RESULTSTATUS,p.RULEIDS,p.ISPRINT,p.BLH" 
				+ " FROM l_patientinfo p left join l_testresult t on p.sampleno=t.sampleno WHERE " 
				+ "p.cloudmark=0 and p.resultstatus>=5 and t.measuretime between to_date(?,?) and to_date(?,?) and p.sampleno not like '" + strDate + "%'";
		Object[] args = new Object[] { preStrDate + " 00:00:00", DATEFORMAT, preStrDate + " 23:59:59", DATEFORMAT };
		return jdbcTemplate.query(sql, args, patientRowMapper);
	}
	
	public void updateClodMark(final List<PatientInfo> infos) {
		
		String sql = "UPDATE l_patientinfo SET cloudmark=1 WHERE DOCTADVISENO=?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, infos.get(i).getId());
			}
			
			@Override
			public int getBatchSize() {
				return infos.size();
			}
		});
	}
	
	private RowMapper<PatientInfoWithResult> rowMapper = new RowMapper<PatientInfoWithResult>(){

		@Override
		public PatientInfoWithResult mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			PatientInfo info = new PatientInfo();
			info.setId(rs.getLong("DOCTADVISENO"));
			info.setSampleNo(rs.getString("SAMPLENO"));
			info.setResultStatus(rs.getInt("RESULTSTATUS"));
			try {
				info.setStayHospitalMode(rs.getInt("STAYHOSPITALMODE"));
				info.setPatientId(rs.getString("PATIENTID"));
				info.setPatientName(rs.getString("PATIENTNAME"));
				info.setDiagnostic(rs.getString("DIAGNOSTIC"));
				info.setExaminaim(rs.getString("EXAMINAIM"));
				info.setRuleIds(rs.getString("RULEIDS"));
				info.setPrintFlag(rs.getInt("ISPRINT"));
				info.setBlh(rs.getString("BLH"));
				info.setSampleType(rs.getString("SAMPLETYPE").charAt(0));
				info.setReceivetime(new java.util.Date(rs.getTimestamp(
						"RECEIVETIME").getTime()));
			} catch (Exception e) {
			}
			
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
			
			PatientInfoWithResult infoWithResult = new PatientInfoWithResult();
			infoWithResult.setPatientInfo(info);
			infoWithResult.setTestResult(result);
			return infoWithResult;
		}
		
	};
	
	private RowMapper<PatientInfo> patientRowMapper = new RowMapper<PatientInfo>(){

		@Override
		public PatientInfo mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			PatientInfo info = new PatientInfo();
			info.setId(rs.getLong("DOCTADVISENO"));
			info.setSampleNo(rs.getString("SAMPLENO"));
			info.setResultStatus(rs.getInt("RESULTSTATUS"));
			try {
				info.setStayHospitalMode(rs.getInt("STAYHOSPITALMODE"));
				info.setPatientId(rs.getString("PATIENTID"));
				info.setPatientName(rs.getString("PATIENTNAME"));
				info.setDiagnostic(rs.getString("DIAGNOSTIC"));
				info.setExaminaim(rs.getString("EXAMINAIM"));
				info.setRuleIds(rs.getString("RULEIDS"));
				info.setPrintFlag(rs.getInt("ISPRINT"));
				info.setBlh(rs.getString("BLH"));
				info.setSampleType(rs.getString("SAMPLETYPE").charAt(0));
				info.setReceivetime(rs.getTimestamp("RECEIVETIME"));
			} catch (Exception e) {
			}
			return info;
		}	
	};
}
