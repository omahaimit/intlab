package com.zju.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.context.i18n.LocaleContextHolder;

import com.zju.model.Constants;

public class Patients {

	// Primary Key
	private long barCode;

	private String section;
	private String labDepartment = "1300000";
	private String labDepartmentStr;
	private int result;

	private String patientName;
	private int sex = 3;
	private String sexStr;
	private int age;
	private String examinaim;
	private String receivePerson;
	private Date receiveTime;
	private String sampleNo;
	private char sampleType = 'C';
	private String sampleTypeString;
	private String treatmentType = "1";
	private String treatmentTypeString;
	private Locale locale = LocaleContextHolder.getLocale();
	private Date birth;

	public String getSampleTypeString() {
		sampleTypeString = "" + sampleType;
		if (ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).containsKey("sampleType." + sampleType)) {
			sampleTypeString = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString(
					"sampleType." + sampleType);
		}
		return sampleTypeString;
	}

	public void setSampleTypeString(String sampleTypeString) {
		this.sampleTypeString = sampleTypeString;
	}

	public String getTreatmentTypeString() {
		treatmentTypeString = "" + treatmentType;

		if (ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).containsKey("treatmentType." + treatmentType)) {
			treatmentTypeString = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString(
					"treatmentType." + treatmentType);
		}
		return treatmentTypeString;
	}

	public void setTreatmentTypeString(String treatmentTypeString) {
		this.treatmentTypeString = treatmentTypeString;
	}

	public long getBarCode() {
		return barCode;
	}

	public void setBarCode(long barCode) {
		this.barCode = barCode;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getLabDepartment() {
		return labDepartment;
	}

	public void setLabDepartment(String labDepartment) {
		this.labDepartment = labDepartment;
	}

	public String getLabDepartmentStr() {
		labDepartmentStr = "" + labDepartment;
		if (ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).containsKey("labDepartment." + labDepartment)) {
			labDepartmentStr = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString(
					"labDepartment." + labDepartment);
		}
		return labDepartmentStr;
	}

	public void setLabDepartmentStr(String labDepartmentStr) {
		this.labDepartmentStr = labDepartmentStr;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getSexStr() {
		try {
			sexStr = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("sex." + sex);

		} catch (Exception e) {
			sexStr = "" + sex;

		}
		return sexStr;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public int getAge() {
		if (birth != null) {
			Calendar now = Calendar.getInstance();
			Calendar previous = Calendar.getInstance();
			previous.setTime(birth);
			setAge(now.get(Calendar.YEAR) - previous.get(Calendar.YEAR));
		}
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getExaminaim() {
		return examinaim;
	}

	public void setExaminaim(String examinaim) {
		this.examinaim = examinaim;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	public char getSampleType() {
		return sampleType;
	}

	public void setSampleType(char sampleType) {
		this.sampleType = sampleType;
	}

	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((birth == null) ? 0 : birth.hashCode());
		result = prime * result + ((examinaim == null) ? 0 : examinaim.hashCode());
		result = prime * result + ((labDepartment == null) ? 0 : labDepartment.hashCode());
		result = prime * result + ((labDepartmentStr == null) ? 0 : labDepartmentStr.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
		result = prime * result + ((receivePerson == null) ? 0 : receivePerson.hashCode());
		result = prime * result + ((receiveTime == null) ? 0 : receiveTime.hashCode());
		result = prime * result + this.result;
		result = prime * result + ((sampleNo == null) ? 0 : sampleNo.hashCode());
		result = prime * result + sampleType;
		result = prime * result + ((sampleTypeString == null) ? 0 : sampleTypeString.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		result = prime * result + sex;
		result = prime * result + ((sexStr == null) ? 0 : sexStr.hashCode());
		result = prime * result + ((treatmentType == null) ? 0 : treatmentType.hashCode());
		result = prime * result + ((treatmentTypeString == null) ? 0 : treatmentTypeString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patients other = (Patients) obj;
		if (age != other.age)
			return false;
		if (birth == null) {
			if (other.birth != null)
				return false;
		} else if (!birth.equals(other.birth))
			return false;
		if (examinaim == null) {
			if (other.examinaim != null)
				return false;
		} else if (!examinaim.equals(other.examinaim))
			return false;
		if (labDepartment == null) {
			if (other.labDepartment != null)
				return false;
		} else if (!labDepartment.equals(other.labDepartment))
			return false;
		if (labDepartmentStr == null) {
			if (other.labDepartmentStr != null)
				return false;
		} else if (!labDepartmentStr.equals(other.labDepartmentStr))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (patientName == null) {
			if (other.patientName != null)
				return false;
		} else if (!patientName.equals(other.patientName))
			return false;
		if (receivePerson == null) {
			if (other.receivePerson != null)
				return false;
		} else if (!receivePerson.equals(other.receivePerson))
			return false;
		if (receiveTime == null) {
			if (other.receiveTime != null)
				return false;
		} else if (!receiveTime.equals(other.receiveTime))
			return false;
		if (result != other.result)
			return false;
		if (sampleNo == null) {
			if (other.sampleNo != null)
				return false;
		} else if (!sampleNo.equals(other.sampleNo))
			return false;
		if (sampleType != other.sampleType)
			return false;
		if (sampleTypeString == null) {
			if (other.sampleTypeString != null)
				return false;
		} else if (!sampleTypeString.equals(other.sampleTypeString))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (sex != other.sex)
			return false;
		if (sexStr == null) {
			if (other.sexStr != null)
				return false;
		} else if (!sexStr.equals(other.sexStr))
			return false;
		if (treatmentType == null) {
			if (other.treatmentType != null)
				return false;
		} else if (!treatmentType.equals(other.treatmentType))
			return false;
		if (treatmentTypeString == null) {
			if (other.treatmentTypeString != null)
				return false;
		} else if (!treatmentTypeString.equals(other.treatmentTypeString))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Patients [barCode=" + barCode + ", section=" + section + ", labDepartment=" + labDepartment
				+ ", labDepartmentStr=" + labDepartmentStr + ", result=" + result + ", patientName=" + patientName
				+ ", sex=" + sex + ", sexStr=" + sexStr + ", age=" + age + ", examinaim=" + examinaim
				+ ", receivePerson=" + receivePerson + ", receiveTime=" + receiveTime + ", sampleNo=" + sampleNo
				+ ", sampleType=" + sampleType + ", sampleTypeString=" + sampleTypeString + ", treatmentType="
				+ treatmentType + ", treatmentTypeString=" + treatmentTypeString + ", locale=" + locale + ", birth="
				+ birth + "]";
	}

}