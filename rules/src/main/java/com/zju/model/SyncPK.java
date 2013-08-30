package com.zju.model;

public class SyncPK {
    private String sampleNo = "";
    private String testId = "";

    public SyncPK(String sampleNo, String testId) {
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
        if (obj instanceof SyncPK) {

            SyncPK pk = (SyncPK) obj;
            return this.sampleNo.equals(pk.getSampleNo()) && this.testId.equals(pk.getTestId());

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.sampleNo.hashCode() + this.testId.hashCode();
    }
}
