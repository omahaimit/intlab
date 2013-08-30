package com.zju.dao.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.zju.dao.PatientsDao;
import com.zju.model.Patients;

public class PatientsDaoSpring implements PatientsDao {

	private JdbcTemplate jdbcTemplatez1;

	@Autowired
	public void setJdbcTemplatez1(JdbcTemplate jdbcTemplatez1) {
		this.jdbcTemplatez1 = jdbcTemplatez1;
	}

	public List<Patients> findBySection(String section) {
		return jdbcTemplatez1.query("select * from l_patientinfo where section='" + section + "'", rowMapper);
	}

	@Override
	public Patients get(long id) {
		return jdbcTemplatez1.queryForObject("select * from l_patientinfo where doctadviseno=" + id, rowMapper);
	}

	@Override
	public boolean exists(long id) {
		int count = jdbcTemplatez1.queryForInt("select count(*) from l_patientinfo where doctadviseno=" + id);
		return count != 0;
	}

	private RowMapper<Patients> rowMapper = new RowMapper<Patients>() {

		@Override
		public Patients mapRow(ResultSet rs, int rowNum) throws SQLException {
			Patients info = new Patients();
			info.setBarCode(rs.getLong("DOCTADVISENO"));
			info.setSection(rs.getString("SECTION"));
			info.setLabDepartment(rs.getString("LABDEPARTMENT"));
			info.setResult(rs.getInt("RESULTSTATUS"));
			info.setPatientName(rs.getString("PATIENTNAME"));
			info.setSex(rs.getInt("SEX"));
			info.setBirth(new Date(rs.getTimestamp("BIRTHDAY").getTime()));
			info.setExaminaim(rs.getString("EXAMINAIM"));
			info.setReceivePerson(rs.getString("EXECUTOR"));
			info.setReceiveTime(new Date(rs.getTimestamp("EXECUTETIME").getTime()));
			info.setSampleNo(rs.getString("SAMPLENO"));
			info.setSampleType(rs.getString("SAMPLETYPE").charAt(0));
			info.setTreatmentType(rs.getString("STAYHOSPITALMODE"));
			return info;
		}
	};
}