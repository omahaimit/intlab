package com.zju.catcher.dao.local;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientInfo;

@Repository
public class PatientSampleDaoImpl implements PatientSampleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static Log log = LogFactory.getLog(PatientSampleDaoImpl.class);

    private void setValue(PreparedStatement ps, PatientInfo info) {

        Field[] fields = PatientInfo.class.getDeclaredFields();
        int k = 0;
        for (int i = 0; i < fields.length; i++) {

            String type = fields[i].getType().getName();
            k = (i == 0) ? fields.length : i;

            try {
                fields[i].setAccessible(true);
                if (type.equals(String.class.getName())) {
                    ps.setString(k, (String) fields[i].get(info));
                } else if (type.equals(int.class.getName())) {
                    ps.setInt(k, fields[i].getInt(info));
                } else if (type.equals(Date.class.getName())) {
                    if (fields[i].get(info) != null) {
                        Date d = (Date) fields[i].get(info);
                        ps.setTimestamp(k, new java.sql.Timestamp(d.getTime()));
                    } else {
                        ps.setNull(k, Types.TIMESTAMP);
                    }
                } else if (type.equals(long.class.getName())) {
                    ps.setLong(k, fields[i].getLong(info));
                } else if (type.equals(char.class.getName())) {
                    char c = fields[i].getChar(info);
                    ps.setString(k, String.valueOf(c));
                } else {
                    ps.setObject(k, fields[i].get(info));
                }
                fields[i].setAccessible(false);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fields[i].setAccessible(false);
            }
        }
    }

    private String buildInsertSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("insert into l_patientinfo(");
        Field[] fields = PatientInfo.class.getDeclaredFields();

        for (int i = 1; i < fields.length; i++) {
            builder.append(fields[i].getName());
            builder.append(',');
        }

        builder.append("DOCTADVISENO,AUDITSTATUS) values (");
        for (int i = 1; i < fields.length; i++) {
            builder.append("?,");
        }
        builder.append("?,?)");
        return builder.toString();
    }

    @SuppressWarnings("unused")
	private String buildUpdateSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("update l_patientinfo set ");
        Field[] fields = PatientInfo.class.getDeclaredFields();

        for (int i = 1; i < fields.length; i++) {
            builder.append(fields[i].getName());
            builder.append("=?");
            if (i + 1 < fields.length) {
                builder.append(',');
            }
        }
        builder.append(" where DOCTADVISENO=?");
        return builder.toString();
    }

    public void insert(final List<PatientSample> patientInfos) {
        // System.out.println(buildInsertSql());
        if (patientInfos == null || patientInfos.size() == 0)
            return;

        log.info("INSERT : " + patientInfos.size());
       
        String insertSql = buildInsertSql();
        //String insertSql = "insert into l_patientinfo(DOCTADVISENO,REQUESTTIME,REQUESTER,EXECUTETIME,EXECUTOR,RECEIVETIME,RECEIVER,STAYHOSPITALMODE,PATIENTID,RESULTSTATUS,INFANTFLAG,SECTION,DEPART_BED,PATIENTNAME,SEX,BIRTHDAY,DIAGNOSTIC,SAMPLETYPE,EXAMINAIM,SAMPLENO,CHECKOPERATOR,CHECKEROPINION,CHECKTIME,LABDEPARTMENT,ISPRINT,CHKOPER2,PRINTTIME) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        // 批量insert
        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {

            // DOCTADVISENO,REQUESTTIME,REQUESTER,EXECUTETIME,EXECUTOR,RECEIVETIME,RECEIVER,STAYHOSPITALMODE,PATIENTID,RESULTSTATUS,INFANTFLAG,SECTION,DEPART_BED,PATIENTNAME,SEX,BIRTHDAY,DIAGNOSTIC,SAMPLETYPE,EXAMINAIM,SAMPLENO,CHECKOPERATOR,CHECKEROPINION,CHECKTIME,LABDEPARTMENT,ISPRINT,CHKOPER2,PRINTTIME
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                setValue(ps, patientInfos.get(i).getPatientInfo());
                Field[] fields = PatientInfo.class.getDeclaredFields();
                ps.setInt(fields.length + 1, patientInfos.get(i).getAuditStatus());
            }

            public int getBatchSize() {
                return patientInfos.size();
            }

        });

        log.info("Finished INSERT!");
    }

    public void update(final List<PatientInfo> patientInfos) {
    	
        //System.out.println(buildUpdateSql());
        if (patientInfos == null || patientInfos.size() == 0)
            return;
        log.info("UPDATE : " + patientInfos.size());
        /*String updateSql = buildUpdateSql();
        // 批量update
        jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {

            // DOCTADVISENO,REQUESTTIME,REQUESTER,EXECUTETIME,EXECUTOR,RECEIVETIME,RECEIVER,STAYHOSPITALMODE,PATIENTID,RESULTSTATUS,INFANTFLAG,SECTION,DEPART_BED,PATIENTNAME,SEX,BIRTHDAY,DIAGNOSTIC,SAMPLETYPE,EXAMINAIM,SAMPLENO,CHECKOPERATOR,CHECKEROPINION,CHECKTIME,LABDEPARTMENT,ISPRINT,CHKOPER2,PRINTTIME
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                setValue(ps, patientInfos.get(i));
            }

            public int getBatchSize() {
                return patientInfos.size();
            }

        });*/
		String updateSql = "update l_patientinfo set "
				+ "REQUESTTIME=?,REQUESTER=?,EXECUTETIME=?,EXECUTOR=?,RECEIVETIME=?,RECEIVER=?,"
				+ "PATIENTID=?,RESULTSTATUS=?,SECTION=?,BIRTHDAY=?,DIAGNOSTIC=?,"
				+ "EXAMINAIM=?,SAMPLENO=?,CHECKTIME=?,LABDEPARTMENT=?,"
				+ "YLXH=?,BLH=?,CYCLE=?" + " where DOCTADVISENO=?";
		jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
            	PatientInfo info = patientInfos.get(i);
            	if (info.getREQUESTTIME() != null) {
                    ps.setTimestamp(1, new java.sql.Timestamp(info.getREQUESTTIME().getTime()));
                } else {
                    ps.setNull(1, Types.TIMESTAMP);
                }
            	ps.setString(2, info.getREQUESTER());
            	if (info.getEXECUTETIME() != null) {
                    ps.setTimestamp(3, new java.sql.Timestamp(info.getEXECUTETIME().getTime()));
                } else {
                    ps.setNull(3, Types.TIMESTAMP);
                }
            	ps.setString(4, info.getEXECUTOR());
            	if (info.getRECEIVETIME() != null) {
                    ps.setTimestamp(5, new java.sql.Timestamp(info.getRECEIVETIME().getTime()));
                } else {
                    ps.setNull(5, Types.TIMESTAMP);
                }
            	ps.setString(6, info.getRECEIVER());
            	//
            	ps.setString(7, info.getPATIENTID());
            	ps.setInt(8, info.getRESULTSTATUS());
            	ps.setString(9, info.getSECTION());
            	if (info.getBIRTHDAY() != null) {
                    ps.setTimestamp(10, new java.sql.Timestamp(info.getBIRTHDAY().getTime()));
                } else {
                    ps.setNull(10, Types.TIMESTAMP);
                }
            	ps.setString(11, info.getDIAGNOSTIC());
            	//
            	ps.setString(12, info.getEXAMINAIM());
            	ps.setString(13, info.getSAMPLENO());
            	if (info.getCHECKTIME() != null) {
                    ps.setTimestamp(14, new java.sql.Timestamp(info.getCHECKTIME().getTime()));
                } else {
                    ps.setNull(14, Types.TIMESTAMP);
                }
            	ps.setString(15, info.getLABDEPARTMENT());
            	//
            	ps.setString(16, info.getYLXH());
            	ps.setString(17, info.getBLH());
            	ps.setInt(18, info.getCYCLE());
            	ps.setLong(19, info.getDOCTADVISENO());
            }

            public int getBatchSize() {
                return patientInfos.size();
            }

        });
		log.info("Finished Update!");
    }
    
    public void updateWithStatus(final List<PatientSample> patientInfos) {
    	if (patientInfos == null || patientInfos.size() == 0)
            return;
        log.info("UPDATE Status : " + patientInfos.size());
        String updateSql = "Update l_patientinfo set AuditStatus=? where DOCTADVISENO=?";
        // 批量update
        jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {

            // DOCTADVISENO,REQUESTTIME,REQUESTER,EXECUTETIME,EXECUTOR,RECEIVETIME,RECEIVER,STAYHOSPITALMODE,PATIENTID,RESULTSTATUS,INFANTFLAG,SECTION,DEPART_BED,PATIENTNAME,SEX,BIRTHDAY,DIAGNOSTIC,SAMPLETYPE,EXAMINAIM,SAMPLENO,CHECKOPERATOR,CHECKEROPINION,CHECKTIME,LABDEPARTMENT,ISPRINT,CHKOPER2,PRINTTIME
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, patientInfos.get(i).getAuditStatus());
                ps.setLong(2, patientInfos.get(i).getPatientInfo().getDOCTADVISENO());
            }

            public int getBatchSize() {
                return patientInfos.size();
            }

        });
        log.info("Finished Update!");
	}
    

    public List<Long> getPatientId(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strDate = df.format(date);
        String sql = "select doctadviseno from l_patientinfo where sampleno like '" + strDate + "%'";
        return jdbcTemplate.queryForList(sql, Long.class);
    }

	public List<PatientSample> getPatientInfo(String sample, String lib, int status, String operator) {
		String sql = "select * from l_patientinfo where sampleno like '"
				+ sample + "%' and labdepartment='" + lib + "'";
		if (status == 5) {
			sql += " and writeback=1";
		} else {
			sql += " and AuditStatus=" + status;
		}
		if (!StringUtils.isEmpty(operator)) {
			sql += " and checkoperator='" + operator + "'";
		}
		return jdbcTemplate.query(sql, rowMapper2);
		
	}
	
	public List<Long> getPatientId(Date date, int status) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strDate = df.format(date);
		String sql = "select doctadviseno from l_patientinfo where sampleno like '"
				+ strDate + "%' and AuditStatus=" + status;
		return jdbcTemplate.queryForList(sql, Long.class);
	}
	
	protected RowMapper<PatientInfo> rowMapper = new RowMapper<PatientInfo>() {

        public PatientInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

            PatientInfo info = new PatientInfo();
            // 反射构建PatientInfo实体
            Field[] fields = info.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String type = field.getType().getName();

                try {
                    if (type.equals(String.class.getName())) {
                        field.set(info, rs.getString(name));
                    } else if (type.equals(int.class.getName())) {
                        field.setInt(info, rs.getInt(name));
                    } else if (type.equals(Date.class.getName())) {
                        field.set(info, new java.util.Date(rs.getTimestamp(name).getTime()));
                    } else if (type.equals(long.class.getName())) {
                        field.setLong(info, rs.getLong(name));
                    } else if (type.equals(char.class.getName())) {
                        String sampleType = rs.getString(name);
                        if (sampleType != null && sampleType.length() > 0)
                            field.setChar(info, sampleType.charAt(0));
                    } else {
                        field.set(info, rs.getObject(name));
                    }
                /*} catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();*/
                } catch (Exception e) {
                }
                field.setAccessible(false);
            }
            return info;
        }
    };
    
    protected RowMapper<PatientSample> rowMapper2 = new RowMapper<PatientSample>() {

        public PatientSample mapRow(ResultSet rs, int rowNum) throws SQLException {

            PatientInfo info = new PatientInfo();
            // 反射构建PatientInfo实体
            Field[] fields = info.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String type = field.getType().getName();

                try {
                    if (type.equals(String.class.getName())) {
                        field.set(info, rs.getString(name));
                    } else if (type.equals(int.class.getName())) {
                        field.setInt(info, rs.getInt(name));
                    } else if (type.equals(Date.class.getName())) {
                        field.set(info, new java.util.Date(rs.getTimestamp(name).getTime()));
                    } else if (type.equals(long.class.getName())) {
                        field.setLong(info, rs.getLong(name));
                    } else if (type.equals(char.class.getName())) {
                        String sampleType = rs.getString(name);
                        if (sampleType != null && sampleType.length() > 0)
                            field.setChar(info, sampleType.charAt(0));
                    } else {
                        field.set(info, rs.getObject(name));
                    }
                } catch (Exception e) {}
                field.setAccessible(false);
            }
            PatientSample sample = new PatientSample();
            sample.setPatientInfo(info);
            sample.setAuditStatus(rs.getInt("AUDITSTATUS"));
            sample.setAuditMark(rs.getInt("AUDITMARK"));
            sample.setWriteBack(rs.getInt("WRITEBACK"));
            return sample;
        }
    };

	public PatientSample getPatientInfo(String sampleNo) {
		String sql = "select * from l_patientinfo where sampleno='" + sampleNo + "'";
		return jdbcTemplate.queryForObject(sql, rowMapper2);
	}

	public void updateEditPatientInfo(final List<String> sampleNoList) {
		
		String updateSql = "Update l_patientinfo set AuditStatus=0,AuditMark=0 where sampleNo=? and AuditStatus=2";
        // 批量update
        jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, sampleNoList.get(i));
            }

            public int getBatchSize() {
                return sampleNoList.size();
            }

        });
	}
	
	public List<String> exsitSampleList(List<PatientInfo> sampleNoList) {
		if (sampleNoList == null || sampleNoList.size() == 0) {
			return new ArrayList<String>();
		} else {
			StringBuilder builder = new StringBuilder();
			for (PatientInfo info : sampleNoList) {
				builder.append("'");
				builder.append(info.getSAMPLENO());
				builder.append("',");
			}
			String insql = builder.substring(0, builder.length() - 1);
			return jdbcTemplate.queryForList("select sampleno from l_patientinfo where sampleno in (" + insql + ")", String.class);
		}
	}
}
