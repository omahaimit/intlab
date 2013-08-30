package com.zju.catcher.entity.local;

public class MapPK {

    private long doctId;
    private String sampleNo;
    private String testId;

    public MapPK(long doctId, String sampleNo, String testId) {
        this.doctId = doctId;
        this.sampleNo = sampleNo;
        this.testId = testId;
    }

    public long getDoctId() {
        return doctId;
    }

    public void setDoctId(long doctId) {
        this.doctId = doctId;
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
        if (obj instanceof MapPK) {
            MapPK pk = (MapPK) obj;
            return this.doctId == pk.getDoctId() && sampleNo.equals(pk.getSampleNo())
                    && this.testId.equals(pk.getTestId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new Long(doctId).hashCode() + this.sampleNo.hashCode() + this.testId.hashCode();
    }
}
