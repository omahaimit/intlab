package com.zju.catcher.entity.local;

public class PK {

    private String sampleNo;
    private String testId;

    public PK (String sampleNo, String testId) {
        this.sampleNo = sampleNo;
        this.testId = testId;
    }
    
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PK) {
            return this.sampleNo.equals(((PK) obj).getSampleNo()) && this.testId.equals(((PK) obj).getTestId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.sampleNo.hashCode() + this.testId.hashCode();
    }
}
