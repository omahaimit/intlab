package com.zju.catcher.dao.z1;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.PatientInfo;
import com.zju.catcher.entity.z1.TestResult;
import com.zju.catcher.entity.z1.TestResultPK;

@Repository
public class TestResultDaoImpl implements TestResultDao {

    @Autowired
    private JdbcTemplate jdbcTemplateForZ1;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";
    private static Log log = LogFactory.getLog(TestResultDaoImpl.class); 
    
    public List<TestResult> getResult(String preSample) {
    	
	    String sql = "select t.testid, t.testresult, p.sex, p.auditmark from l_testresult t,l_patientinfo p where p.sampleno like '"+preSample+"%' and t.sampleno=p.sampleno and p.auditstatus>0";
	    return jdbcTemplate.query(sql, mapper);

    }
    	
	protected RowMapper<TestResult> mapper = new RowMapper<TestResult>() {

		public TestResult mapRow(ResultSet rs, int rowNum) throws SQLException {
			TestResult tr = new TestResult();
			tr.setTESTID(rs.getString(1));
			tr.setTESTRESULT(rs.getString(2));
			tr.setEDITMARK(rs.getInt(3));
			tr.setTESTSTATUS(rs.getInt(4));
			return tr;
		}
	};
    
    public List<TestResult> getListBetween(Date from, Date to) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "select * from l_testresult where MEASURETIME between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { df.format(from), DATEFORMAT, df.format(to), DATEFORMAT };
        return jdbcTemplateForZ1.query(sql, args, rowMapper);
    }

    protected RowMapper<TestResult> rowMapper = new RowMapper<TestResult>() {
        public TestResult mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestResult result = new TestResult();
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
                /*} catch (IllegalArgumentException e) {
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

    public List<String> getSampleNo(Date from, Date to) {

        log.info("开始吧!");
        long count = jdbcTemplateForZ1.queryForLong("select count(*) from l_patientinfo where doctadviseno=2007873");
        log.info(count);
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "select distinct sampleNo from l_testresult where MEASURETIME between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { df.format(from), DATEFORMAT, df.format(to), DATEFORMAT };
        return jdbcTemplateForZ1.queryForList(sql, String.class, args);
    }

    public List<TestResult> getEditList(Date from, Date to) {
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "select * from l_testresult_edit where EDITTIME between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { df.format(from), DATEFORMAT, df.format(to), DATEFORMAT };
        return jdbcTemplateForZ1.query(sql, args, rowMapper);
    }

    public List<TestResultPK> getTestResult(Date date) {
        
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strDate = df.format(date);
        /*String sql = "select p.doctadviseno,t.* from l_patientinfo p, l_testresult t where p.sampleno=t.sampleno and receivetime between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { strDate + " 00:00:00", DATEFORMAT, strDate + " 23:59:59", DATEFORMAT };
        */
        String sql = "select p.doctadviseno,t.* from l_patientinfo p, l_testresult t where p.sampleno=t.sampleno and p.sampleno like '"
                + strDate + "%'";
        return jdbcTemplateForZ1.query(sql, rowMapper2);
    
    }
    
    protected RowMapper<TestResultPK> rowMapper2 = new RowMapper<TestResultPK>() {
        public TestResultPK mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestResult result = new TestResult();
            TestResultPK PK = new TestResultPK();
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
                /*} catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();*/
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                field.setAccessible(false);
            }
            PK.setTestResult(result);
            PK.setId(rs.getLong("DOCTADVISENO"));
            return PK;
        }
    };

    public List<PatientAndResult> getAll(Date date) {
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] args = new Object[] { df.format(date), DATEFORMAT };
        String sql = "select * from l_patientinfo p, l_testresult t where p.sampleno=t.sampleno and measuretime>to_date(?,?)";
        return jdbcTemplateForZ1.query(sql, args, mergeRowMapper);
    }
    
    protected RowMapper<PatientAndResult> mergeRowMapper = new RowMapper<PatientAndResult>() {

        public PatientAndResult mapRow(ResultSet rs, int rowNum) throws SQLException {

            PatientAndResult merger = new PatientAndResult();
            PatientInfo info = new PatientInfo();
            TestResult test = new TestResult();
            // 反射构建PatientInfo实体
            merger.setPatientInfo((PatientInfo) setField(rs, info));
            merger.setTestResult((TestResult) setField(rs, test));
            return merger;
        }

        private Object setField(ResultSet rs, Object info) {

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
    
	public void updateField(final List<TestResult> resultList) {
		String sql = "update l_testresult set refhi=?,reflo=?,resultflag=?,isprint=?,operator=? where sampleno=? and testid=? and teststatus<6";
		jdbcTemplateForZ1.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, resultList.get(i).getREFHI());
				ps.setString(2, resultList.get(i).getREFLO());
				ps.setString(3, resultList.get(i).getRESULTFLAG());
				ps.setInt(4, resultList.get(i).getISPRINT());
				ps.setString(5, resultList.get(i).getOPERATOR());
				ps.setString(6, resultList.get(i).getSAMPLENO());
				ps.setString(7, resultList.get(i).getTESTID());
			}
			
			public int getBatchSize() {
				return resultList.size();
			}
		});
	}

	public void deleteTestResult(final List<TestResult> resultList) {
		String sql = "delete from l_testresult where SAMPLENO=? and TESTID=? and teststatus<6";
		jdbcTemplateForZ1.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, resultList.get(i).getSAMPLENO());
				ps.setString(2, resultList.get(i).getTESTID());
			}
			
			public int getBatchSize() {
				return resultList.size();
			}
		});
	}

	public void addTestResult(final List<TestResult> resultList) {
		String sql = buildInsertSql();
		jdbcTemplateForZ1.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				setValue(ps, resultList.get(i));
			}
			
			public int getBatchSize() {
				return resultList.size();
			}
		});
	}

	public void editTestResult(final List<TestResult> resultList) {
		String sql = "update l_testresult set testresult=?,operator=?,edittime=? where SAMPLENO=? and TESTID=? and teststatus<6";
		jdbcTemplateForZ1.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, resultList.get(i).getTESTRESULT());
				ps.setString(2, resultList.get(i).getOPERATOR());
				ps.setTimestamp(3, new Timestamp(resultList.get(i).getMEASURETIME().getTime()));
				ps.setString(4, resultList.get(i).getSAMPLENO());
				ps.setString(5, resultList.get(i).getTESTID());
			}
			
			public int getBatchSize() {
				return resultList.size();
			}
		});
	}
	
	private String buildInsertSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("insert into l_testresult(");
        Field[] fields = TestResult.class.getDeclaredFields();

        for (int i = 0; i < fields.length - 2; i++) {
            builder.append(fields[i].getName());
            builder.append(',');
        }

        builder.append("ISPRINT) values (");
        for (int i = 0; i < fields.length - 2; i++) {
            builder.append("?,");
        }

        builder.append("?)");
        return builder.toString();
    }
	
	private void setValue(PreparedStatement ps, TestResult info) {

        Field[] fields = TestResult.class.getDeclaredFields();

        for (int i = 0; i < fields.length - 1; i++) {

            String type = fields[i].getType().getName();
            int k = i + 1;
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
            /*} catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();*/
            } catch (Exception e) {
                //e.printStackTrace();
            } finally {
                fields[i].setAccessible(false);
            }
        }
    }

	public List<String> getEditSampleNoFrom(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String sql = "select distinct SAMPLENO from l_testresult_edit where SAMPLENO like '" 
        		+ df.format(date) + "%' and EDITTIME>to_date('" 
        		+ sdf.format(date) + "','yyyy-MM-dd hh24:mi:ss')";
        return jdbcTemplateForZ1.queryForList(sql, String.class);
	}
}
