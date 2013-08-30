package com.zju.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TestResult implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -3248928438618467686L;
	
	private String sampleNo;
    private String testId;
    private String testResult;
    private String resultFlag;
    private int testStatus;
    private char sampleType;
    private String refLo;
    private String refHi;
    private Date measureTime;
    private String unit;
    
    private String name;

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public int getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(int testStatus) {
        this.testStatus = testStatus;
    }

    public char getSampleType() {
        return sampleType;
    }

    public void setSampleType(char sampleType) {
        this.sampleType = sampleType;
    }

    public String getRefLo() {
        return refLo;
    }

    public void setRefLo(String refLo) {
        this.refLo = refLo;
    }

    public String getRefHi() {
        return refHi;
    }

    public void setRefHi(String refHi) {
        this.refHi = refHi;
    }
    
    public String getScope() {
    	if (refLo != null && refHi != null) {
    		return refLo + "-" + refHi;
    	} else if (refLo != null) {
    		return refLo;
    	} else {
    		return refHi;
    	}
    }

    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHasHigh() {
		String flag = " ";
		try {
			if (!StringUtils.isEmpty(refHi) && !StringUtils.isEmpty(refLo)) {
				Double hiDb = Double.valueOf(refHi);
				Double loDb = Double.valueOf(refLo);
				if (!StringUtils.isEmpty(testResult)) {
					Double result = Double.valueOf(testResult);
					if (result < loDb) {
						flag = "↓";
					} else if (result > hiDb) {
						flag = "↑";
					}
				}
			}
		} catch (Exception e) {}
		return flag;
	}

}
