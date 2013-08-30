package com.zju.dao.spring;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import com.zju.dao.InvalidSamplesDao;
import com.zju.model.InvalidSamples;

public class InvalidSamplesOracleDaoSpring implements InvalidSamplesDao {

	private JdbcTemplate jdbcTemplatez1;

	@Autowired
	public void setJdbcTemplatez1(JdbcTemplate jdbcTemplatez1) {
		this.jdbcTemplatez1 = jdbcTemplatez1;
	}

	public List<InvalidSamples> findByParamter(String username, String fromdate, String todate, String labdepartment) {

		StringBuilder hql = new StringBuilder("select i.* from intlab_samples i, l_patientinfo p"
				+ " where i.doctadviseno=p.doctadviseno and i.reject_time between to_date('" + fromdate
				+ " 00:00:00', 'YYYY-MM-DD HH24:MI:SS') " + "and to_date('" + todate
				+ " 23:59:59', 'YYYY-MM-DD HH24:MI:SS')");

		if (!username.equals("")) {
			hql.append(" and i.reject_person='" + username + "'");
		}
		if (!labdepartment.equals("0000000")) {
			hql.append(" and p.labdepartment='" + labdepartment + "'");
		}
		return jdbcTemplatez1.query(hql.toString(), rowMapper);
	}

	@Override
	public void save(final InvalidSamples sample) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("update intlab_samples set ");
		sqlBuilder
				.append("reject_time=?,container_type=?,label_type=?,requestion_type=?,reject_sample_reason=?,measure_taken=?,notes=?,reject_person=?");
		sqlBuilder.append(" where doctadviseno=?");
		jdbcTemplatez1.update(sqlBuilder.toString(), new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setTimestamp(1, new Timestamp(sample.getRejectTime().getTime()));
				ps.setInt(2, sample.getContainerType());
				ps.setInt(3, sample.getLabelType());
				ps.setInt(4, sample.getRequestionType());
				ps.setInt(5, sample.getRejectSampleReason());
				ps.setInt(6, sample.getMeasureTaken());
				ps.setString(7, sample.getNotes());
				ps.setString(8, sample.getRejectPerson());
				ps.setString(9, sample.getBarCode());
			}

		});
	}

	@Override
	public InvalidSamples get(String barCode) {
		return jdbcTemplatez1.queryForObject("select * from intlab_samples where doctadviseno=" + barCode, rowMapper);
	}

	@Override
	public void remove(String barCode) {
		jdbcTemplatez1.update("delete from intlab_samples where doctadviseno=" + barCode);
	}

	@Override
	public boolean exists(String barCode) {

		int count = jdbcTemplatez1.queryForInt("select count(*) from intlab_samples where doctadviseno=" + barCode);
		return count != 0;
	}

	private RowMapper<InvalidSamples> rowMapper = new RowMapper<InvalidSamples>() {

		@Override
		public InvalidSamples mapRow(ResultSet rs, int rowNum) throws SQLException {
			InvalidSamples sample = new InvalidSamples();
			sample.setBarCode(rs.getString("DOCTADVISENO"));
			sample.setRejectTime(new Date(rs.getTimestamp("reject_time").getTime()));
			sample.setContainerType(rs.getInt("container_type"));
			sample.setLabelType(rs.getInt("label_type"));
			sample.setRequestionType(rs.getInt("requestion_type"));
			sample.setRejectSampleReason(rs.getInt("reject_sample_reason"));
			sample.setMeasureTaken(rs.getInt("measure_taken"));
			sample.setNotes(rs.getString("notes"));
			sample.setRejectPerson(rs.getString("reject_person"));
			return sample;
		}
	};
}
