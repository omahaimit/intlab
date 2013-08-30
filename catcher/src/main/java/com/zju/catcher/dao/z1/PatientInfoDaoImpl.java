package com.zju.catcher.dao.z1;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.PatientAndResult;
import com.zju.catcher.entity.z1.PatientInfo;
import com.zju.catcher.entity.z1.TestResult;

@Repository
public class PatientInfoDaoImpl  implements PatientInfoDao {

	@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplateForZ1;
    
    @SuppressWarnings("unused")
	private TransactionTemplate transactionTemplate;
    @SuppressWarnings("unused")
	private JdbcTemplate jdbcTemplateManager;
    
    private static Log log = LogFactory.getLog(PatientInfoDaoImpl.class);
    
    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
		DataSourceTransactionManager manager = (DataSourceTransactionManager)transactionTemplate.getTransactionManager();   
		jdbcTemplateManager = new JdbcTemplate(manager.getDataSource()); 
	}

    private static final String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";
    private static final int STATUS_PASS = 5;
    private static final int STATUS_UNPASS = 4;

    public List<PatientInfo> getListBetween(Date from, Date to) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "select * from l_patientinfo where RECEIVETIME between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { df.format(from), DATEFORMAT, df.format(to), DATEFORMAT };
        return jdbcTemplateForZ1.query(sql, args, rowMapper);
    }

    public List<PatientInfo> getAllChanged() {
        return jdbcTemplateForZ1.query("select * from l_patientinfo_cache", rowMapper);
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
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }
                field.setAccessible(false);
            }
            return info;
        }
    };

    public List<PatientInfo> getList(List<String> sampleNoList) {

        if (sampleNoList == null || sampleNoList.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder("select * from l_patientinfo where sampleNo in (");

        for (int i = 0; i < sampleNoList.size(); i++) {
            builder.append("'");
            builder.append(sampleNoList.get(i));
            if (i < sampleNoList.size() - 1) {
                builder.append("',");
            } else {
                builder.append("')");
            }
        }
        String sql = builder.toString();
        System.out.println(sql);
        return jdbcTemplateForZ1.query(sql, rowMapper);
    }

    public List<PatientAndResult> getAll(Date date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strDate = df.format(date);
        String sql = "select * from l_patientinfo p left join l_testresult t on p.sampleno=t.sampleno where p.sampleno like '"
                + strDate + "%'";
        return jdbcTemplateForZ1.query(sql, mergeRowMapper);
    }
    
    public List<PatientAndResult> getNotToday(Date date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strDate = df.format(date);
        SimpleDateFormat pdf = new SimpleDateFormat("yyyy-MM-dd");
        String preStrDate = pdf.format(date);
        String sql = "select * from l_patientinfo p left join l_testresult t on p.sampleno=t.sampleno where p.sampleno not like '"
                + strDate + "%' and t.measuretime between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { preStrDate + " 00:00:00", DATEFORMAT, preStrDate + " 23:59:59", DATEFORMAT };
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
                } catch (Exception e) {}
                field.setAccessible(false);
            }
            return info;
        }
    };

    public List<PatientInfo> getPatientInfo(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = df.format(date);
        String sql = "select * from l_patientinfo where receivetime between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { strDate + " 00:00:00", DATEFORMAT, strDate + " 23:59:59", DATEFORMAT };
        return jdbcTemplateForZ1.query(sql, args, rowMapper);
    }

	public void UpdateSampleHasPass(final List<PatientSample> samples) {
		
		boolean success = true;
		try {
			log.info("执行tesdresult写回...");
			jdbcTemplateForZ1.batchUpdate("update l_testresult set teststatus=? where sampleno=? and teststatus<6", new BatchPreparedStatementSetter() {
				
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					PatientInfo info = samples.get(i).getPatientInfo();
					ps.setInt(1, getTestStatus(samples.get(i)));
					ps.setString(2, info.getSAMPLENO());
				}
				
				public int getBatchSize() {
					return samples.size();
				}
			});
			log.info("tesdresult写回完成");
			
			log.info("执行patientinfo写回...");
			jdbcTemplateForZ1.batchUpdate("update l_patientinfo set resultstatus=?,checkoperator=?,checktime=?,checkeropinion=?,chkoper2=? where doctadviseno=? and resultstatus<6", new BatchPreparedStatementSetter() {
				
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					PatientInfo info = samples.get(i).getPatientInfo();
					ps.setInt(1, getTestStatus(samples.get(i)));
					ps.setString(2, info.getCHECKOPERATOR());
					if (info.getCHECKTIME() != null)
						ps.setTimestamp(3, new Timestamp(info.getCHECKTIME().getTime()));
					else
						ps.setTimestamp(3, new Timestamp(new Date().getTime()));
					ps.setString(4, info.getCHECKEROPINION());
					ps.setString(5, info.getCHKOPER2());
					ps.setLong(6, info.getDOCTADVISENO());
				}
				
				public int getBatchSize() {
					return samples.size();
				}
			});
			log.info("patientinfo写回完成");

		} catch (Exception e) {
			success = false;
			log.error("回滚!", e);
		} finally {
			if (success) {
				jdbcTemplate.batchUpdate("update l_patientinfo set writeback=0 where doctadviseno=?", new BatchPreparedStatementSetter() {
					
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, samples.get(i).getPatientInfo().getDOCTADVISENO());
					}
					
					public int getBatchSize() {
						return samples.size();
					}
				});
			}
		}

		/*transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				log.info("Transaction Started!");
				boolean success = true;
				try {
					log.info("执行patientinfo写回...");
					jdbcTemplateManager.batchUpdate("update l_patientinfo set resultstatus=?,checkoperator=?,checktime=? where doctadviseno=? and resultstatus<6", new BatchPreparedStatementSetter() {
						
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							PatientInfo info = samples.get(i).getPatientInfo();
							ps.setInt(1, getTestStatus(samples.get(i)));
							ps.setString(2, info.getCHECKOPERATOR());
							if (info.getCHECKTIME() != null)
								ps.setTimestamp(3, new Timestamp(info.getCHECKTIME().getTime()));
							else
								ps.setTimestamp(3, new Timestamp(new Date().getTime()));
							ps.setLong(4, info.getDOCTADVISENO());
						}
						
						public int getBatchSize() {
							return samples.size();
						}
					});
					log.info("patientinfo写回完成");
					log.info("执行tesdresult写回...");
					jdbcTemplateManager.batchUpdate("update l_testresult set teststatus=? where sampleno=? and teststatus<6", new BatchPreparedStatementSetter() {
						
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							PatientInfo info = samples.get(i).getPatientInfo();
							ps.setInt(1, getTestStatus(samples.get(i)));
							ps.setString(2, info.getSAMPLENO());
						}
						
						public int getBatchSize() {
							return samples.size();
						}
					});
					log.info("tesdresult写回完成");
					
				} catch (Exception e) {
					status.setRollbackOnly();
					success = false;
					log.error("回滚!", e);
				} finally {
					if (success) {
						jdbcTemplate.batchUpdate("update l_patientinfo set writeback=0 where doctadviseno=?", new BatchPreparedStatementSetter() {
							
							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setLong(1, samples.get(i).getPatientInfo().getDOCTADVISENO());
							}
							
							public int getBatchSize() {
								return samples.size();
							}
						});
					}
				}
			}
		});*/
	
	} 
	
	private int getTestStatus(PatientSample sample) {
		int result = STATUS_PASS;
		if (sample.getAuditStatus() != 1) {
			result = STATUS_UNPASS;
		}
		return result;
	}
}
