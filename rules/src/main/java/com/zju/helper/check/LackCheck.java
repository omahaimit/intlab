package com.zju.helper.check;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;

public class LackCheck implements Check {

	private Map<Long, List<String>> ylxhMap = null;
	private Map<String, TestDescribe> idMap = null;
	
	public LackCheck(Map<Long, List<String>> ylxhMap, Map<String, TestDescribe> idMap) {
		this.ylxhMap = ylxhMap;
		this.idMap = idMap;
	}
	
	@Override
	public boolean doCheck(PatientInfo info) {

		boolean result = true;
		Set<String> lackSet = new HashSet<String>();
		String ylxh = info.getYlxh();	// 医疗序号
		
		if (!StringUtils.isEmpty(ylxh)) {
			List<String> xhList = new ArrayList<String>();
			String[] xhs = ylxh.split("[+]");
			for (String xh : xhs) {
				if(xh.contains("[")){
					String[] linshi_xh = xh.split("\\[");
					xh = linshi_xh[0];
				}
				List<String> list = ylxhMap.get(Long.parseLong(xh));
				xhList.addAll(list);
			}

			// 提取testId的set集
			Set<TestResult> set = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			if (set != null) {
				for (TestResult r : set)
					testIdSet.add(r.getTestId());
			} else {
				result = false;
				lackSet.addAll(xhList);
			}
			for (String xh : xhList) {
				if(xh.equals("3007")){
					if(testIdSet.contains(xh)||testIdSet.contains("4710")||testIdSet.contains("0649")){
						continue;
					}
				} else if (xh.equals("4710")){
					if(testIdSet.contains(xh)||testIdSet.contains("3007")||testIdSet.contains("0649")){
						continue;
					}
				} else if (xh.equals("3076")){
					if(testIdSet.contains(xh)||testIdSet.contains("0560")){
						continue;
					}
				} else if (xh.equals("3017")){
					if(testIdSet.contains(xh)||testIdSet.contains("4679")){
						continue;
					}
				} else if (xh.equals("3040")){
					if(testIdSet.contains(xh)||testIdSet.contains("3179")){
						continue;
					}
				}
				if (!testIdSet.contains(xh) && idMap.containsKey(xh)) {
					result = false;
					TestDescribe des = idMap.get(xh);
					if (des != null)
						lackSet.add(des.getChineseName());
					else
						lackSet.add(xh);
				}
			}
		}
		
		if (!result) {
			String lack = "少做:" + CheckUtil.toString(lackSet);
			info.setAuditMark(LACK_MARK);
			info.setAuditStatus(UNPASS);
			info.setNotes(lack);
		}
		
		return result;
	}

}
