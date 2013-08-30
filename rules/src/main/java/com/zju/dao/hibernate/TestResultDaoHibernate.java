package com.zju.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zju.dao.TestResultDao;
import com.zju.model.TestResult;
import com.zju.model.TestResultPK;

@Repository("testResultDao")
public class TestResultDaoHibernate extends GenericDaoHibernate<TestResult, TestResultPK> implements TestResultDao {

	private static String DATEFORMAT = "yyyy-MM-dd hh24:mi:ss";
	
	public TestResultDaoHibernate() {
		super(TestResult.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getBySampleNo(String sampleNo) {
		return getHibernateTemplate().find("select t from TestResult t, TestDescribe b where t.testId=b.testId and t.sampleNo=? order by printord", sampleNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getByList(String sampleList) {
		return getHibernateTemplate().find("from TestResult t where t.sampleNo in " + sampleList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getHistory(String patientId, String history) {

		return getHibernateTemplate().find(
				"select t from PatientInfo p, TestResult t where p.patientId='" + patientId
						+ "' and t.sampleNo=p.sampleNo and t.testId in " + history + " order by p.receivetime desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getByPatientId(String patientId) {
		return getHibernateTemplate().find(
				"select t from PatientInfo p, TestResult t where p.patientId='" + patientId
				+ "' and t.sampleNo=p.sampleNo order by p.receivetime");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getForDifCheck(String patientId, String testId,
			Date date) {
		return getHibernateTemplate().find(
				"select t from PatientInfo p, TestResult t where p.patientId='" + patientId
				+ "' and t.sampleNo=p.sampleNo and t.testId='"+testId+"' and t.measureTime>=date('"
						+date +"')-15 order by t.measureTime desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getUnAudit(String operator) {
		
		return getHibernateTemplate().find(
				"select t from PatientInfo p, TestResult t where p.checkOperator='" + operator
						+ "' and t.sampleNo=p.sampleNo and p.auditStatus=0 order by t.sampleNo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getListByTestString(String sampleNo,
			String testString) {
		return getHibernateTemplate().find("from TestResult where sampleNo='" + sampleNo
				+ "' and testId in ('"+ testString.replace(",", "','") +"')");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestResult> getSingleHistory(String testid, String patientName,
			String birthday) {
		return getHibernateTemplate().find("select t from PatientInfo p, TestResult t where t.testId='" + testid
				+ "' and p.patientName='"+ patientName + "' and p.birthday=to_date('" + birthday + "','" + DATEFORMAT
                        + "') and p.sampleNo=t.sampleNo order by t.measureTime desc)");
	}

}
