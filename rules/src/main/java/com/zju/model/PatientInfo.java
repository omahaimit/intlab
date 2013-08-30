package com.zju.model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zju.model.BaseObject;

/**
 * 检验单样本
 */
@Entity
@Table(name = "L_PATIENTINFO")
public class PatientInfo extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Primary Key
	private Long id; // 条形码编号

	private Date requesttime; // 医生开检验单的时间
	private String requester; // 开检验单的医生
	private int requestMode;
	private Date executetime; // 样本采集的时间
	private String executor; // 检验人员
	private Date receivetime; //
	private String receiver; //
	private int stayHospitalMode; // 就诊方式（门诊、住院、急诊）
	private String patientId;
	private int infantFlag; // 婴儿标识
	private String section; // 科室
	private String departBed; // 病床号
	private String patientName;
	private int sex;
	private Date birthday;
	private String diagnostic; // 诊断
	private char sampleType;
	private String examinaim; // 检验名称
	private String sampleNo;
	private int resultStatus; // 样本所处的状态
	private String checkOperator;
	private String checkerOpinion;
	private Date checkTime;
	private String labdepartMent; // 检验部门
	private int printFlag; // 是否打印
	private String chkoper2;
	private String printTime;
	private String intelExplain; // 智能解释
	private String ruleIds; // 智能解释对应的规则id
	private int viewFlag; // 检验结果是否已查看
	private int checkStatus; // 样本审核的状态
	private int auditMark; // 审核标记
	private int auditStatus; // 审核状态
	private int age;
	private String notes;
	private String markTests; // 需要标记的检验项目（包括比值校验、差值校验）
	private String criticalValues; // 危急值
	private String ylxh; // 医疗序号
	private Set<TestResult> results = new HashSet<TestResult>();
	private int criticalDealFlag; // 危机值是否被处理的标记
	private String criticalDeal; // 危机值处理信息
	private Date criticalDealTime;
	private String criticalContent;	//危急内容
	private String criticalDealPerson;	//处理人员
	private String blh;
	private int cycle;
	private int writeBack;
	private int modifyFlag;
	private Date sendTime;
	private Date ksReceiveTime;
	private String passReason;

	/**
	 * 主键、医嘱号
	 */
	@Id
	@Column(name = "DOCTADVISENO", length = 20)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 检验单开单时间
	 */
	@Column(name = "REQUESTTIME")
	public Date getRequesttime() {
		return requesttime;
	}

	public void setRequesttime(Date requesttime) {
		this.requesttime = requesttime;
	}

	/**
	 * 检验单开单者
	 */
	@Column(name = "REQUESTER", length = 20)
	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}
	
	/**
	 * 检验单类型
	 */
	@Column(name = "REQUESTMODE")
	public int getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(int requestMode) {
		this.requestMode = requestMode;
	}

	/**
	 * 样本采样时间
	 */
	@Column(name = "EXECUTETIME")
	public Date getExecutetime() {
		return executetime;
	}

	public void setExecutetime(Date executetime) {
		this.executetime = executetime;
	}

	/**
	 * 采样者
	 */
	@Column(name = "EXECUTOR", length = 20)
	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	/**
	 * 样本接收时间
	 */
	@Column(name = "RECEIVETIME")
	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	/**
	 * 接收人
	 */
	@Column(name = "RECEIVER", length = 20)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * 就诊类型
	 */
	@Column(name = "STAYHOSPITALMODE", length = 1)
	public int getStayHospitalMode() {
		return stayHospitalMode;
	}

	public void setStayHospitalMode(int stayHospitalMode) {
		this.stayHospitalMode = stayHospitalMode;
	}

	/**
	 * 病人id
	 */
	@Column(name = "PATIENTID", length = 30)
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	/**
	 * 婴儿标识
	 */
	@Column(name = "INFANTFLAG", length = 1)
	public int getInfantFlag() {
		return infantFlag;
	}

	public void setInfantFlag(int infantFlag) {
		this.infantFlag = infantFlag;
	}

	/**
	 * 就诊科室
	 */
	@Column(name = "SECTION", length = 20)
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * 病床号
	 */
	@Column(name = "DEPART_BED", length = 10)
	public String getDepartBed() {
		return departBed;
	}

	public void setDepartBed(String departBed) {
		this.departBed = departBed;
	}

	/**
	 * 病人姓名
	 */
	@Column(name = "PATIENTNAME", length = 20)
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	/**
	 * 性别
	 */
	@Column(name = "SEX", length = 1)
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	/**
	 * 出生日期
	 */
	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 诊断
	 */
	@Column(name = "DIAGNOSTIC")
	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	/**
	 * 样本类型
	 */
	@Column(name = "SAMPLETYPE")
	public char getSampleType() {
		return sampleType;
	}

	public void setSampleType(char sampleType) {
		this.sampleType = sampleType;
	}

	/**
	 * 检验名称
	 */
	@Column(name = "EXAMINAIM")
	public String getExaminaim() {
		return examinaim;
	}

	public void setExaminaim(String examinaim) {
		this.examinaim = examinaim;
	}

	/**
	 * 样本号
	 */
	@Column(name = "SAMPLENO", length = 50)
	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	/**
	 * 结果状态
	 */
	@Column(name = "RESULTSTATUS")
	public int getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(int resultStatus) {
		this.resultStatus = resultStatus;
	}

	/**
	 * 审核者
	 */
	@Column(name = "CHECKOPERATOR", length = 20)
	public String getCheckOperator() {
		return checkOperator;
	}

	public void setCheckOperator(String checkOperator) {
		this.checkOperator = checkOperator;
	}

	/**
	 * 审核意见
	 */
	@Column(name = "CHECKEROPINION")
	public String getCheckerOpinion() {
		return checkerOpinion;
	}

	public void setCheckerOpinion(String checkerOpinion) {
		this.checkerOpinion = checkerOpinion;
	}

	/**
	 * 审核时间
	 */
	@Column(name = "CHECKTIME")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * 检验科室
	 */
	@Column(name = "LABDEPARTMENT", length = 30)
	public String getLabdepartMent() {
		return labdepartMent;
	}

	public void setLabdepartMent(String labdepartMent) {
		this.labdepartMent = labdepartMent;
	}

	/**
	 * 审核者2
	 */
	@Column(name = "CHKOPER2", length = 20)
	public String getChkoper2() {
		return chkoper2;
	}

	public void setChkoper2(String chkoper2) {
		this.chkoper2 = chkoper2;
	}

	/**
	 * 打印时间
	 */
	@Column(name = "PRINTTIME")
	public String getPrintTime() {
		return printTime;
	}

	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}

	/**
	 * 智能解释
	 */
	@Column(name = "INTELEXPLAIN")
	public String getIntelExplain() {
		return intelExplain;
	}

	public void setIntelExplain(String intelExplain) {
		this.intelExplain = intelExplain;
	}

	/**
	 * 自动审核所推出的规则id
	 */
	@Column(name = "RULEIDS")
	public String getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}

	/**
	 * 打印标识
	 */
	@Column(name = "ISPRINT", length = 1)
	public int getPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(int printFlag) {
		this.printFlag = printFlag;
	}

	/**
	 * 查看标识
	 */
	@Column(name = "VIEWFLAG", length = 1)
	public int getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}

	/**
	 * 审核状态
	 */
	@Column(name = "CHECKSTATUS")
	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	/**
	 * 审核标记
	 */
	@Column(name = "AUDITMARK")
	public int getAuditMark() {
		return auditMark;
	}

	public void setAuditMark(int auditMark) {
		this.auditMark = auditMark;
	}

	/**
	 * 审核状态
	 */
	@Column(name = "AUDITSTATUS")
	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * 需要标记的检验项目id
	 */
	@Column(name = "MARKTESTS")
	public String getMarkTests() {
		return markTests == null ? "" : markTests;
	}

	public void setMarkTests(String markTests) {
		this.markTests = markTests;
	}

	/**
	 * 危急值
	 */
	@Column(name = "CRITICALVALUES")
	public String getCriticalValues() {
		return criticalValues;
	}

	public void setCriticalValues(String criticalValues) {
		this.criticalValues = criticalValues;
	}

	/**
	 * 医疗序号
	 */
	@Column(name = "YLXH")
	public String getYlxh() {
		return ylxh;
	}

	public void setYlxh(String ylxh) {
		this.ylxh = ylxh;
	}

	/**
	 * 少做的项目
	 */
	@Column(name = "LACKNOTES")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 危急值标识
	 */
	@Column(name = "DANGERFLAG")
	public int getCriticalDealFlag() {
		return criticalDealFlag;
	}

	public void setCriticalDealFlag(int criticalDealFlag) {
		this.criticalDealFlag = criticalDealFlag;
	}

	/**
	 * 危急值处理结果
	 */
	@Column(name = "DANGERDEAL")
	public String getCriticalDeal() {
		return criticalDeal;
	}

	public void setCriticalDeal(String criticalDeal) {
		this.criticalDeal = criticalDeal;
	}
	
	/**
	 * 危急值处理时间
	 */
	@Column(name = "DANGERTIME")
	public Date getCriticalDealTime() {
		return criticalDealTime;
	}

	public void setCriticalDealTime(Date criticalDealTime) {
		this.criticalDealTime = criticalDealTime;
	}
	
	/**
	 * 危急值内容
	 */
	@Column(name = "DANGERCONTENT")
	public String getCriticalContent() {
		return criticalContent;
	}

	public void setCriticalContent(String criticalContent) {
		this.criticalContent = criticalContent;
	}
	
	/**
	 * 危急值处理者
	 */
	@Column(name = "DEALPERSON")
	public String getCriticalDealPerson() {
		return criticalDealPerson;
	}

	public void setCriticalDealPerson(String criticalDealPerson) {
		this.criticalDealPerson = criticalDealPerson;
	}

	/**
	 * 病历号
	 */
	@Column(name = "BLH")
	public String getBlh() {
		return blh;
	}

	public void setBlh(String blh) {
		this.blh = blh;
	}
	
	@Column(name = "CYCLE")
	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	/**
	 * 写会
	 */
	@Column(name = "WRITEBACK")
	public int getWriteBack() {
		return writeBack;
	}

	public void setWriteBack(int writeBack) {
		this.writeBack = writeBack;
	}

	/**
	 * 修改标记
	 */
	@Column(name = "MODIFYFLAG")
	public int getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(int modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	/**
	 * 运送时间
	 */
	@Column(name = "SENDTIME")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 科室接收时间
	 */
	@Column(name = "KSRECEIVETIME")
	public Date getKsReceiveTime() {
		return ksReceiveTime;
	}

	public void setKsReceiveTime(Date ksReceiveTime) {
		this.ksReceiveTime = ksReceiveTime;
	}
	
	/**
	 * 审核通过原因
	 */
	@Column(name = "PASSREASON")
	public String getPassReason() {
		return passReason;
	}

	public void setPassReason(String passReason) {
		this.passReason = passReason;
	}

	@Transient
	public int getAge() {
		if (birthday != null) {
			Calendar now = Calendar.getInstance();
			Calendar previous = Calendar.getInstance();
			previous.setTime(birthday);
			setAge(now.get(Calendar.YEAR) - previous.get(Calendar.YEAR));
		}
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Transient
	public String getAuditMarkValue() {
		String value = "";
		switch (getAuditMark()) {
		case 0:
			break;
		case 1:
			value = "自动";
			break;
		case 2:
			value = "差值";
			break;
		case 3:
			value = "比值";
			break;
		case 4:
			value = "少做";
			break;
		case 5:
			value = "复检";
			break;
		case 6:
			value = "危急";
			break;
		case 7:
			value = "警戒1";
			break;
		case 8:
			value = "警戒2";
			break;
		case 9:
			value = "极值";
			break;
		case 10:
			value = "自动b";
			break;
		}
		return value;
	}

	@Transient
	public String getAuditStatusValue() {
		String value = "";
		switch (getAuditStatus()) {
		case -1:
			value = "无结果";
			break;
		case 0:
			value = "未审核";
			break;
		case 1:
			value = "已通过";
			break;
		case 2:
			value = "未通过";
			break;
		default:
			value = "未审核";
			break;
		}
		return value;
	}

	@Transient
	public String getSexValue() {

		String sex = "";
		if (this.getSex() == 1)
			sex = "男";
		else if (this.getSex() == 2)
			sex = "女";
		else
			sex = "未知";

		return sex;
	}

	/**
	 * 该检验所做的检验项目结果
	 */
	@OneToMany(targetEntity = TestResult.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "intlab_patient_result", joinColumns = { @JoinColumn(name = "doc_id", referencedColumnName = "DOCTADVISENO") }, inverseJoinColumns = {
			@JoinColumn(name = "sample_no", referencedColumnName = "sampleNo"),
			@JoinColumn(name = "test_id", referencedColumnName = "testId") })
	public Set<TestResult> getResults() {
		return results;
	}

	public void setResults(Set<TestResult> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}
}
