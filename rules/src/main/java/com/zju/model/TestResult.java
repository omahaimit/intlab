package com.zju.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.zju.model.BaseObject;

/**
 * 检验项目结果
 */
@Entity
@IdClass(TestResultPK.class)
@Table(name = "L_TESTRESULT")
public class TestResult extends BaseObject {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String sampleNo;
    private String testId;
    private String testResult;
    private String resultFlag;
    private int testStatus;
    private String correctFlag;
    private char sampleType;
    private String refLo; // 参考范围低值
    private String refHi; // 参考范围高值
    private String deviceId; // 设备号
    private Date measureTime; // 检验时间
    private String operator; // 操作者
    private String unit;
    private String chineseName;
    private int isprint;
    private int editMark;

    /**
	 * 主键、检验样本号
	 */
    @Id
    @Column(name = "SAMPLENO")
    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    /**
	 * 主键、检验项目id
	 */
    @Id
    @Column(name = "TESTID")
    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    /**
	 * 检验项目结果
	 */
    @Column(name = "TESTRESULT")
    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    /**
	 * 检验项目结果标记
	 */
    @Column(name = "RESULTFLAG")
    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    /**
	 * 检验项目状态
	 */
    @Column(name = "TESTSTATUS")
    public int getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(int testStatus) {
        this.testStatus = testStatus;
    }

    /**
	 * 检验项目正确标识
	 */
    @Column(name = "CORRECTFLAG")
    public String getCorrectFlag() {
        return correctFlag;
    }

    public void setCorrectFlag(String correctFlag) {
        this.correctFlag = correctFlag;
    }

    /**
	 * 检验项目样本类型
	 */
    @Column(name = "SAMPLETYPE")
    public char getSampleType() {
        return sampleType;
    }

    public void setSampleType(char sampleType) {
        this.sampleType = sampleType;
    }

    /**
	 * 检验项目参考低值
	 */
    @Column(name = "REFLO")
    public String getRefLo() {
        return refLo;
    }

    public void setRefLo(String refLo) {
        this.refLo = refLo;
    }

    /**
	 * 检验项目参考高值
	 */
    @Column(name = "REFHI")
    public String getRefHi() {
        return refHi;
    }

    public void setRefHi(String refHi) {
        this.refHi = refHi;
    }

    /**
	 * 检验项目仪器号
	 */
    @Column(name = "DEVICEID")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
	 * 检验项目测量时间
	 */
    @Column(name = "MEASURETIME")
    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    /**
	 * 检验项目测量者
	 */
    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
	 * 检验项目单位
	 */
    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
	 * 检验项目打印标识
	 */
    @Column(name = "ISPRINT")
    public int getIsprint() {
		return isprint;
	}

	public void setIsprint(int isprint) {
		this.isprint = isprint;
	}
    
	/**
	 * 检验项目编辑标记
	 */
    @Column(name = "EDITMARK")
    public int getEditMark() {
		return editMark;
	}

	public void setEditMark(int editMark) {
		this.editMark = editMark;
	}


    /**
     * Returns the reference.
     * 
     * @return refLo +" -- " + refHi
     */
    @Transient
    public String getReference() {
        if (refLo.isEmpty() && refHi.isEmpty()) {
            return "-";
        } else if (refLo.isEmpty() && (!refHi.isEmpty())) {
            return refHi;
        } else if (refHi.isEmpty() && (!refLo.isEmpty())) {
            return refLo;
        } else {
            return refLo + "--" + refHi;
        }
    }

    /**
     * Returns the result view.
     * 
     * @return result + '' + resultFlag
     */
    @Transient
    public String getResultView() {
        if (resultFlag.isEmpty()) {
            return testResult;
        }
        if (resultFlag.charAt(0) == 'B') {
            return "↑ " + testResult;
        } else if (resultFlag.charAt(0) == 'C') {
            return "↓ " + testResult;
        } else {
            return testResult;
        }
    }

    /**
     * Return the chineseName of the testId
     * 
     */
    @Transient
    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
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
