package com.zju.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zju.model.Describe;
import com.zju.model.PatientInfo;
import com.zju.model.ReferenceValue;
import com.zju.model.TestResult;
import com.zju.service.SyncManager;

public class FillFieldUtil {

	private Map<String, Describe> desMap = null;
	private Map<String, List<ReferenceValue>> refMap = null;
	private static FillFieldUtil util = null;

	private FillFieldUtil(List<Describe> desList, List<ReferenceValue> refList) {
		desMap = new HashMap<String, Describe>();
		refMap = new HashMap<String, List<ReferenceValue>>();
		for (Describe des : desList) {
			desMap.put(des.getTESTID(), des);
		}
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
	}

	public synchronized static FillFieldUtil getInstance(List<Describe> desList, List<ReferenceValue> refList) {
		if (util == null) {
			util = new FillFieldUtil(desList, refList);
		}
		return util;
	}

	public TestResult fillResult(TestResult result, PatientInfo info) {

		// 完善字段数据
		int li_direct = fillReference(result, info.getAge(), info.getCycle(), info.getSex());
		filleResultFlag(result, li_direct);
		return result;
	}

	private int fillReference(TestResult result, int age, int cycle, int sex) {
		int direct = 0;
		String testid = result.getTestId();
		String value = result.getTestResult();
		if (refMap.containsKey(testid)) {
			List<ReferenceValue> referList = refMap.get(testid);
			int linshi = 200;
			ReferenceValue reference = referList.get(0);
			for (ReferenceValue r : referList) {
				if (age - r.getREFAGE() > 0 && age - r.getREFAGE() < linshi) {
					reference = r;
					linshi = age - r.getREFAGE();
				}
			}
			if (sex == 2) { // 性别：女
				switch (cycle) {
				case 0:
					result.setRefLo(reference.getFREFLO0());
					result.setRefHi(reference.getFREFHI0());
					direct = reference.getDIRECT();
					break;
				case 1:
					result.setRefLo(reference.getFREFLO1());
					result.setRefHi(reference.getFREFHI1());
					direct = reference.getDIRECT();
					break;
				case 2:
					result.setRefLo(reference.getFREFLO2());
					result.setRefHi(reference.getFREFHI2());
					direct = reference.getDIRECT();
					break;
				case 3:
					result.setRefLo(reference.getFREFLO3());
					result.setRefHi(reference.getFREFHI3());
					direct = reference.getDIRECT();
					break;
				case 4:
					result.setRefLo(reference.getFREFLO4());
					result.setRefHi(reference.getFREFHI4());
					direct = reference.getDIRECT();
					break;
				case 5:
					result.setRefLo(reference.getFREFLO5());
					result.setRefHi(reference.getFREFHI5());
					direct = reference.getDIRECT();
					break;
				case 6:
					result.setRefLo(reference.getFREFLO6());
					result.setRefHi(reference.getFREFHI6());
					direct = reference.getDIRECT();
					break;
				case 7:
					result.setRefLo(reference.getFREFLO7());
					result.setRefHi(reference.getFREFHI7());
					direct = reference.getDIRECT();
					break;
				case 8:
					result.setRefLo(reference.getFREFLO8());
					result.setRefHi(reference.getFREFHI8());
					direct = reference.getDIRECT();
				}
				if ((result.getRefHi() == null || result.getRefHi().isEmpty())
						&& (result.getRefHi() == null || result.getRefHi().isEmpty())) {
					result.setRefLo(reference.getFREFLO0());
					result.setRefHi(reference.getFREFHI0());
					direct = reference.getDIRECT();
				}
			} else { // 性别：男或未知
				switch (cycle) {
				case 0:
					result.setRefLo(reference.getMREFLO0());
					result.setRefHi(reference.getMREFHI0());
					direct = reference.getDIRECT();
					break;
				case 1:
					result.setRefLo(reference.getMREFLO1());
					result.setRefHi(reference.getMREFHI1());
					direct = reference.getDIRECT();
					break;
				case 2:
					result.setRefLo(reference.getMREFLO2());
					result.setRefHi(reference.getMREFHI2());
					direct = reference.getDIRECT();
					break;
				case 3:
					result.setRefLo(reference.getMREFLO3());
					result.setRefHi(reference.getMREFHI3());
					direct = reference.getDIRECT();
					break;
				case 4:
					result.setRefLo(reference.getMREFLO4());
					result.setRefHi(reference.getMREFHI4());
					direct = reference.getDIRECT();
					break;
				case 5:
					result.setRefLo(reference.getMREFLO5());
					result.setRefHi(reference.getMREFHI5());
					direct = reference.getDIRECT();
					break;
				case 6:
					result.setRefLo(reference.getMREFLO6());
					result.setRefHi(reference.getMREFHI6());
					direct = reference.getDIRECT();
					break;
				case 7:
					result.setRefLo(reference.getMREFLO7());
					result.setRefHi(reference.getMREFHI7());
					direct = reference.getDIRECT();
					break;
				case 8:
					result.setRefLo(reference.getMREFLO8());
					result.setRefHi(reference.getMREFHI8());
					direct = reference.getDIRECT();
				}
				if ((result.getRefHi() == null || result.getRefHi().isEmpty())
						&& (result.getRefHi() == null || result.getRefHi().isEmpty())) {
					result.setRefLo(reference.getMREFLO0());
					result.setRefHi(reference.getMREFHI0());
					direct = reference.getDIRECT();
				}
			}
			
			String reflo = result.getRefLo();
			if (value != null && value.length() > 0) {
				if (value.charAt(0) == '.') {
					value = "0" + value;
				}
				
				if (reflo != null) {
					if (reflo.contains(".") && reflo.split("[.]").length > 1) {
						int round = reflo.split("[.]")[1].length();
						double f = Double.parseDouble(value);
						double p = Math.pow(10, round);
						value = String.valueOf((double)(Math.round(f*p))/p);
					} else {
						value = value.split("[.]")[0];
					}
				}
			}
		} 
		/*else {
			if (value.contains(".")) {
				String[] array = value.split("[.]");
				value = array[0] + "." + array[1].substring(0, 2);
				char c = array[1].charAt(2);
				if(c >= '5' && c <= '9') {
					double f = Double.parseDouble(value);
					f = f + 1/Math.pow(10, 2);
					value = String.valueOf(f);
				}
			}
		}*/
		result.setTestResult(value);
		return direct;
	}

	private void filleResultFlag(TestResult result, int li_direct) {

		Describe des = desMap.get(result.getTestId());
		String ls_result = result.getTestResult();
		String ls_reflo = result.getRefLo();
		String ls_refhi = result.getRefHi();
		String resultFlag = result.getResultFlag();
		char[] flags;
		
		if (resultFlag != null && resultFlag.length() == 6) {
			flags = result.getResultFlag().toCharArray();
		} else {
			flags = new char[] { 'A', 'A', 'A', 'A', 'A', 'A' };
		}
		
		if (des != null) {
			result.setIsprint(des.getISPRINT());
		} else {
			result.setIsprint(0);
		}
		
		if (ls_reflo == null || ls_reflo.trim().length() == 0) {
			ls_reflo = "";
			result.setRefLo(ls_reflo);
		}
		if (ls_refhi == null || ls_refhi.trim().length() == 0) {
			ls_refhi = "";
			result.setRefHi(ls_refhi);
		}
		if (ls_result == null) {
			ls_result = "";
		}
		if (ls_result.indexOf("<") == 0 || ls_result.indexOf(">") == 0) {
			ls_result = ls_result.substring(1);
		}
		
		if (isDouble(ls_result) && des != null) {
			double ld_result = dbl(ls_result);
			if (des.getWARNHI3() != null && des.getWARNHI3().trim().length() != 0 && ld_result > dbl(des.getWARNHI3())) {
				flags[1] = 'D';
			} else if (des.getWARNHI2() != null && des.getWARNHI2().trim().length() != 0 && ld_result > dbl(des.getWARNHI2())) {
				flags[1] = 'C';
			} else if (des.getWARNHI1() != null && des.getWARNHI1().trim().length() != 0 && ld_result > dbl(des.getWARNHI1())) {
				flags[1] = 'B';
			} else if (des.getWARNLO3() != null && des.getWARNLO3().trim().length() != 0 && ld_result < dbl(des.getWARNLO3())) {
				flags[1] = 'D';
			} else if (des.getWARNLO2() != null && des.getWARNLO2().trim().length() != 0 && ld_result < dbl(des.getWARNLO2())) {
				flags[1] = 'C';
			} else if (des.getWARNLO1() != null && des.getWARNLO1().trim().length() != 0 && ld_result < dbl(des.getWARNLO1())) {
				flags[1] = 'B';
			} else {
				flags[1] = 'A';
			}
			if (StringUtils.isEmpty(ls_reflo) || ".".equals(ls_reflo)) {
				flags[0] = 'A';
			} else if (des.getPRINTORD() < 1000) {
				if (ld_result < dbl(ls_reflo)) {
					switch (li_direct) {
					case 0:
						flags[0] = 'C';
						break;
					case 1:
						flags[0] = 'A';
						break;
					case 2:
						flags[0] = 'B';
						break;
					default:
						flags[0] = 'C';
						break;
					}
				} else if (ld_result > dbl(ls_refhi)) {
					switch (li_direct) {
					case 0:
						flags[0] = 'B';
						break;
					case 1:
						flags[0] = 'B';
						break;
					case 2:
						flags[0] = 'A';
						break;
					default:
						flags[0] = 'B';
						break;
					}
				} else {
					flags[0] = 'A';
				}
			} else {
				if (ld_result < dbl(ls_reflo)) {
					switch (li_direct) {
					case 0:
						flags[0] = 'A';
						break;
					case 1:
						flags[0] = 'A';
						break;
					case 2:
						flags[0] = 'B';
						break;
					default:
						flags[0] = 'A';
						break;
					}
				} else if (ld_result > dbl(ls_refhi)) {
					switch (li_direct) {
					case 0:
						flags[0] = 'B';
						break;
					case 1:
						flags[0] = 'B';
						break;
					case 2:
						flags[0] = 'A';
						break;
					default:
						flags[0] = 'B';
						break;
					}
				} else {
					flags[0] = 'A';
				}
			}
		} else {
			flags[0] = 'A';
			if (ls_result.indexOf("+") > -1 || ls_result.indexOf("阳") > -1) {
				flags[1] = 'B';
			} else if (ls_result.indexOf("-") > -1 || ls_result.indexOf("阴") > -1) {
				flags[1] = 'A';
			} else {
				flags[1] = 'B';
			}
		}

		if (des != null && des.getWARNHI1() != null && des.getWARNHI2() != null && des.getWARNHI2() != null
				&& des.getWARNLO1() != null && des.getWARNLO2() != null && des.getWARNLO3() != null) {
			try {
				if (isDouble(ls_result)) {
					char ls_warn;
					double ld_result = dbl(ls_result);
					if (ld_result <= dbl(des.getWARNHI1())
							&& ld_result >= dbl(des.getWARNLO1())) {
						ls_warn = 'A';
					} else if (ld_result <= dbl(des.getWARNHI2())
							&& ld_result >= dbl(des.getWARNLO2())) {
						ls_warn = 'B';
					} else if (ld_result <= dbl(des.getWARNHI3())
							&& ld_result >= dbl(des.getWARNLO3())) {
						ls_warn = 'C';
					} else {
						ls_warn = 'D';
					}
					flags[1] = ls_warn;
				}
			} catch (Exception e) {
			}
		}

		// 把flags写回resultFlag
		result.setResultFlag(String.valueOf(flags));
	}

	private double dbl(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0;
		}
	}
	
	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Describe getDescribe(String testId) {
		if (desMap != null) {
			if (desMap.containsKey(testId)) {
				return desMap.get(testId);
			} else {
				return null;
			}
		}
		return null;
	}
	
	public static String getJYZ(SyncManager syncManager, String profileName, String deviceId) {
		
		List<String> jyzList = syncManager.getProfileJYZ(profileName, deviceId);
		//System.out.println(jyzList);
		if (jyzList != null && jyzList.size() > 0) {
			if (!StringUtils.isEmpty(jyzList.get(0))) {
				return jyzList.get(0);
			}
		}
		List<String> profileList = syncManager.getProfileJYZ(profileName, null);
		for (String jyz : profileList) {
			if (!StringUtils.isEmpty(jyz))
				return jyz;
		}
		return null;
	}
}

