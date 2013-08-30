package com.zju.model;

import java.io.Serializable;

/**
 * 检验结果主键
 */
public class TestResultPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sampleNo;
	private String testId;

	public TestResultPK() {
	}

	public TestResultPK(String sampleNo, String testId) {
		this.sampleNo = sampleNo;
		this.testId = testId;
	}

	/**
	 * 检验样本号
	 */
	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	/**
	 * 检验项目id
	 */
	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sampleNo == null) ? 0 : sampleNo.hashCode());
		result = prime * result + ((testId == null) ? 0 : testId.hashCode());
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
		TestResultPK other = (TestResultPK) obj;
		if (sampleNo == null) {
			if (other.sampleNo != null)
				return false;
		} else if (!sampleNo.equals(other.sampleNo))
			return false;
		if (testId == null) {
			if (other.testId != null)
				return false;
		} else if (!testId.equals(other.testId))
			return false;
		return true;
	}
}
