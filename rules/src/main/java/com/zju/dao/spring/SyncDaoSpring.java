package com.zju.dao.spring;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zju.dao.SyncDao;
import com.zju.model.ContactInfor;
import com.zju.model.Describe;
import com.zju.model.Diagnostic;
import com.zju.model.FormulaItem;
import com.zju.model.Patient;
import com.zju.model.PatientInfo;
import com.zju.model.Profile;
import com.zju.model.ReferenceValue;
import com.zju.model.Section;
import com.zju.model.SyncLabGroupInfo;
import com.zju.model.SyncMapPK;
import com.zju.model.SyncPK;
import com.zju.model.SyncPatient;
import com.zju.model.SyncResult;
import com.zju.model.SyncSample;
import com.zju.model.TestResult;
import com.zju.model.User;

public class SyncDaoSpring implements SyncDao {

    private JdbcTemplate jdbcTemplatelocal;
    private JdbcTemplate jdbcTemplatez1;

    @Override
    public List<SyncPatient> getPatientInfo(String date, String department, String code) {

        if (StringUtils.isEmpty(date) || date.length() != 8 || StringUtils.isEmpty(department)
                || StringUtils.isEmpty(code))
            return null;
        String[] cds = code.split(",");
        StringBuilder builder = new StringBuilder();
        builder.append("select * from l_patientinfo where ");

        builder.append("(");
        for (int i = 0; i < cds.length; i++) {
            builder.append("sampleno like '");
            builder.append(date);
            builder.append(cds[i]);
            builder.append("%'");
            if (cds.length != i + 1) {
                builder.append(" or ");
            }
        }
        builder.append(")");

        builder.append(" and labdepartment in (");
        builder.append(department);
        builder.append(")");

        return jdbcTemplatez1.query(builder.toString(), new RowMapper<SyncPatient>() {

            @Override
            public SyncPatient mapRow(ResultSet rs, int rowNum) throws SQLException {

                SyncPatient p = new SyncPatient();
                setField(rs, p);
                return p;
            }

        });
    }

    @Override
    public List<SyncResult> getTestResult(String date, String department, String code) {

        if (StringUtils.isEmpty(date) || date.length() != 8 || StringUtils.isEmpty(code))
            return null;
        String[] cds = code.split(",");
        StringBuilder builder = new StringBuilder();
        builder.append("select * from l_testresult where ");

        builder.append("(");
        for (int i = 0; i < cds.length; i++) {
            builder.append("sampleno like '");
            builder.append(date);
            builder.append(cds[i]);
            builder.append("%'");
            if (cds.length != i + 1) {
                builder.append(" or ");
            }
        }
        builder.append(")");

        return jdbcTemplatez1.query(builder.toString(), new RowMapper<SyncResult>() {

            @Override
            public SyncResult mapRow(ResultSet rs, int rowNum) throws SQLException {

                SyncResult r = new SyncResult();
                setField(rs, r);
                return r;
            }

        });
    }

    @Override
    public List<Long> getExsitPatientPK(String date, String department, String code, int status) {
        
        if (StringUtils.isEmpty(date) || date.length() != 8 || StringUtils.isEmpty(department)
                || StringUtils.isEmpty(code))
            return null;
        String[] cds = code.split(",");
        StringBuilder builder = new StringBuilder();
        builder.append("select doctadviseno from l_patientinfo where ");

        builder.append("(");
        for (int i = 0; i < cds.length; i++) {
            builder.append("sampleno like '");
            builder.append(date);
            builder.append(cds[i]);
            builder.append("%'");
            if (cds.length != i + 1) {
                builder.append(" or ");
            }
        }
        builder.append(")");

        builder.append(" and labdepartment in (");
        builder.append(department);
        builder.append(")");

        return jdbcTemplatelocal.queryForList(builder.toString(), Long.class);
    }

    @Override
    public List<SyncPK> getExsitResultPK(String date, String department, String code) {
        
        if (StringUtils.isEmpty(date) || date.length() != 8 || StringUtils.isEmpty(code))
            return null;
        String[] cds = code.split(",");
        StringBuilder builder = new StringBuilder();
        builder.append("select sampleno, testid from l_testresult where ");

        builder.append("(");
        for (int i = 0; i < cds.length; i++) {
            builder.append("sampleno like '");
            builder.append(date);
            builder.append(cds[i]);
            builder.append("%'");
            if (cds.length != i + 1) {
                builder.append(" or ");
            }
        }
        builder.append(")");

        return jdbcTemplatelocal.query(builder.toString(), new RowMapper<SyncPK>() {

            @Override
            public SyncPK mapRow(ResultSet rs, int rowNum) throws SQLException {

                String s = rs.getString(1);
                String t = rs.getString(2);
                return new SyncPK(s, t);
            }

        });
    }

    @Override
    public List<SyncMapPK> getExsitMapPK(String date, String code) {
        
        if (StringUtils.isEmpty(date) || date.length() != 8 || StringUtils.isEmpty(code))
            return null;
        String[] cds = code.split(",");
        StringBuilder builder = new StringBuilder();
        builder.append("select doc_id, sample_no, test_id from intlab_patient_result where ");

        builder.append("(");
        for (int i = 0; i < cds.length; i++) {
            builder.append("sample_no like '");
            builder.append(date);
            builder.append(cds[i]);
            builder.append("%'");
            if (cds.length != i + 1) {
                builder.append(" or ");
            }
        }
        builder.append(")");

        return jdbcTemplatelocal.query(builder.toString(), new RowMapper<SyncMapPK>() {

            @Override
            public SyncMapPK mapRow(ResultSet rs, int rowNum) throws SQLException {

                long d = rs.getLong(1);
                String s = rs.getString(2);
                String t = rs.getString(3);
                return new SyncMapPK(d, s, t);
            }

        });
    }

    @Override
    public void updatePatients(final List<SyncPatient> patients) {

        if (patients != null && patients.size() != 0) {
            String sql = updatePatientSql();
            jdbcTemplatelocal.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    setValue(ps, patients.get(i), 1);
                }

                @Override
                public int getBatchSize() {
                    return patients.size();
                }
            });
        }
    }

    @Override
    public void updateResults(final List<SyncResult> results) {

        if (results != null && results.size() != 0) {
            String sql = updateResultSql();
            jdbcTemplatelocal.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    setValue(ps, results.get(i), 2);
                }

                @Override
                public int getBatchSize() {
                    return results.size();
                }
            });
        }
    }

    @Override
    public void insertPatients(final List<SyncPatient> patients) {

        if (patients != null && patients.size() != 0) {
            String sql = insertPatientSql();
            jdbcTemplatelocal.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    setValue(ps, patients.get(i), 0);
                }

                @Override
                public int getBatchSize() {
                    return patients.size();
                }
            });
        }
    }
    
    @Override
    public void insertResults(final List<SyncResult> results) {

        if (results != null && results.size() != 0) {
            String sql = insertResultSql();
            jdbcTemplatelocal.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    setValue(ps, results.get(i), 0);
                }

                @Override
                public int getBatchSize() {
                    return results.size();
                }
            });
        }
    }

    @Override
    public void insertPKMap(final List<SyncMapPK> mapPK) {
        
        if (mapPK != null && mapPK.size() != 0) {
            String sql = "insert into intlab_patient_result values (?,?,?)";
            jdbcTemplatelocal.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    
                    ps.setLong(1, mapPK.get(i).getDoctId());
                    ps.setString(2, mapPK.get(i).getSampleNo());
                    ps.setString(3, mapPK.get(i).getTestId());
                }

                @Override
                public int getBatchSize() {
                    return mapPK.size();
                }
            });
        }
    }

    @Autowired
    public void setJdbcTemplatelocal(JdbcTemplate jdbcTemplatelocal) {
        this.jdbcTemplatelocal = jdbcTemplatelocal;
    }

    @Autowired
    public void setJdbcTemplatez1(JdbcTemplate jdbcTemplatez1) {
        this.jdbcTemplatez1 = jdbcTemplatez1;
    }

	private void setValue(PreparedStatement ps, Object obj, int move) {

        Field[] fields = obj.getClass().getDeclaredFields();
        int k = 0;
        for (int i = 0; i < fields.length; i++) {

            String type = fields[i].getType().getName();
            // 平移计算
            k = (i < move) ? (fields.length - move + i + 1) : (i - move + 1);

            try {
                fields[i].setAccessible(true);
                if (type.equals(String.class.getName())) {
                    ps.setString(k, (String) fields[i].get(obj));
                } else if (type.equals(int.class.getName())) {
                    ps.setInt(k, fields[i].getInt(obj));
                } else if (type.equals(Date.class.getName())) {
                    if (fields[i].get(obj) != null) {
                        Date d = (Date) fields[i].get(obj);
                        ps.setTimestamp(k, new java.sql.Timestamp(d.getTime()));
                    } else {
                        ps.setNull(k, Types.TIMESTAMP);
                    }
                } else if (type.equals(long.class.getName())) {
                    ps.setLong(k, fields[i].getLong(obj));
                } else if (type.equals(char.class.getName())) {
                    char c = fields[i].getChar(obj);
                    ps.setString(k, String.valueOf(c));
                } else {
                    ps.setObject(k, fields[i].get(obj));
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

    private String updatePatientSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("update l_patientinfo set ");
        Field[] fields = SyncPatient.class.getDeclaredFields();

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

    private String updateResultSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("update l_testresult set ");
        Field[] fields = SyncResult.class.getDeclaredFields();

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
    
    private String insertPatientSql() {
        
        StringBuilder builder = new StringBuilder();
        builder.append("insert into l_patientinfo(");
        Field[] fields = SyncPatient.class.getDeclaredFields();

        for (int i = 0; i < fields.length - 1; i++) {
            builder.append(fields[i].getName());
            builder.append(',');
        }
        builder.append(fields[fields.length - 1].getName());
        builder.append(") values (");
        for (int i = 0; i < fields.length - 1; i++) {
            builder.append("?,");
        }
        builder.append("?)");
        return builder.toString();
    }

    private String insertResultSql() {

        StringBuilder builder = new StringBuilder();
        builder.append("insert into l_testresult(");
        Field[] fields = SyncResult.class.getDeclaredFields();

        for (int i = 0; i < fields.length - 1; i++) {
            builder.append(fields[i].getName());
            builder.append(',');
        }
        builder.append(fields[fields.length - 1].getName());
        builder.append(") values (");
        for (int i = 0; i < fields.length - 1; i++) {
            builder.append("?,");
        }
        builder.append("?)");
        return builder.toString();
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

	@Override
	public List<Patient> getPatientList(String patientIds) {

		String sql ="select JZKH,LXDZ,LXDH,BAH from gy_brjbxxk where JZKH in ("+patientIds+")";
		return jdbcTemplatez1.query(sql, new RowMapper<Patient>() {

            @Override
            public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {

                Patient p = new Patient();
                p.setPatientId(rs.getString("JZKH"));
                p.setAddress(rs.getString("LXDZ"));
                p.setPhone(rs.getString("LXDH"));
                p.setBlh(rs.getString("BAH"));
                return p;
            }
		});
	}

	@Override
	public void updateSamples(List<SyncSample> samples) {
	
	}

	@Override
	public void insertSamples(List<SyncSample> samples) {

	}

	@Override
	public List<Section> getSection() {
		
		String sql ="select KSDM,KSMC from gy_ksdm";
		return jdbcTemplatez1.query(sql, new RowMapper<Section>() {

            @Override
            public Section mapRow(ResultSet rs, int rowNum) throws SQLException {

            	Section sn = new Section();
            	sn.setKsdm(rs.getString("KSDM"));
            	sn.setKsmc(rs.getString("KSMC"));
                return sn;
            }
		});
	}

	@Override
	public void updateStatus(final PatientInfo info) {
		/*String sql = "update l_patientinfo set resultstatus=?,editoperator=?,edittime=? where doctadviseno=?";
		jdbcTemplatez1.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
			}
			
		});*/
	}

	@Override
	public List<Describe> getAllDescribe() {
		return jdbcTemplatelocal.query("select * from l_testdescribe", new RowMapper<Describe>() {

			@Override
			public Describe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Describe des = new Describe();
				setField(rs, des);
				return des;
			}
			
		});
	}

	@Override
	public List<ReferenceValue> getAllReferenceValue() {
		return jdbcTemplatelocal.query("select * from l_referencevalue", new RowMapper<ReferenceValue>() {

			@Override
			public ReferenceValue mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReferenceValue ref = new ReferenceValue();
				setField(rs, ref);
				return ref;
			}
			
		});
	}

	@Override
	public List<Profile> getProfiles(String name) {
		String sql = "select PROFILENAME,PROFILEDESCRIBE,DEVICEID,PROFILETEST,JYZ from l_profiletest where section=?";
		return jdbcTemplatez1.query(sql, new Object[] { name }, new RowMapper<Profile>() {
			@Override
			public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
				Profile profile = new Profile();
				profile.setName(rs.getString("PROFILENAME"));
				profile.setDescribe(rs.getString("PROFILEDESCRIBE"));
				profile.setDeviceId(rs.getString("DEVICEID"));
				profile.setTest(rs.getString("PROFILETEST"));
				profile.setJyz(rs.getString("JYZ"));
				return profile;
			}
		});
	}

	@Override
	public List<SyncResult> getZ1BySampleNo(String sampleNo) {
		String sql = "select t.* from l_testresult t, l_testdescribe b where sampleno='"+sampleNo+"' and t.testid=b.testid order by b.printord";
		return jdbcTemplatez1.query(sql, new RowMapper<SyncResult>() {

            @Override
            public SyncResult mapRow(ResultSet rs, int rowNum) throws SQLException {

                SyncResult r = new SyncResult();
                setField(rs, r);
                return r;
            }

        });
	}

	@Override
	public List<SyncResult> getLocalBySampleNo(String sampleNo) {
		String sql = "select t.* from l_testresult t, l_testdescribe b where sampleno='"+sampleNo+"' and t.testid=b.testid order by b.printord";
		return jdbcTemplatez1.query(sql, new RowMapper<SyncResult>() {

            @Override
            public SyncResult mapRow(ResultSet rs, int rowNum) throws SQLException {

                SyncResult r = new SyncResult();
                setField(rs, r);
                return r;
            }

        });
	}

	@Override
	public List<String> getProfileJYZ(String profileName, String deviceId) {
		if (StringUtils.isEmpty(deviceId)) {
			String sql = "select JYZ from l_profiletest where PROFILENAME=?";
			return jdbcTemplatez1.queryForList(sql, new Object[] { profileName }, String.class);
		} else {
			String sql = "select JYZ from l_profiletest where PROFILENAME=? and DEVICEID=?";
			return jdbcTemplatez1.queryForList(sql, new Object[] { profileName, deviceId }, String.class);
		}
	}

	@Override
	public List<Describe> getDescribeByName(String name) {
		return jdbcTemplatez1.query("select * from l_testdescribe where chinesename like '"+name+"%' or englishab like '"+name+"%'", new RowMapper<Describe>() {

			@Override
			public Describe mapRow(ResultSet rs, int rowNum) throws SQLException {
				Describe des = new Describe();
				setField(rs, des);
				return des;
			}
			
		});
	}

	@Override
	public List<User> getAllWriteBack(String sample, String labdepartment) {
		String sql = "select username, first_name, last_name, lab_code, library, count(*) as writecount from l_patientinfo, intlab_user where checkoperator=username and sampleno like '"
				+ sample + "%' and ";
		if (!StringUtils.isEmpty(labdepartment)) {
			sql +=  "labdepartment=" + labdepartment + " and ";
		}
		sql +=  "writeback=1 group by username, first_name, last_name, lab_code, library";
		List<User> userList = jdbcTemplatelocal.query(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setLabCode(rs.getString("lab_code"));
				user.setLastLibrary(rs.getString("library"));
				user.setWriteBackCount(rs.getInt("writecount"));
				return user;
			}
			
		});
		
		for (User user : userList) {
			String s = "select sampleno from l_patientinfo where checkoperator='"+user.getUsername()+"' and ";
			s += "sampleno like '" + sample + "%' and ";
			if (!StringUtils.isEmpty(labdepartment)) {
				sql +=  "labdepartment=" + labdepartment + " and ";
			}
			s += "writeback=1";
			List<String> sampleNoList = jdbcTemplatelocal.queryForList(s, String.class);
			StringBuilder builder = new StringBuilder();
			for (String sampleNo :sampleNoList) {
				builder.append(sampleNo);
				builder.append("<br/>");
			}
			user.setWebsite(builder.toString());
		}
		
		return userList;
	}

	@Override
	public List<ContactInfor> getContactInformation(String requester) {
		String sql = "";
		if(StringUtils.isNumeric(requester)){
			sql = "select * from contactinformation where workid=" + requester;
		}else{
			sql = "select * from contactinformation where name='" + requester + "'";
		}
		return jdbcTemplatelocal.query(sql, new RowMapper<ContactInfor>() {

			@Override
			public ContactInfor mapRow(ResultSet rs, int rowNum) throws SQLException {
				ContactInfor ci = new ContactInfor();
				setField(rs, ci);
				return ci;
			}
			
		});
	}

	@Override
	public List<FormulaItem> getFormulaItem(String labdepartment) {
		String sql = "select c.formulatype, c.testid, c.sampletype, c.formuladescribe, c.formula, c.formulaitem, c.testnumb, c.excludedescribe, c.excludeitem, t.isprint from l_calculateformula c, l_testdescribe t where c.testid=t.testid and t.labdepartment like '%" + labdepartment + "%'";
		return jdbcTemplatez1.query(sql, new RowMapper<FormulaItem>() {
			@Override
			public FormulaItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				FormulaItem fItem = new FormulaItem();
				setField(rs, fItem);
				return fItem;
			}
	    });
	}

	@Override
	public void updateFormula(TestResult testResult, String formula) {
		String sql = "update l_testresult set testresult=round(" + formula + ", 2) where testid='" + testResult.getTestId()
				+ "' and sampleno='" + testResult.getSampleNo() + "'";
		jdbcTemplatelocal.update(sql);
	}

	@Override
	public TestResult getTestResult(String testId, String sampleNo) {
		return jdbcTemplatelocal.queryForObject("select * from l_testresult where testid='"+testId+"' and sampleno='"+sampleNo+"'", new RowMapper<TestResult>(){

			@Override
			public TestResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				TestResult tr = new TestResult();
				setField(rs, tr);
				return tr;
			}
		});
	}

	@Override
	public void saveTestResult(TestResult testResult) {
		jdbcTemplatelocal.update("update l_testresult set testresult='" + testResult.getTestResult() + "',reflo='"
				+ testResult.getRefLo() + "',refhi='" + testResult.getRefHi() + "',editmark="
				+ testResult.getEditMark() + ",resultflag='" + testResult.getResultFlag() + "' where sampleno='"
				+ testResult.getSampleNo() + "' and testid='" + testResult.getTestId() + "'");
	}

	@Override
	public List<TestResult> getTestBySampleNo(String sampleNo) {
		String sql = "select t.* from l_testResult t, l_testDescribe b where t.testId=b.testId and t.sampleNo='"+ sampleNo+"' order by printord";
		return jdbcTemplatelocal.query(sql, new RowMapper<TestResult>() {
			@Override
			public TestResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				TestResult test = new TestResult();
				setField(rs, test);
				return test;
			}
	    });
	}

	@Override
	public List<SyncLabGroupInfo> getSyncLabGroupInfo() {
		String sql = "select * from lab_group_information";
		return jdbcTemplatez1.query(sql, new RowMapper<SyncLabGroupInfo>() {
			@Override
			public SyncLabGroupInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
				SyncLabGroupInfo slgi = new SyncLabGroupInfo();
				setField(rs, slgi);
				return slgi;
			}
	    });
	}

	@Override
	public List<Diagnostic> getDiagnostic() {
		String sql = "select * from diagnostic";
		return jdbcTemplatelocal.query(sql, new RowMapper<Diagnostic>() {
			@Override
			public Diagnostic mapRow(ResultSet rs, int rowNum) throws SQLException {
				Diagnostic d = new Diagnostic();
				setField(rs, d);
				return d;
			}
	    });
	}
}
