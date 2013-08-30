package com.zju.catcher.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.catcher.entity.local.Distribute;
import com.zju.catcher.entity.local.PatientSample;
import com.zju.catcher.entity.z1.ReferenceValue;
import com.zju.catcher.entity.z1.TestResult;
import com.zju.catcher.service.local.PatientSampleService;
import com.zju.catcher.service.local.TestDataService;
import com.zju.catcher.service.local.UserService;
import com.zju.catcher.service.z1.ReferenceValueService;
import com.zju.catcher.service.z1.TestResultService;

@Controller
public class DistributeController {

	private static final float MaxFLOAT = 999999.0f;

	@RequestMapping(value = "/distribute.htm", method = RequestMethod.GET)
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return new ModelAndView("distribute");
	} 
	
	@ResponseBody
	@RequestMapping(value = "/ajax/distribute/start.htm", method = RequestMethod.GET)
	public void writeBack(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("Start...");
		int sameCount = 0;
		int passCount = 0;
		int unPassCount = 0;
		for (int i = 201; i <= 300; i++) {
			String sampleNo = "20130419CBD" + String.format("%03d", i);
			PatientSample info = patientSampleService.getPatientInfo(sampleNo);
			int sex = (info.getPatientInfo().getSEX() == 2) ? 2 : 1;
			double rate = AuditByBayes(sampleNo, sex);
			if (info.getAuditMark() == 1 && rate > 0.90) {
				passCount++;
				sameCount++;
			} else if (info.getAuditMark() > 1 && rate < 0.90) {
				unPassCount++;
				sameCount++;
			} else {
				//System.out.println(sampleNo + " : " + rate);
			}
			if (info.getAuditMark() == 1 && rate < 0.90) {
				System.out.println(sampleNo + " : " + rate);
			}
		}
		System.out.println("Pass:	" + passCount);
		System.out.println("Unpass:	" + unPassCount);
		System.out.println("Same:	" + sameCount);
		printFloat();
	}

	@SuppressWarnings("unused")
	private void getCount() {
		Map<String, List<Distribute>> disMap = new HashMap<String, List<Distribute>>();
		List<TestResult> results =  testResultService.getResult("201304__CBC");
		System.out.println(results.size());
		for(TestResult tr : results) {
			String key = tr.getTESTID();
			if (!disMap.containsKey(key)) {
				disMap.put(key, userService.getDistribute(key));
			}
			if (isFloat(tr.getTESTRESULT())) {
				int sex = (tr.getEDITMARK() != 2) ? 1 : 2;
				int mark = tr.getTESTSTATUS();
				float val = Float.parseFloat(tr.getTESTRESULT());
				for (Distribute dis : disMap.get(key)) {
					if (dis.getSEX() == sex && dis.isIn(val)) {
						if (mark == 1)
							dis.addPass();
						else if (mark > 1)
							dis.addUnpass();
						break;
					}
				}
			}
		}
		List<Distribute> updateDisList = new ArrayList<Distribute>();
		for (List<Distribute> dis : disMap.values()) {
			updateDisList.addAll(dis);
		}
		System.out.println(updateDisList.size());
		userService.updateDistribute(updateDisList);
	}
	
	public double AuditByBayes(String sampleNo, int sex) {
		List<TestResult> results = testDataService.getSample(sampleNo);
		Map<String, List<Distribute>> disMap = new HashMap<String, List<Distribute>>();
		Map<String, Float> passRateMap = new HashMap<String, Float>();

		for (TestResult tr : results) {
			String key = tr.getTESTID();
			if (!disMap.containsKey(key)) {
				disMap.put(key, userService.getDistribute(key));
			}
			
			if (!isFloat(tr.getTESTRESULT())) continue;

			sex = (sex != 2) ? 1 : 2;
			float val = Float.parseFloat(tr.getTESTRESULT());
			int passCount = 0, unPassCount = 0;
			int passTotal = 0, unPassTotal = 0;
			boolean hasGetCount = false;
			for (Distribute dis : disMap.get(key)) {
				if (!hasGetCount && dis.getSEX() == sex && dis.isIn(val)) {
					passCount = dis.getPASSCOUNT();
					unPassCount = dis.getUNPASSCOUNT();
					hasGetCount = true;
				}
				passTotal += dis.getPASSCOUNT();
				unPassTotal += dis.getUNPASSCOUNT();
			}
			
			if (passTotal == 0 || unPassTotal == 0 || passCount == 0 || unPassCount == 0) continue;
			
			float rate = passCount * 1.0f / passTotal / (passCount * 1.0f / passTotal + unPassCount * 1.0f / unPassTotal);
			passRateMap.put(key,rate);
		}
		
		double d1 = 1.0;
		double d2 = 1.0;
		for (Float f : passRateMap.values()) {
			d1 *= f.doubleValue();
			d2 *= 1.0 - f.doubleValue();
		}
		
		return d1 / (d1 + d2);
	}
	
	@SuppressWarnings("unused")
	private void getScope() {
		
		Map<String, List<ReferenceValue>> refMap = new HashMap<String, List<ReferenceValue>>();
		List<ReferenceValue> refList = referenceValueService.getAll();
		for (ReferenceValue ref : refList) {
			if (refMap.containsKey(ref.getTESTID())) {
				List<ReferenceValue> list = refMap.get(ref.getTESTID());
				list.add(ref);
			} else {
				List<ReferenceValue> list = new ArrayList<ReferenceValue>();
				list.add(ref);
				refMap.put(ref.getTESTID(), list);
			}
		}

		List<Distribute> disList = new ArrayList<Distribute>();

		for (String key : referenceValueService.getIdList()) {
			List<ReferenceValue> rList = refMap.get(key);
			float mLo = Float.MAX_VALUE;
			float mHi = - Float.MAX_VALUE;
			float fLo = Float.MAX_VALUE;
			float fHi = - Float.MAX_VALUE;
			boolean mFlag = false;
			boolean fFlag = false;
			
			for (ReferenceValue val : rList) {
				if (isFloat(val.getMREFLO0()) && isFloat(val.getMREFHI0()) && val.getMREFLO0().indexOf('E') == -1 && val.getMREFHI0().indexOf('E') == -1) {
					mFlag = true;
					float _mlo = Float.parseFloat(val.getMREFLO0());
					float _mhi = Float.parseFloat(val.getMREFHI0());
					if (_mlo < mLo) {
						mLo = _mlo;
					}
					if (_mhi > mHi) {
						mHi = _mhi;
					}
				}
				if (isFloat(val.getFREFLO0()) && isFloat(val.getFREFHI0())) {
					fFlag = true;
					float _flo = Float.parseFloat(val.getFREFLO0());
					float _fhi = Float.parseFloat(val.getFREFHI0());
					if (_flo < fLo) {
						fLo = _flo;
					}
					if (_fhi > fHi) {
						fHi = _fhi;
					}
				}
			}
			
			if (mFlag && fFlag) {
				division(disList, key, mLo, mHi, fLo, fHi);
			}
		}
		userService.saveDistribute(disList);
	}
	
	private void printFloat() {

//		(一):
//
//		   float   a   =   123.2334f; 
//		      float   b   =   (float)(Math.round(a*100))/100;(这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
//
//		(二):
//		  import   java.text.DecimalFormat;      
//		  String   a   =   new   DecimalFormat("###,###,###.##").format(100.12345   );
//
//		(三):
//
//		float   ft   =   134.3435f; 
//		  int   scale   =   2;//设置位数 
//		  int   roundingMode   =   4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等. 
//		  BigDecimal   bd   =   new   BigDecimal((double)ft); 
//		  bd   =   bd.setScale(scale,roundingMode); 
//		  ft   =   bd.floatValue();  
		float f = Float.parseFloat("6.90E7");
		System.out.println((float)(Math.round(f*1000))/1000 + ";");
		System.out.println("-----------------------------------");
		
		float v = 6 / 13;
		System.out.println(v);
		System.out.println(6 * 1.0f / 13);
		
	}

	private void division(List<Distribute> disList, String key, float mLo, float mHi, float fLo, float fHi) {
		
		if (mHi - mLo > 0.001 && fHi - fLo > 0.001) {

			System.out.println(key);
			float mp = (mHi - mLo) / 10;
			float fp = (fHi - fLo) / 10;
			
			float ml = mLo - 5 * mp;
			float mh = mHi - 5 * mp;
			float fl = fLo - 5 * fp;
			float fh = fHi - 5 * fp;
			float add;
			
			for (int i = 0; i < 10; i++) {
				Distribute dis = new Distribute();
				dis.setTESTID(key);
				dis.setSEX(1);
				dis.setPASSCOUNT(0);
				dis.setUNPASSCOUNT(0);
				add = ml + mp;
				dis.setSCOPEHI(add);
				dis.setSCOPELO(ml);
				dis.setSCOPENO(i + 1);
				if (i == 0) dis.setSCOPELO(-MaxFLOAT);
				disList.add(dis);
				ml = add;
			}
			for (int i = 0; i < 10; i++) {
				Distribute dis = new Distribute();
				dis.setTESTID(key);
				dis.setSEX(1);
				dis.setPASSCOUNT(0);
				dis.setUNPASSCOUNT(0);
				add = mh + mp;
				dis.setSCOPEHI(add);
				dis.setSCOPELO(mh);
				dis.setSCOPENO(i + 11);
				if (i == 0) dis.setSCOPELO(ml);
				if (i == 9) dis.setSCOPEHI(MaxFLOAT);
				disList.add(dis);
				mh = add;
			}
			
			for (int i = 0; i < 10; i++) {
				Distribute dis = new Distribute();
				dis.setTESTID(key);
				dis.setSEX(2);
				dis.setPASSCOUNT(0);
				dis.setUNPASSCOUNT(0);
				add = fl + fp;
				dis.setSCOPEHI(add);
				dis.setSCOPELO(fl);
				dis.setSCOPENO(i + 21);
				if (i == 0) dis.setSCOPELO(-MaxFLOAT);
				disList.add(dis);
				fl = add;
			}
			for (int i = 0; i < 10; i++) {
				Distribute dis = new Distribute();
				dis.setTESTID(key);
				dis.setSEX(2);
				dis.setPASSCOUNT(0);
				dis.setUNPASSCOUNT(0);
				add = fh + fp;
				dis.setSCOPEHI(add);
				dis.setSCOPELO(fh);
				dis.setSCOPENO(i + 31);
				if (i == 0) dis.setSCOPELO(fl);
				if (i == 9) dis.setSCOPEHI(MaxFLOAT);
				disList.add(dis);
				fh = add;
			}
		}
	}
	
	private boolean isFloat(String value) {
		try {
			Float.parseFloat(value);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	@Autowired
	private UserService userService = null;
	
	@Autowired
	private ReferenceValueService referenceValueService = null;
	
	@Autowired
	private PatientSampleService patientSampleService = null;
	
	@Autowired
	private TestResultService testResultService = null;

	@Autowired
	private TestDataService testDataService = null;
}
