package com.zju.model;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.context.i18n.LocaleContextHolder;

import com.zju.model.Constants;

public class InvalidSamples {

	// Primary Key
	private String barCode;

	private Date rejectTime = new Date();
	private String containerTypeString;
	private int containerType;
	private String labelTypeString;
	private int labelType;
	private String requestionTypeString;
	private int requestionType;
	private String rejectSampleReasonString;
	private int rejectSampleReason;
	private String measureTakenString;
	private int measureTaken;
	private String notes;
	private String rejectPerson;
	private Locale locale = LocaleContextHolder.getLocale();

	private Patients patient = new Patients();
	private String buttonresult = "s";

	public String getButtonresult() {
		return buttonresult;
	}

	public void setButtonresult(String buttonresult) {
		this.buttonresult = buttonresult;
	}

	public String getContainerTypeString() {
		return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("containerType." + containerType);
	}

	public void setContainerTypeString(String containerTypeString) {
		this.containerTypeString = containerTypeString;
	}

	public String getLabelTypeString() {
		return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("labelType." + labelType);
	}

	public void setLabelTypeString(String labelTypeString) {
		this.labelTypeString = labelTypeString;
	}

	public String getRequestionTypeString() {
		return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("requestionType." + requestionType);
	}

	public void setRequestionTypeString(String requestionTypeString) {
		this.requestionTypeString = requestionTypeString;
	}

	public String getRejectSampleReasonString() {
		return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("sampleReason." + rejectSampleReason);
	}

	public void setRejectSampleReasonString(String rejectSampleReasonString) {
		this.rejectSampleReasonString = rejectSampleReasonString;
	}

	public String getMeasureTakenString() {
		return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("measureTaken." + measureTaken);
	}

	public void setMeasureTakenString(String measureTakenString) {
		this.measureTakenString = measureTakenString;
	}

	public Patients getPatient() {
		return patient;
	}

	public void setPatient(Patients patient) {
		this.patient = patient;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Date getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}

	public int getContainerType() {
		return containerType;
	}

	public void setContainerType(int containerType) {
		this.containerType = containerType;

	}

	public int getLabelType() {
		return labelType;
	}

	public void setLabelType(int labelType) {
		this.labelType = labelType;

	}

	public int getRequestionType() {
		return requestionType;
	}

	public void setRequestionType(int requestionType) {
		this.requestionType = requestionType;

	}

	public int getRejectSampleReason() {
		return rejectSampleReason;
	}

	public void setRejectSampleReason(int rejectSampleReason) {
		this.rejectSampleReason = rejectSampleReason;
	}

	public int getMeasureTaken() {
		return measureTaken;
	}

	public void setMeasureTaken(int measureTaken) {
		this.measureTaken = measureTaken;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getRejectPerson() {
		return rejectPerson;
	}

	public void setRejectPerson(String rejectPerson) {
		this.rejectPerson = rejectPerson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buttonresult == null) ? 0 : buttonresult.hashCode());
		result = prime * result + containerType;
		result = prime * result + ((containerTypeString == null) ? 0 : containerTypeString.hashCode());
		result = prime * result + labelType;
		result = prime * result + ((labelTypeString == null) ? 0 : labelTypeString.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + measureTaken;
		result = prime * result + ((measureTakenString == null) ? 0 : measureTakenString.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + ((rejectPerson == null) ? 0 : rejectPerson.hashCode());
		result = prime * result + rejectSampleReason;
		result = prime * result + ((rejectSampleReasonString == null) ? 0 : rejectSampleReasonString.hashCode());
		result = prime * result + ((rejectTime == null) ? 0 : rejectTime.hashCode());
		result = prime * result + requestionType;
		result = prime * result + ((requestionTypeString == null) ? 0 : requestionTypeString.hashCode());
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
		InvalidSamples other = (InvalidSamples) obj;
		if (buttonresult == null) {
			if (other.buttonresult != null)
				return false;
		} else if (!buttonresult.equals(other.buttonresult))
			return false;
		if (containerType != other.containerType)
			return false;
		if (containerTypeString == null) {
			if (other.containerTypeString != null)
				return false;
		} else if (!containerTypeString.equals(other.containerTypeString))
			return false;
		if (labelType != other.labelType)
			return false;
		if (labelTypeString == null) {
			if (other.labelTypeString != null)
				return false;
		} else if (!labelTypeString.equals(other.labelTypeString))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (measureTaken != other.measureTaken)
			return false;
		if (measureTakenString == null) {
			if (other.measureTakenString != null)
				return false;
		} else if (!measureTakenString.equals(other.measureTakenString))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (rejectPerson == null) {
			if (other.rejectPerson != null)
				return false;
		} else if (!rejectPerson.equals(other.rejectPerson))
			return false;
		if (rejectSampleReason != other.rejectSampleReason)
			return false;
		if (rejectSampleReasonString == null) {
			if (other.rejectSampleReasonString != null)
				return false;
		} else if (!rejectSampleReasonString.equals(other.rejectSampleReasonString))
			return false;
		if (rejectTime == null) {
			if (other.rejectTime != null)
				return false;
		} else if (!rejectTime.equals(other.rejectTime))
			return false;
		if (requestionType != other.requestionType)
			return false;
		if (requestionTypeString == null) {
			if (other.requestionTypeString != null)
				return false;
		} else if (!requestionTypeString.equals(other.requestionTypeString))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvalidSamples [barCode=" + barCode + ", rejectTime=" + rejectTime + ", containerTypeString="
				+ containerTypeString + ", containerType=" + containerType + ", labelTypeString=" + labelTypeString
				+ ", labelType=" + labelType + ", requestionTypeString=" + requestionTypeString + ", requestionType="
				+ requestionType + ", rejectSampleReasonString=" + rejectSampleReasonString + ", rejectSampleReason="
				+ rejectSampleReason + ", measureTakenString=" + measureTakenString + ", measureTaken=" + measureTaken
				+ ", notes=" + notes + ", rejectPerson=" + rejectPerson + ", locale=" + locale + ", patient=" + patient
				+ ", buttonresult=" + buttonresult + "]";
	}

}
