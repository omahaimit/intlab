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

import com.zju.catcher.entity.local.MapPK;
import com.zju.catcher.entity.local.PK;
import com.zju.catcher.entity.z1.TestResult;
import com.zju.catcher.util.Constants;

@Repository
public class TestDataDaoImpl implements TestDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //private static final String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";
    private static Log log = LogFactory.getLog(TestDataDaoImpl.class);
    
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    
    private static final String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";

    /*
     * public void insert(List<TestResult> testResults) {
     * 
     * final List<TestResult> insertList = new ArrayList<TestResult>(); final List<TestResult> updateList = new
     * ArrayList<TestResult>(); Map<PK, TestResult> map = new HashMap<PK, TestResult>();
     * 
     * if (testResults == null || testResults.size() == 0) return;
     * 
     * // 更新、插入分离 StringBuilder builder = new StringBuilder(
     * "select SAMPLENO,TESTID from l_testresult where (sampleNo,testId) in ("); for (int i = 0; i < testResults.size();
     * i++) { TestResult info = testResults.get(i); // System.out.println(info.getSAMPLENO()+","+info.getTESTID()); if
     * (info.getTESTID() == null) continue; map.put(new PK(info.getSAMPLENO(), info.getTESTID()), info);
     * builder.append("('"); builder.append(info.getSAMPLENO()); builder.append("','");
     * builder.append(info.getTESTID()); builder.append("')"); if (i < testResults.size() - 1) { builder.append(','); }
     * else { builder.append(')'); } }
     * 
     * List<Map<String, Object>> list = jdbcTemplate.queryForList(builder.toString()); // System.out.println(list);
     * 
     * for (Map<String, Object> l : list) { PK pk = new PK(l.get("SAMPLENO"), l.get("TESTID")); if (map.containsKey(pk))
     * { updateList.add(map.get(pk)); map.remove(pk); } } insertList.addAll(map.values());
     * 
     * log.info("INSERT : " + insertList.size()); log.info("UPDATE : " + updateList.size());
     * 
     * if (insertList.size() != 0) { String insertSql = buildInsertSql(); // System.out.println(insertSql); // 批量insert
     * jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
     * 
     * public void setValues(PreparedStatement ps, int i) throws SQLException { setValue(ps, insertList.get(i)); //
     * System.out.println(i); }
     * 
     * public int getBatchSize() { return insertList.size(); }
     * 
     * }); }
     * 
     * log.info("Finished Insert!");
     * 
     * if (updateList.size() != 0) { String updateSql = buildUpdateSql(); // 批量update
     * jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {
     * 
     * public void setValues(PreparedStatement ps, int i) throws SQLException { setValue(ps, updateList.get(i)); }
     * 
     * public int getBatchSize() { return updateList.size(); }
     * 
     * }); }
     * 
     * log.info("Finished Update!"); }
     */

    public void update(final List<TestResult> testResults) {

        if (testResults != null && testResults.size() != 0) {

            log.info("UPDATE : " + testResults.size());
            
            String updateSql = "update l_testresult set testresult=?, teststatus=?, measuretime=? where sampleno=? and testid=? and editmark<3";
            // 批量update
            jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, testResults.get(i).getTESTRESULT());
                    ps.setInt(2, testResults.get(i).getTESTSTATUS());
                    Date measureDate = testResults.get(i).getMEASURETIME();
                    if (measureDate != null) {
                        ps.setTimestamp(3, new java.sql.Timestamp(measureDate.getTime()));
                    } else {
                        ps.setNull(3, Types.TIMESTAMP);
                    }
                    ps.setString(4,	testResults.get(i).getSAMPLENO());
                    ps.setString(5, testResults.get(i).getTESTID());
                }

                public int getBatchSize() {
                    return testResults.size();
                }

            });

            log.info("Finished Update!");
        }
    }

    public void delete(final List<TestResult> testResults) {
    	if (testResults != null && testResults.size() != 0) {

            log.info("Delete : " + testResults.size());
            
            String updateSql = "delete from l_testresult where sampleno=? and testid=?";
            // 批量update
            jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter() {

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1,	testResults.get(i).getSAMPLENO());
                    ps.setString(2, testResults.get(i).getTESTID());
                }

                public int getBatchSize() {
                    return testResults.size();
                }

            });

            log.info("Finished Delete!");
        }
    }
    
    private void setValue(PreparedStatement ps, TestResult info) {

        Field[] fields = TestResult.class.getDeclaredFields();
        int k = 0;
        for (int i = 0; i < fields.length; i++) {

            String type = fields[i].getType().getName();
            k = (i == 0 || i == 1) ? (fields.length + i - 1) : (i - 1);

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

    private String buildInsertSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("insert into l_testresult(");
        Field[] fields = TestResult.class.getDeclaredFields();

        for (int i = 2; i < fields.length; i++) {
            builder.append(fields[i].getName());
            builder.append(',');
        }

        builder.append("SAMPLENO,TESTID) values (");
        for (int i = 1; i < fields.length; i++) {
            builder.append("?,");
        }

        builder.append("?)");
        return builder.toString();
    }

    @SuppressWarnings("unused")
	private String buildUpdateSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("update l_testresult set ");
        Field[] fields = TestResult.class.getDeclaredFields();

        for (int i = 2; i < fields.length; i++) {
            builder.append(fields[i].getName());
            builder.append("=?");
            if (i + 1 < fields.length) {
                builder.append(',');
            }
        }
        builder.append(" where SAMPLENO=? and TESTID=?");
        return builder.toString();
    }

    public void insert(final List<TestResult> testResults) {
        if (testResults != null && testResults.size() != 0) {

            log.info("INSERT : " + testResults.size());
            String insertSql = buildInsertSql();
            // 批量insert
            jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    setValue(ps, testResults.get(i));
                }

                public int getBatchSize() {
                    return testResults.size();
                }

            });

            log.info("Finished INSERT!");
        }
    }

    public void insertMapping(final List<MapPK> mapList) {

        if (mapList != null && mapList.size() != 0) {

            log.info("INSERT : " + mapList.size());
            String insertSql = "insert into intlab_patient_result values (?,?,?)";
            // 批量insert
            jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, mapList.get(i).getDoctId());
                    ps.setString(2, mapList.get(i).getSampleNo());
                    ps.setString(3, mapList.get(i).getTestId());
                }

                public int getBatchSize() {
                    return mapList.size();
                }

            });

            log.info("Finished INSERT!");
        }
    }

    public List<PK> getTestResultPKNot(Date date) {
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strDate = df.format(date);
        SimpleDateFormat pdf = new SimpleDateFormat("yyyy-MM-dd");
        String preStrDate = pdf.format(date);
        String sql = "select sampleNo, testId from l_testresult where sampleno not like '"
                + strDate + "%' and measuretime between to_date(?,?) and to_date(?,?)";
        Object[] args = new Object[] { preStrDate + " 00:00:00", DATEFORMAT, preStrDate + " 23:59:59", DATEFORMAT };
        return jdbcTemplate.query(sql, args, new RowMapper<PK>() {

            public PK mapRow(ResultSet rs, int rowNum) throws SQLException {
                String s = rs.getString(1);
                String t = rs.getString(2);
                return new PK(s, t);
            }

        });
    }
    
    public List<PK> getTestResultPK(Date date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strDate = df.format(date);
        String sql = "select sampleNo, testId from l_testresult where sampleno like '"
                + strDate + "%'";
        //Object[] args = new Object[] { strDate + " 00:00:00", DATEFORMAT, strDate + " 23:59:59", DATEFORMAT };
        return jdbcTemplate.query(sql, new RowMapper<PK>() {

            public PK mapRow(ResultSet rs, int rowNum) throws SQLException {
                String s = rs.getString(1);
                String t = rs.getString(2);
                return new PK(s, t);
            }

        });
    }

    public List<MapPK> getMapPk(Date date) {
        
        String strDate = df.format(date);
        String sql = "select doc_id, sample_no, test_id from intlab_patient_result where sample_no like '"
                + strDate + "%'";
        //Object[] args = new Object[] { strDate + " 00:00:00", DATEFORMAT, strDate + " 23:59:59", DATEFORMAT };
        return jdbcTemplate.query(sql, new RowMapper<MapPK>() {

            public MapPK mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long d = rs.getLong(1);
                String s = rs.getString(2);
                String t = rs.getString(3);
                return new MapPK(d, s, t);
            }

        });
    }

	public List<TestResult> getResultWithEdit(String sample, String lib, String operator) {
		String sql = "select t.* from l_testresult t, l_patientinfo p where t.sampleno like '" + sample + "%' and t.sampleno=p.sampleno and p.writeback<>0 and p.labdepartment='"+lib+"'";
		if (!StringUtils.isEmpty(operator)) {
			sql += " and p.checkoperator='" + operator + "'";
		}
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	protected RowMapper<TestResult> rowMapper = new RowMapper<TestResult>() {
        public TestResult mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestResult result = new TestResult();
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
               /* } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {*/
                } catch (Exception e) {}
                field.setAccessible(false);
            }
            return result;
        }
    };

	public void updateEditMark(final List<TestResult> resultList) {
		
		final List<TestResult> deleteList = new ArrayList<TestResult>();
		for (TestResult tr : resultList) {
			if (tr.getEDITMARK() % Constants.DELETE_FLAG == 0) {
				deleteList.add(tr);
			}
		}
		
		if (deleteList.size() != 0) {
			jdbcTemplate.batchUpdate("delete from l_testresult where SAMPLENO=? and TESTID=?", new BatchPreparedStatementSetter() {

	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	                ps.setString(1, deleteList.get(i).getSAMPLENO());
	                ps.setString(2, deleteList.get(i).getTESTID());
	            }

	            public int getBatchSize() {
	                return deleteList.size();
	            }

	        });
		}
		
		String sql = "update l_testresult set EDITMARK=? where SAMPLENO=? and TESTID=?";
        // 批量Update
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {
            	//ps.setInt(1, resultList.get(i).getEDITMARK());
            	ps.setInt(1, 0);
                ps.setString(2, resultList.get(i).getSAMPLENO());
                ps.setString(3, resultList.get(i).getTESTID());
            }

            public int getBatchSize() {
                return resultList.size();
            }

        });
	}

	public List<TestResult> getSample(String sampleNo) {
		String sql = "select * from l_testresult where sampleno='" + sampleNo + "'";
		return jdbcTemplate.query(sql, rowMapper);
	}
}
