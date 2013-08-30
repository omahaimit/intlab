package com.zju.dao.hibernate;

//import java.text.SimpleDateFormat;
//import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
//import org.hibernate.Query;
//import org.hibernate.Session;

import com.zju.dao.PatientInfoDao;
import com.zju.model.PatientInfo;

public class PatientInfoDaoHibernate extends GenericDaoHibernate<PatientInfo, Long> implements PatientInfoDao {

    private static String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public PatientInfoDaoHibernate() {
		super(PatientInfo.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getForDoctor(String doctorId, String fromDate, String toDate) {
		String hql = "from PatientInfo p where p.requester='" + doctorId + "' and p.receivetime between '" + fromDate
				+ " 00:00:00' and '" + toDate + " 23:59:59'";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getForPatient(String patientId, String fromDate, String toDate) {
		String hql = "from PatientInfo p where p.patientId='" + patientId + "' and p.receivetime between '" + fromDate
				+ " 00:00:00' and '" + toDate + " 23:59:59'";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getBySampleNo(String sampleNo) {
		return getHibernateTemplate().find("from PatientInfo p where p.sampleNo=?", sampleNo);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getById(String patientId) {
		return getHibernateTemplate().find("from PatientInfo where patientId=? order by receivetime desc", patientId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getForAudit(String operator, String fromDate, String toDate, String sample) {

		String sql = "from PatientInfo p where p.checkOperator='" + operator + "'";
		String date = " and p.receivetime between '" + fromDate + " 00:00:00' and '" + toDate + " 23:59:59'";

		if (sample != "") {
			if (sample.length() == 14) {
				return getHibernateTemplate().find(sql + " and p.sampleNo='" + sample + "'");
			} else {
				return getHibernateTemplate().find(sql + " and p.sampleNo like '" + sample + "%'");
			}
		} else {
			return getHibernateTemplate().find(sql + date);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getForAudit(String operator) {

		return getHibernateTemplate().find("from PatientInfo p where p.checkOperator=?", operator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getForAudit(String operator, int mark, int status) {

		List<PatientInfo> list = null;
		// Date now = new Date();
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// String date = df.format(now);
		String date = "2012-08-22";
		String pre = "from PatientInfo p where ";
		String end = "p.checkOperator='" + operator + "' and p.receivetime between '" + date + " 00:00:00' and '"
				+ date + " 23:59:59'";

		if (mark == 0 && status == -1) {
			list = getHibernateTemplate().find(pre + end);
		} else if (mark == 0 && status != -1) {
			list = getHibernateTemplate().find(pre + "p.auditStatus=" + status + " and " + end);
		} else {
			list = getHibernateTemplate().find(pre + "p.auditMark=" + mark + " and " + end);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getDiffCheck(PatientInfo info) {

        Date toDate = new Date(info.getReceivetime().getTime() - 1000);
        Calendar calendar = Calendar.getInstance(); 
        calendar.setTime(toDate); 
        calendar.add(Calendar.DATE,-180); 
        Date fromDate = calendar.getTime();
        
        List<PatientInfo> infos = getHibernateTemplate().find(
                "from PatientInfo p where p.patientId='" + info.getPatientId() + "' and p.receivetime between to_date('" + sdf.format(fromDate) + "','"
                        + DATEFORMAT + "') and to_date('" + sdf.format(toDate) + "','" + DATEFORMAT
                        + "') order by p.receivetime desc");
        return infos;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getSampleByOperator(String date, String department, String code, int mark, int status) {

		if (StringUtils.isEmpty(department) || StringUtils.isEmpty(code)) {
			return null;
		}
		
		if (StringUtils.isEmpty(date)) {
			date = "________";
		}
		
		String[] cds = code.split(",");
		StringBuilder builder = new StringBuilder();
		builder.append("from PatientInfo p where p.labdepartMent in (");
		builder.append(department);
		builder.append(")");
		if (status == -3) {
			// all
		} else if (status == -2) {
			builder.append(" and ");
			builder.append("p.auditStatus>-1");
		} else if(status == 3){
			builder.append(" and ");
			builder.append("p.modifyFlag=1");
		} else if(status == 4){
			builder.append(" and ");
			builder.append("p.resultStatus<5");
		} else {
			builder.append(" and ");
			builder.append("p.auditStatus=");
			builder.append(status);
		}
		if (mark != 0) {
			builder.append(" and p.auditMark=");
			builder.append(mark);
		}
		builder.append(" and (");
		for (int i=0; i<cds.length; i++) {
			builder.append("p.sampleNo like '");
			builder.append(date);
			builder.append(cds[i]);
			builder.append("%'");
			if (cds.length != i+1) {
				builder.append(" or ");
			}
		}
		builder.append(") order by p.sampleNo");
		return getHibernateTemplate().find(builder.toString());
	}

	@Override
	public List<Integer> getAuditInfo(String date, String department, String code, String user) {
		
		if (StringUtils.isEmpty(department) || StringUtils.isEmpty(code)) {
			return null;
		}
		if (StringUtils.isEmpty(date)) {
			date = "________";
		}
		
		String[] cds = code.split(",");
		StringBuilder builder = new StringBuilder();
		builder.append("select count(*) from PatientInfo p where p.labdepartMent");
		if (department.contains(",")) {
			builder.append(" in (");
			builder.append(department);
			builder.append(")");
		} else {
			builder.append("=");
			builder.append(department);
		}
		
		builder.append(" and ");
		
		StringBuilder bld = new StringBuilder();
		bld.append("(");
		for (int i=0; i<cds.length; i++) {
			bld.append("p.sampleNo like '");
			bld.append(date);
			bld.append(cds[i]);
			bld.append("%'");
			if (cds.length != i+1) {
				bld.append(" or ");
			}
		}
		bld.append(")");
		builder.append(bld.toString());
		
		int unaudit = ((Long)this.getHibernateTemplate().iterate(builder.toString() + " and p.auditStatus=0").next()).intValue();
		int unpass = ((Long)this.getHibernateTemplate().iterate(builder.toString() + " and p.auditStatus=2").next()).intValue();
		int danger = ((Long)this.getHibernateTemplate().iterate(builder.toString() + " and p.auditMark=6 and p.criticalDealFlag=0").next()).intValue();
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(unaudit);
		list.add(unpass);
		list.add(danger);
		
		if (!date.equals("________")) {
			int needwriteBack = ((Long)this.getHibernateTemplate().iterate(builder.toString() + " and p.checkOperator='" + user + "' and p.writeBack!=0").next()).intValue();
			list.add(needwriteBack);
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getHistoryTable(String patientId, String lab) {
		
		/*Session session = getHibernateTemplate().getSessionFactory().openSession();
		Query query = session.createQuery("from PatientInfo where patientId='"+ patientId +
				"' and ylxh='"+ ylxh +"' order by receivetime desc");
		List<PatientInfo> list = query.list();
		session.close();
		return list;*/
		return getHibernateTemplate().find("from PatientInfo where patientId='"+ patientId +
				"' order by receivetime desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getByBLH(String blh) {
		return getHibernateTemplate().find("from PatientInfo where blh='"+ blh +
				"' order by receivetime desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getHasResult(String sample, int limit) {
		limit += 1;
		List<PatientInfo> infos = getHibernateTemplate().find("from PatientInfo where sampleno like '"+sample+"%' and auditstatus=0 and rownum<"+limit);
		for (PatientInfo info : infos) {
			info.getResults().size();
		}
		return infos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getSampleByCode(String code) {
		return getHibernateTemplate().find("from PatientInfo where sampleno like '"+ code +"%'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PatientInfo> getByPatientName(String from, String to,
			String pName) {
		String hql = "from PatientInfo p where p.patientName='" + pName + "' and p.receivetime between to_date('" + from + " 00:00:00','"
                        + DATEFORMAT + "') and to_date('" + to + " 23:59:59','" + DATEFORMAT
                        + "') order by p.receivetime desc";
		return getHibernateTemplate().find(hql);
	}	
}
