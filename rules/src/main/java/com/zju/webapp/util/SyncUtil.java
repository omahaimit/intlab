package com.zju.webapp.util;

//import com.zju.model.SyncSample;
import com.zju.service.SyncManager;

public class SyncUtil {

	//private static Log log = LogFactory.getLog(SyncUtil.class);
/*    private SyncManager manager = null;
    private String date;
    private String department;
    private String code;
    private final static int ALL_SAMPLE = -2;*/
    //private final static int NO_RESULT_SAMPLE = -1;
    //private final static int UNAUDIT_SAMPLE = 0;

    public SyncUtil(SyncManager manager, String date, String department, String code) {
/*        this.manager = manager;
        this.date = date;
        this.department = department;
        this.code = code;*/
    }

    /*public void Sync() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        try {
            if (sdf.format(today).equals(date)) {
                System.out.println("Just Update !");
                //JustUpdate();
            } else {
                System.out.println("Need Insert !");
                //NeedInsert();
            }
        } catch (Exception e) {
            log.error("更新数据失败", e);
        }
    }*/

/*    private void NeedInsert() {

        System.out.println("获取");
        List<SyncPatient> patients = manager.getPatientInfo(date, department, code);
        List<SyncResult> results = manager.getTestResult(date, department, code);
        List<Long> patientPK = manager.getExsitPatientPK(date, department, code, ALL_SAMPLE);
        //List<Long> noResultPK = manager.getExsitPatientPK(date, department, code, NO_RESULT_SAMPLE);
        List<SyncPK> resultPK = manager.getExsitResultPK(date, department, code);
        List<SyncMapPK> mapPK = manager.getExsitMapPK(date, code);

        Set<Long> exsitPatient = new HashSet<Long>(patientPK);
        //Set<Long> noResultPatient = new HashSet<Long>(noResultPK);
        Set<SyncPK> exsitResult = new HashSet<SyncPK>(resultPK);
        Set<SyncMapPK> exsitMap = new HashSet<SyncMapPK>(mapPK);
        Map<String, Set<Long>> exsitSample = new HashMap<String, Set<Long>>();
        //Map<Long, SyncPatient> allSample = new HashMap<Long, SyncPatient>();
        List<SyncPatient> insertPatients = new ArrayList<SyncPatient>();
        //List<SyncSample> insertSamples = new ArrayList<SyncSample>();
        List<SyncResult> insertResults = new ArrayList<SyncResult>();
        List<SyncPatient> updatePatients = new ArrayList<SyncPatient>();
        //List<SyncSample> updateSamples = new ArrayList<SyncSample>();
        List<SyncResult> updateResults = new ArrayList<SyncResult>();
        List<SyncMapPK> insertMap = new ArrayList<SyncMapPK>();

        log.info("过滤");
        for (SyncPatient p : patients) {
            long pk = p.getDOCTADVISENO();
            String sampleNo = p.getSAMPLENO();
            if (exsitPatient.contains(pk)) {
                updatePatients.add(p);
            } else {
                insertPatients.add(p);
                exsitPatient.add(pk);
            }
            if (exsitSample.containsKey(sampleNo)) {
                Set<Long> set = exsitSample.get(sampleNo);
                set.add(pk);
            } else {
                Set<Long> set = new HashSet<Long>();
                set.add(pk);
                exsitSample.put(sampleNo, set);
            }
        }

        for (SyncResult r : results) {

            String sampleNo = r.getSAMPLENO();
            String testId = r.getTESTID();
            SyncPK pk = new SyncPK(sampleNo, testId);

            if (StringUtils.isEmpty(testId)) {
                continue;
            }
            
            if (exsitResult.contains(pk)) {
                updateResults.add(r);
            } else {
                insertResults.add(r);
                exsitResult.add(pk);
            }

            if (exsitSample.containsKey(sampleNo)) {
                Set<Long> set = exsitSample.get(sampleNo);
                for (Long doctId : set) {
                    SyncMapPK mpk = new SyncMapPK(doctId, sampleNo, testId);
                    if (!exsitMap.contains(mpk)) {
                        insertMap.add(mpk);
                        exsitMap.add(mpk);
                    }
                }
            }
        }
        
        log.info("同步");
        manager.insertPatients(insertPatients);
        manager.updatePatients(updatePatients);
        manager.insertResults(insertResults);
        manager.updateResults(updateResults);
        manager.insertPKMap(insertMap);
    }*/
}
