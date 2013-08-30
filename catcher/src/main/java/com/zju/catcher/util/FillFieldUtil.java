package com.zju.catcher.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zju.catcher.entity.z1.PatientInfo;
import com.zju.catcher.entity.z1.ReferenceValue;
import com.zju.catcher.entity.z1.TestDescribe;
import com.zju.catcher.entity.z1.TestResult;

public class FillFieldUtil {

	private Map<String, TestDescribe> desMap = null;
	private Map<String, List<ReferenceValue>> refMap = null;
	private static FillFieldUtil util = null;

	private FillFieldUtil(List<TestDescribe> desList, List<ReferenceValue> refList) {
		desMap = new HashMap<String, TestDescribe>();
		refMap = new HashMap<String, List<ReferenceValue>>();
		for (TestDescribe des : desList) {
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

	public synchronized static FillFieldUtil getInstance(List<TestDescribe> desList, List<ReferenceValue> refList) {
		if (util == null) {
			util = new FillFieldUtil(desList, refList);
		}
		return util;
	}

	public TestResult fillResult(TestResult result, PatientInfo info) {

		// 完善字段数据
		int li_direct = fillReference(result, info.getAge(), info.getCYCLE(), info.getSEX());
		filleResultFlag(result, li_direct);
		if (result.getEDITMARK() == 0) {
			result.setEDITMARK(Constants.FILL_FLAG);
		}
		return result;
	}

	private int fillReference(TestResult result, int age, int cycle, int sex) {
		int direct = 0;
		String testid = result.getTESTID();
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
					result.setREFLO(reference.getFREFLO0());
					result.setREFHI(reference.getFREFHI0());
					direct = reference.getDIRECT();
					break;
				case 1:
					result.setREFLO(reference.getFREFLO1());
					result.setREFHI(reference.getFREFHI1());
					direct = reference.getDIRECT();
					break;
				case 2:
					result.setREFLO(reference.getFREFLO2());
					result.setREFHI(reference.getFREFHI2());
					direct = reference.getDIRECT();
					break;
				case 3:
					result.setREFLO(reference.getFREFLO3());
					result.setREFHI(reference.getFREFHI3());
					direct = reference.getDIRECT();
					break;
				case 4:
					result.setREFLO(reference.getFREFLO4());
					result.setREFHI(reference.getFREFHI4());
					direct = reference.getDIRECT();
					break;
				case 5:
					result.setREFLO(reference.getFREFLO5());
					result.setREFHI(reference.getFREFHI5());
					direct = reference.getDIRECT();
					break;
				case 6:
					result.setREFLO(reference.getFREFLO6());
					result.setREFHI(reference.getFREFHI6());
					direct = reference.getDIRECT();
					break;
				case 7:
					result.setREFLO(reference.getFREFLO7());
					result.setREFHI(reference.getFREFHI7());
					direct = reference.getDIRECT();
					break;
				case 8:
					result.setREFLO(reference.getFREFLO8());
					result.setREFHI(reference.getFREFHI8());
					direct = reference.getDIRECT();
				}
				if ((result.getREFHI() == null || result.getREFHI().isEmpty())
						&& (result.getREFHI() == null || result.getREFHI().isEmpty())) {
					result.setREFLO(reference.getFREFLO0());
					result.setREFHI(reference.getFREFHI0());
					direct = reference.getDIRECT();
				}
			} else { // 性别：男或未知
				switch (cycle) {
				case 0:
					result.setREFLO(reference.getMREFLO0());
					result.setREFHI(reference.getMREFHI0());
					direct = reference.getDIRECT();
					break;
				case 1:
					result.setREFLO(reference.getMREFLO1());
					result.setREFHI(reference.getMREFHI1());
					direct = reference.getDIRECT();
					break;
				case 2:
					result.setREFLO(reference.getMREFLO2());
					result.setREFHI(reference.getMREFHI2());
					direct = reference.getDIRECT();
					break;
				case 3:
					result.setREFLO(reference.getMREFLO3());
					result.setREFHI(reference.getMREFHI3());
					direct = reference.getDIRECT();
					break;
				case 4:
					result.setREFLO(reference.getMREFLO4());
					result.setREFHI(reference.getMREFHI4());
					direct = reference.getDIRECT();
					break;
				case 5:
					result.setREFLO(reference.getMREFLO5());
					result.setREFHI(reference.getMREFHI5());
					direct = reference.getDIRECT();
					break;
				case 6:
					result.setREFLO(reference.getMREFLO6());
					result.setREFHI(reference.getMREFHI6());
					direct = reference.getDIRECT();
					break;
				case 7:
					result.setREFLO(reference.getMREFLO7());
					result.setREFHI(reference.getMREFHI7());
					direct = reference.getDIRECT();
					break;
				case 8:
					result.setREFLO(reference.getMREFLO8());
					result.setREFHI(reference.getMREFHI8());
					direct = reference.getDIRECT();
				}
				if ((result.getREFHI() == null || result.getREFHI().isEmpty())
						&& (result.getREFHI() == null || result.getREFHI().isEmpty())) {
					result.setREFLO(reference.getMREFLO0());
					result.setREFHI(reference.getMREFHI0());
					direct = reference.getDIRECT();
				}
			}
		}
		return direct;
	}

	private void filleResultFlag(TestResult result, int li_direct) {

		TestDescribe des = desMap.get(result.getTESTID());
		String ls_result = result.getTESTRESULT();
		String ls_reflo = result.getREFLO();
		String ls_refhi = result.getREFHI();
		String resFlag = result.getRESULTFLAG();
		char[] flags;
		
		if (resFlag != null && resFlag.length() == 6) {
			flags = resFlag.toCharArray();
		} else {
			flags = "AAAAAA".toCharArray();
		}
		
		if (des != null) {
			result.setISPRINT(des.getISPRINT());
		} else {
			result.setISPRINT(0);
		}
		
		if (ls_reflo == null || ls_reflo.trim().length() == 0) {
			ls_reflo = "";
			result.setREFLO(ls_reflo);
		}
		if (ls_refhi == null || ls_refhi.trim().length() == 0) {
			ls_refhi = "";
			result.setREFHI(ls_refhi);
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
		result.setRESULTFLAG(String.valueOf(flags));
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
}
