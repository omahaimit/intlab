package com.zju.webapp.controller.explain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Constants;
import com.zju.model.Item;
import com.zju.model.Library;
import com.zju.model.Patient;
import com.zju.model.PatientInfo;
import com.zju.model.ReasoningModify;
import com.zju.model.Result;
import com.zju.model.Rule;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.model.User;
import com.zju.service.IndexManager;
import com.zju.service.ItemManager;
import com.zju.service.PatientInfoManager;
import com.zju.service.ReasoningModifyManager;
import com.zju.service.RuleManager;
import com.zju.service.SyncManager;
import com.zju.service.TestDescribeManager;
import com.zju.service.TestResultManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.DataResponse;
import com.zju.webapp.util.PatientUtil;
import com.zju.webapp.util.SampleUtil;
import com.zju.webapp.util.SectionUtil;

@Controller
@RequestMapping("/explain/patientList*")
public class PatientListController {

	private PatientInfoManager patientInfoManager = null;
	private TestResultManager testResultManager = null;
	private TestDescribeManager testDescribeManager = null;
	private IndexManager indexManager = null;
	private ItemManager itemManager = null;
	private RuleManager ruleManager = null;
	private UserManager userManager = null;
	private SyncManager syncManager = null;
	private ReasoningModifyManager reasoningModifyManager = null;
	private Map<String, TestDescribe> idMap = new HashMap<String, TestDescribe>();
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获取样本中的病人信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/patient*", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPatientInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String id = request.getParameter("id");
		if (id == null) {
			throw new NullPointerException();
		}

		PatientInfo info = patientInfoManager.get(Long.parseLong(id));
		Map<String, Object> map = new HashMap<String, Object>();
		SectionUtil sectionutil = SectionUtil.getInstance(syncManager);
		if (info != null) {
			map.put("id", info.getPatientId());
			map.put("name", info.getPatientName());
			map.put("age", String.valueOf(info.getAge()));
			String ex = info.getExaminaim().trim();
			if (ex.length() > 16) {
				ex = ex.substring(0, 16) + "...";
			}
			map.put("examinaim", ex);
			map.put("diagnostic", info.getDiagnostic());
			map.put("section", sectionutil.getValue(info.getSection()));

			String note = info.getNotes();
			if (!StringUtils.isEmpty(info.getRuleIds())) {
				for (Rule rule : ruleManager.getRuleList(info.getRuleIds())) {
					if (rule.getType() == 3 || rule.getType() == 4) {
						String result = rule.getResultName();
						String itemString = "";
						for (Item i : rule.getItems()) {
							itemString = itemString + i.getIndex().getName() + "、";
						}
						if (note != null && !note.isEmpty()) {
							note = note + "<br>" + itemString.substring(0, itemString.length() - 1) + "异常，" + result;
						} else {
							note = itemString.substring(0, itemString.length() - 1) + "异常，" + result;
						}
					}
				}
			}

			map.put("reason", note);
			map.put("mark", info.getAuditMark());
			map.put("sex", info.getSexValue());
			if (StringUtils.isEmpty(info.getBlh())) {
				List<Patient> list = syncManager.getPatientList("'" + info.getPatientId() + "'");
				if (list != null && list.size() != 0) {
					map.put("blh", list.get(0).getBlh());
				} else {
					map.put("blh", "");
				}
			} else {
				map.put("blh", info.getBlh());
			}
			map.put("type",
					SampleUtil.getInstance().getSampleList(indexManager).get(String.valueOf(info.getSampleType())));
			map.put("dgFlag", info.getCriticalDealFlag());
			map.put("dgInfo", info.getCriticalDeal());
			String dealTimeStr = "";
			if (info.getCriticalDealTime() != null) {
				dealTimeStr = sdf.format(info.getCriticalDealTime());
			}
			map.put("dgTime", dealTimeStr);
		}
		return map;
	}
	
	/**
	 * 获取样本中的智能解释
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/explain*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getIntelExplain(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id))
			throw new NullPointerException();

		PatientInfo info = patientInfoManager.get(Long.parseLong(id));
		User user = userManager.getUserByUsername(request.getRemoteUser());
		String ruleIds = info.getRuleIds();
		String customResult = user.getReasoningResult();

		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(1);
		dataResponse.setTotal(1);                                                                                     

		if (StringUtils.isEmpty(ruleIds)) {
			dataResponse.setRecords(0);
			return dataResponse;
		}

		List<Rule> rules = ruleManager.getRuleList(ruleIds);

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(rules.size());

		for (Rule rule : rules) {
			String reason = getItemString(rule.getRelation());
			for (Result re : rule.getResults()) {
				if(re.getCategory()==null||customResult.contains(re.getCategory())){
					double rank = getRank(rule, re);
					if (rule.getType() == 0) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", rule.getId() + "+" + re.getId());
						map.put("content", reason);
						map.put("rank", rank);
						map.put("oldResult", re.getContent());
						map.put("result", re.getContent());
						dataRows.add(map);
					}
				}
			}
		}
		List<ReasoningModify> modifyList = reasoningModifyManager.getByDocNo(id);
		dataResponse.setRows(modifyData(modifyList, dataRows));
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	private List<Map<String, Object>> modifyData(List<ReasoningModify> modifyList, List<Map<String, Object>> dataRows) {
		Map<String, ReasoningModify> modifyMap = new HashMap<String, ReasoningModify>();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> returnRows = new ArrayList<Map<String, Object>>();
		String dragResult = null;
		for (ReasoningModify r : modifyList) {
			if (r.getType().equals(Constants.ADD)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", r.getModifyId());
				map.put("content", r.getContent());
				map.put("rank", 0);
				map.put("oldResult", r.getOldResult());
				map.put("result", r.getNewResult());
				dataRows.add(map);
			} else if (r.getType().equals(Constants.DRAG)) {
				dragResult = r.getContent();
			} else {
				modifyMap.put(r.getModifyId(), r);
			}
		}

		for (Map<String, Object> m : dataRows) {
			if (modifyMap.containsKey(m.get("id"))) {
				ReasoningModify rm = modifyMap.get(m.get("id"));
				if (rm.getType().equals(Constants.EDIT)) {
					m.put("oldResult", rm.getOldResult());
					m.put("result", rm.getNewResult());
					m.put("content", rm.getContent());
					rows.add(m);
				}
			} else {
				rows.add(m);
			}
		}

		if (dragResult != null) {
			for (String s : dragResult.split(",")) {
				for (Map<String, Object> ma : rows) {
					if (ma.get("id").equals(s)) {
						returnRows.add(ma);
					}
				}
			}
		} else {
			return rows;
		}
		return returnRows;
	}

	private String getItemStr(String id) {
		String result = "";
		Long ID = Long.parseLong(id.substring(1));
		if (id.startsWith("P")) {
			Library lib = PatientUtil.getInstance().getInfo(ID, itemManager);
			result = lib.getValue();
		} else {
			Item item = itemManager.get(ID);
			String testName = item.getIndex().getName();
			String value = item.getValue();
			if (value.contains("||")) {
				return testName + value.replace("||", "或");
			} else if (value.contains("&&")) {
				return testName + value.replace("&&", "且");
			}
			result = testName + value;
		}
		return result;
	}

	private double getRank(Rule rule, Result re) {
		double importance = 0;
		for (Item item : rule.getItems()) {
			String impo = item.getIndex().getImportance();
			if (impo != null && !StringUtils.isEmpty(impo)) {
				importance = Double.parseDouble(impo) + importance;
			}
		}
		double level = 0;
		if (re.getLevel() != null && !StringUtils.isEmpty(re.getLevel())) {
			level = Double.parseDouble(re.getLevel());
		}
		double precent = 0;
		if (re.getPercent() != null && !StringUtils.isEmpty(re.getPercent())) {
			precent = Double.parseDouble(re.getPercent());
		}
		return importance * 0.5 + level * 0.3 + precent * 0.1;
	}

	private String getItemString(String relation) throws Exception {
		StringBuilder builder = new StringBuilder();
		if (!StringUtils.isEmpty(relation)) {
			JSONObject root = new JSONObject(relation);
			if ("and".equals(root.get("id"))) {
				JSONArray levFirst = root.getJSONArray("children");
				for (int i = 0; i < levFirst.length(); i++) {
					JSONObject obj = levFirst.getJSONObject(i);
					if ("or".equals(obj.get("id"))) {
						JSONArray levSecond = obj.getJSONArray("children");
						for (int j = 0; j < levSecond.length(); j++) {
							JSONObject obj2 = levSecond.getJSONObject(j);
							if (j == 0)
								builder.append("(");
							if ("and".equals(obj2.get("id"))) {
								JSONArray levThird = obj2.getJSONArray("children");
								for (int b = 0; b < levThird.length(); b++) {
									JSONObject o = levThird.getJSONObject(b);
									builder.append(getItemStr(o.get("id").toString()));
									if (b + 1 != levThird.length())
										builder.append(" 并且 ");
								}
							} else {
								JSONObject o = levSecond.getJSONObject(j);
								builder.append(getItemStr(o.get("id").toString()));
							}
							if (j + 1 != levSecond.length())
								builder.append(" 或者 ");
						}
						builder.append(")");
					} else {
						builder.append(getItemStr(obj.get("id").toString()));
					}
					if (i + 1 != levFirst.length())
						builder.append(" 并且 ");
				}
			} else if ("or".equals(root.get("id"))) {
				JSONArray levFirst = root.getJSONArray("children");
				for (int i = 0; i < levFirst.length(); i++) {

					JSONObject obj = levFirst.getJSONObject(i);
					if ("and".equals(obj.get("id"))) {
						JSONArray levSecond = root.getJSONArray("children");
						for (int j = 0; j < levSecond.length(); j++) {
							JSONObject o = levFirst.getJSONObject(i);
							builder.append(getItemStr(o.get("id").toString()));
							if (j + 1 != levSecond.length())
								builder.append(" 或者 ");
						}
					} else {
						builder.append(getItemStr(obj.get("id").toString()));
					}
					if (i + 1 != levFirst.length())
						builder.append(" 并且 ");
				}
			} else {
				builder.append(getItemStr(root.get("id").toString()));
			}
		}
		return builder.toString();
	}
	
	@RequestMapping(value = "/chart*", method = RequestMethod.GET)
	@ResponseBody
	public List<Object> getHistoryChart(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id) || !StringUtils.isNumeric(id)) {
			return null;
		}

		PatientInfo info = patientInfoManager.get(Long.parseLong(id));
		List<Object> res = getHistory(info);

		return res;
	}

	private List<Object> getHistory(PatientInfo info) throws Exception {
		class Temp {
			@SuppressWarnings("unused")
			public String name;
			public List<Object> array;
			public List<Object> array1;
			public List<Object> array2;
		}
		List<TestResult> testList = testResultManager.getBySampleNo(info.getSampleNo());
		if (testList != null && testList.size() != 0) {
			String historyIdStr = "";
			for (TestResult test : testList) {
				if (!StringUtils.isEmpty(test.getResultFlag()) && test.getResultFlag().charAt(0) != 'A') {
					if (!historyIdStr.equals(""))
						historyIdStr += ",";
					historyIdStr += "'" + test.getTestId() + "'";
				}
			}
			// System.out.println(historyIdStr);
			if (!historyIdStr.equals("")) {
				if (idMap.size() == 0)
					initMap();
				List<TestResult> result = testResultManager.getHistory(info.getPatientId(), "(" + historyIdStr + ")");
				Map<String, Object> root = new HashMap<String, Object>();
				for (int i = 0; i < result.size(); i++) {
					String testId = result.get(i).getTestId();
					Temp cur = null;
					if (!root.containsKey(testId)) {
						cur = new Temp();
						if (idMap.containsKey(result.get(i).getTestId())) {
							cur.name = idMap.get(result.get(i).getTestId()).getChineseName();
							cur.array = new ArrayList<Object>();
							cur.array1 = new ArrayList<Object>();
							cur.array2 = new ArrayList<Object>();
							root.put(testId, cur);
						}
					} else {
						cur = (Temp) root.get(testId);
					}
					List<Object> view = cur.array;
					List<Object> view1 = cur.array1;
					List<Object> view2 = cur.array2;
					List<Object> element = new ArrayList<Object>();
					List<Object> element1 = new ArrayList<Object>();
					List<Object> element2 = new ArrayList<Object>();
					element.add(result.get(i).getMeasureTime().toString()); // 横坐标的值
					element1.add(result.get(i).getMeasureTime().toString());
					element2.add(result.get(i).getMeasureTime().toString());
					try {
						element.add(Double.parseDouble(result.get(i).getTestResult())); // 纵坐标的值
						element1.add(Double.parseDouble(result.get(i).getRefLo()));
						element2.add(Double.parseDouble(result.get(i).getRefHi()));
					} catch (Exception e) {
						continue;
					}
					view.add(element);
					view1.add(element1);
					view2.add(element2);
				}
				List<Object> lastResult = new ArrayList<Object>();
				for (String key : root.keySet()) {
					List<Object> array = ((Temp) root.get(key)).array;
					if (array.size() > 1) {
						lastResult.add(root.get(key));
					}
				}
				if (lastResult.size() == 0)
					return null;
				else
					return lastResult;
			}
		}
		return null;
	}
	
	/**
	 * 获取某一样本的检验数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSample(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("id");
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(1);
		dataResponse.setTotal(1);

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();

		PatientInfo info = patientInfoManager.getBySampleNo(sampleNo);
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		if (info != null) {
			List<PatientInfo> list = patientInfoManager.getHistorySample(info.getPatientId(), info.getYlxh());
			if (list.size() >= 2) {
				for (TestResult result : list.get(1).getResults()) {
					resultMap1.put(result.getTestId(), result.getTestResult());
				}
			}
			if (list.size() >= 3) {
				for (TestResult result : list.get(2).getResults()) {
					resultMap2.put(result.getTestId(), result.getTestResult());
				}
			}
			if (list.size() >= 4) {
				for (TestResult result : list.get(3).getResults()) {
					resultMap3.put(result.getTestId(), result.getTestResult());
				}
			}
		}
		Set<String> tests = new HashSet<String>();
		String ts = info.getMarkTests();
		if (!StringUtils.isEmpty(ts)) {
			for (String s : ts.split(",")) {
				tests.add(s);
			}
		}

		List<TestResult> list = testResultManager.getBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(list.size());
		for (int i = 0; i < list.size(); i++) {
			String id = list.get(i).getTestId();
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
				String testId = list.get(i).getTestId();
				map.put("id", id);
				map.put("name", idMap.get(list.get(i).getTestId()).getChineseName());
				map.put("result", list.get(i).getTestResult());
				map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(testId) ? resultMap1.get(testId) : "");
				map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(testId) ? resultMap2.get(testId) : "");
				map.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(testId) ? resultMap3.get(testId) : "");
				map.put("scope", list.get(i).getRefLo() + "-" + list.get(i).getRefHi());
				map.put("unit", list.get(i).getUnit());
				map.put("knowledgeName", idMap.get(list.get(i).getTestId()).getKnowledgeName());
				dataRows.add(map);
			}
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/**
	 * 根据条件查询该检验人员的样本
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/data*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getData(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String pages = request.getParameter("page");
		String rows = request.getParameter("rows");
		String patientId = request.getParameter("patientId");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String pName = request.getParameter("pName");
		String doct = request.getParameter("doct");
		DataResponse dataResponse = new DataResponse();
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);

		if (idMap.size() == 0) initMap();
		List<PatientInfo> list = new ArrayList<PatientInfo>();
		if (patientId != null) {
			list = patientInfoManager.getSampleById(patientId);
		} else if (pName != null && from != null && to != null) {
			list = patientInfoManager.getSampleByPatientName(from, to, pName);
		} else if (doct != null) {
			PatientInfo p = patientInfoManager.get(Long.parseLong(doct));
			if (p != null) {
				list.add(p);
			}
		}
		
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		dataResponse.setRecords(listSize);
		int x = listSize % (row == 0 ? listSize : row);
		if (x != 0) {
			x = row - x;
		}
		int totalPage = (listSize + x) / (row == 0 ? listSize : row);
		dataResponse.setPage(page);
		dataResponse.setTotal(totalPage);
		int start = row * (page - 1);
		int index = 0;
		while (index < row && (start + index) < listSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			PatientInfo info = list.get(start + index);
			map.put("id",info.getId());
			map.put("sample",info.getSampleNo());
			//map.put("receivetime", info.getExecutetime() == null ? "" : sdf.format(info.getExecutetime()));
			if (info.getAuditStatus() == -1) {
				map.put("type", "<font color='red'>无结果</font>");
			} else {
				map.put("type", "有结果");
			}
			if (info.getResultStatus()>=6) {
				map.put("type", "已打印");
			}
			map.put("examinaim", info.getExaminaim());
			dataRows.add(map);
			index++;
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(idMap.size()==0){
			initMap();
		}
		ModelAndView view=new ModelAndView();
		String patientId = request.getParameter("patientId");
		if(patientId == null){
			return view;
		}
		return view.addObject("patientId",patientId);
	}

	synchronized private void initMap() {
		List<TestDescribe> list = testDescribeManager.getAll();
		for (TestDescribe t : list) {
			idMap.put(t.getTestId(), t);
		}
	}

	@Autowired
	public void setPatientInfoManager(PatientInfoManager patientInfoManager) {
		this.patientInfoManager = patientInfoManager;
	}
	
	@Autowired
	public void setTestResultManager(TestResultManager testResultManager) {
		this.testResultManager = testResultManager;
	}
	
	@Autowired
	public void setTestDescribeManager(TestDescribeManager testDescribeManager) {
		this.testDescribeManager = testDescribeManager;
	}
	
	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
	}
	
	@Autowired
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	@Autowired
	public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}
	
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setSyncManager(SyncManager syncManager) {
		this.syncManager = syncManager;
	}
	
	@Autowired
	public void setReasoningModifyManager(
			ReasoningModifyManager reasoningModifyManager) {
		this.reasoningModifyManager = reasoningModifyManager;
	}
}
