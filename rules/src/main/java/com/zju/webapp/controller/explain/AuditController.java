package com.zju.webapp.controller.explain;

import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.drools.DroolsRunner;
import com.zju.drools.R;
import com.zju.helper.check.Alarm2Check;
import com.zju.helper.check.Alarm3Check;
import com.zju.helper.check.BayesCheck;
import com.zju.helper.check.Check;
import com.zju.helper.check.DangerCheck;
import com.zju.helper.check.DiffCheck;
import com.zju.helper.check.DroolsCheck;
import com.zju.helper.check.ExtremeCheck;
import com.zju.helper.check.JyzCheck;
import com.zju.helper.check.LackCheck;
import com.zju.helper.check.RatioCheck;
import com.zju.helper.check.RetestCheck;
import com.zju.model.AuditTrace;
import com.zju.model.Code;
import com.zju.model.Constants;
import com.zju.model.Describe;
import com.zju.model.Diagnostic;
import com.zju.model.Item;
import com.zju.model.Library;
import com.zju.model.Patient;
import com.zju.model.PatientInfo;
import com.zju.model.Profile;
import com.zju.model.ReasoningModify;
import com.zju.model.ReferenceValue;
import com.zju.model.Result;
import com.zju.model.Rule;
import com.zju.model.Statistic;
import com.zju.model.SyncLabGroupInfo;
import com.zju.model.SyncResult;
import com.zju.model.Task;
import com.zju.model.TestDescribe;
import com.zju.model.TestModify;
import com.zju.model.TestResult;
import com.zju.model.TestResultPK;
import com.zju.model.User;
import com.zju.model.Ylxh;
import com.zju.service.AuditTraceManager;
import com.zju.service.BayesService;
import com.zju.service.IndexManager;
import com.zju.service.ItemManager;
import com.zju.service.PatientInfoManager;
import com.zju.service.ReasoningModifyManager;
import com.zju.service.ResultManager;
import com.zju.service.RuleManager;
import com.zju.service.SyncManager;
import com.zju.service.TaskManager;
import com.zju.service.TestDescribeManager;
import com.zju.service.TestModifyManager;
import com.zju.service.TestResultManager;
import com.zju.service.UserManager;
import com.zju.service.YlxhManager;
import com.zju.util.Config;
import com.zju.webapp.util.AnalyticUtil;
import com.zju.webapp.util.DataResponse;
import com.zju.webapp.util.FillFieldUtil;
import com.zju.webapp.util.FormulaUtil;
import com.zju.webapp.util.PatientUtil;
import com.zju.webapp.util.SampleUtil;
import com.zju.webapp.util.SectionUtil;
import com.zju.webapp.util.UserUtil;
import com.zju.webapp.util.TaskManagerUtil;

@Controller
@RequestMapping("/explain/audit*")
public class AuditController {

	private static Log log = LogFactory.getLog(AuditController.class);
	private PatientInfoManager patientInfoManager = null;
	private TestResultManager testResultManager = null;
	private TestDescribeManager testDescribeManager = null;
	private IndexManager indexManager = null;
	private ItemManager itemManager = null;
	private ResultManager resultManager = null;
	private RuleManager ruleManager = null;
	private TaskManager taskManager = null;
	private UserManager userManager = null;
	private SyncManager syncManager = null;
	private YlxhManager ylxhManager = null;
	private BayesService bayesService = null;
	private TestModifyManager testModifyManager = null;
	private AuditTraceManager auditTraceManager = null;
	private ReasoningModifyManager reasoningModifyManager = null;
	private Map<String, TestDescribe> idMap = new HashMap<String, TestDescribe>();
	private Map<Long, List<String>> ylxhMap = new HashMap<Long, List<String>>();
	private Map<String, Integer> slgiMap = new HashMap<String, Integer>();
	private Map<String, String> diagMap = new HashMap<String, String>();
	public static boolean startBackAudit = false;
	public static int backAuditInterval = 5;// minutes
	private static FillFieldUtil fillUtil = null;
	private static FormulaUtil formulaUtil = null;

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat mdf = new SimpleDateFormat("MM/dd");
	private final static SimpleDateFormat hmf = new SimpleDateFormat("HH:mm");
	// private final static int MAX_UNAUDIT_COUNT = Config.getAuditMaxCount(); // 累计到10个样本时自动审核
	private final static long AUDIT_INTERVAL_TIME = Config.getAuditInterval(); // 间隔*毫秒执行自动审核
	//private final static int ONCE_MAX_AUDIT = Config.getOnceAuditMaxCount(); // 一次自动审核最大样本数
	private final static int THREAD_RUNNING = 1;
	private final static int THREAD_FINISHED = 2;
	private final static int THREAD_STOPPED = 3;

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
		if (idMap.size() == 0)
			initMap();
		
		if (slgiMap.size() == 0)
			initSLGIMap();
		
		if (diagMap.size() == 0)
			initDiagMap();

		if (fillUtil == null) {
			List<Describe> desList = syncManager.getAllDescribe();
			List<ReferenceValue> refList = syncManager.getAllReferenceValue();
			fillUtil = FillFieldUtil.getInstance(desList, refList);
		}
		
		if (formulaUtil == null) {
			formulaUtil = FormulaUtil.getInstance(syncManager, testResultManager, patientInfoManager, idMap, fillUtil);
		}

		PatientInfo info = patientInfoManager.get(Long.parseLong(id));
		if (info.getAuditMark() != Constants.STATUS_PASSED) {
			try {
				formulaUtil.formula(info, request.getRemoteUser());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		SectionUtil sectionutil = SectionUtil.getInstance(syncManager);
		if (info != null) {
			String code = info.getSampleNo().substring(8, 11);
			map.put("isOverTime", false);
			if(slgiMap.containsKey(code) && info.getReceivetime()!=null) {
				long exceptTime = slgiMap.get(code) * 60 * 1000;
				long df = new Date().getTime() - info.getReceivetime().getTime();
				if (info.getResultStatus()<5 && df>exceptTime) {
					map.put("isOverTime", true);
				}
			}
			map.put("id", info.getId());
			map.put("name", info.getPatientName());
			map.put("age", String.valueOf(info.getAge()));
			if(info.getExaminaim() != null) {
				String ex = info.getExaminaim().trim();
				/*if (ex.length() > 16) {
					ex = ex.substring(0, 16) + "...";
				}*/
				map.put("examinaim", ex);
			} else {
				map.put("examinaim", "");
			}
			map.put("mode", info.getRequestMode());
			map.put("diagnostic", info.getDiagnostic());
			if(diagMap.containsKey(info.getDiagnostic())) {
				map.put("diagnosticKnow", diagMap.get(info.getDiagnostic()));
			} else {
				map.put("diagnosticKnow", "");
			}
			map.put("section", sectionutil.getValue(info.getSection()));

			String note = info.getNotes();
			List<TestResult> testList = syncManager.getTestBySampleNo(info.getSampleNo());
			Set<String> testIds = new HashSet<String>();
			int size = testList.size();
			for (TestResult t : testList) {
				testIds.add(t.getTestId());
				if (t.getEditMark() == 7) {
					size--;
				}
			}
			if (info.getAuditStatus() == Constants.STATUS_UNPASS
					&& !StringUtils.isEmpty(info.getRuleIds())) {
				for (Rule rule : ruleManager.getRuleList(info.getRuleIds())) {
					//Set<String> usedTestIds = new HashSet<String>();
					if (rule.getType() == 3 || rule.getType() == 4
							|| rule.getType() == 5 || rule.getType() == 6
							|| rule.getType() == 7) {
	 					String reason = getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
						String result = rule.getResultName();
						/*String itemString = "";
						for (Item i : rule.getItems()) {
							String testid = i.getIndex().getIndexId();
							if (testIds.contains(testid) && !usedTestIds.contains(testid)) {
								itemString = itemString + i.getIndex().getName() + "、";
								usedTestIds.add(testid);
							}
						}
						if (!itemString.isEmpty()) {
							if (note != null && !note.isEmpty()) {
								note = note + "<br>" + itemString.substring(0, itemString.length() - 1) + "异常，" + result;
							} else {
								note = itemString.substring(0, itemString.length() - 1) + "异常，" + result;
							}
						}*/
						if (note != null && !note.isEmpty()) {
							note = note + "<br>" + reason + ", <font color='red'>" + result + "</font>";
						} else {
							note = reason + ", <font color='red'>" + result + "</font>";
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
			map.put("bed", info.getDepartBed());
			map.put("size", size);
			map.put("passReason", info.getPassReason());
			map.put("patientId", info.getPatientId());
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
			String reason = getItem(new JSONObject(rule.getRelation()), new StringBuilder()).toString();
			for (Result re : rule.getResults()) {
				if (re.getCategory() == null || customResult == null || customResult.contains(re.getCategory())) {
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
	
	private StringBuilder getItem(JSONObject root, StringBuilder sb) {
		try {
			if ("and".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
					if (i != array.length() - 1) {
						sb.append(" 并 ");
					}
				}
			} else if ("or".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				sb.append("(");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
					if (i != array.length() - 1) {
						sb.append(" 或 ");
					}
				}
				sb.append(")");
			} else if ("not".equals(root.get("id"))) {
				JSONArray array = root.getJSONArray("children");
				sb.append("非(");
				for (int i = 0; i < array.length(); i++) {
					getItem(array.getJSONObject(i), sb);
				}
				sb.append(")");
			} else {
				sb.append(getItemStr(root.get("id").toString()));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return sb;

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
	
	@RequestMapping(value = "/singleChart*", method = RequestMethod.GET)
	@ResponseBody
	public Object getSingleChart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		class Temp {
			@SuppressWarnings("unused")
			public String name;
			public List<Object> array;
			public List<Object> array1;
			public List<Object> array2;
		}
		String testid = request.getParameter("id");
		String sample = request.getParameter("sample");

		PatientInfo info = patientInfoManager.getBySampleNo(sample);
		String birthday = sdf.format(info.getBirthday());
		List<TestResult> list = testResultManager.getSingleHistory(testid, info.getPatientName(), birthday);
		if(list.size()>1) {
			Temp cur = new Temp();
			cur.name = idMap.get(list.get(0).getTestId()).getChineseName();
			cur.array = new ArrayList<Object>();
			cur.array1 = new ArrayList<Object>();
			cur.array2 = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				List<Object> element = new ArrayList<Object>();
				List<Object> element1 = new ArrayList<Object>();
				List<Object> element2 = new ArrayList<Object>();
				element.add(list.get(i).getMeasureTime().toString()); // 横坐标的值
				element1.add(list.get(i).getMeasureTime().toString());
				element2.add(list.get(i).getMeasureTime().toString());
				try {
					element.add(Double.parseDouble(list.get(i).getTestResult())); // 纵坐标的值
					element1.add(Double.parseDouble(list.get(i).getRefLo()));
					element2.add(Double.parseDouble(list.get(i).getRefHi()));
				} catch (Exception e) {
					continue;
				}
				cur.array.add(element);
				cur.array1.add(element1);
				cur.array2.add(element2);
			}
			return cur;
		}
		return null;
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
			if (!historyIdStr.equals("")) {
				if (idMap.size() == 0)
					initMap();
				List<TestResult> result = testResultManager.getHistory(info.getPatientId(), "(" + historyIdStr + ")");
				Map<String, Object> root = new HashMap<String, Object>();
				long linshi_time = result.get(0).getMeasureTime().getTime();
				boolean flag = true;
				for (int i = 0; i < result.size(); i++) {
					String testId = result.get(i).getTestId();

					long time = result.get(i).getMeasureTime().getTime();

					if (time != linshi_time) {
						if (linshi_time - time < 3600000) {
							flag = false;
							linshi_time = time;
						} else {
							flag = true;
							linshi_time = time;
						}
					}

					if (flag) {
						Temp cur = null;
						if (!root.containsKey(testId)) {
							cur = new Temp();
							if (idMap.containsKey(testId)) {
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
	 * 编辑项目中某一项的结果值
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean editSample(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String result = request.getParameter("result");
		String testId = request.getParameter("id");
		String sampleNo = request.getParameter("sampleNo");
		
		if (fillUtil == null) {
			List<Describe> desList = syncManager.getAllDescribe();
			List<ReferenceValue> refList = syncManager.getAllReferenceValue();
			fillUtil = FillFieldUtil.getInstance(desList, refList);
		}
		
		if (formulaUtil == null) {
			formulaUtil = FormulaUtil.getInstance(syncManager, testResultManager, patientInfoManager, idMap, fillUtil);
		}

		if (!StringUtils.isEmpty(testId) && !StringUtils.isEmpty(sampleNo)) {
			TestResult testResult = testResultManager.get(new TestResultPK(sampleNo, testId));
			if (testResult == null)
				return false;
			PatientInfo info = patientInfoManager.getBySampleNo(sampleNo);
			String oldResult = testResult.getTestResult();
			testResult.setTestResult(result);
			testResult.setOperator(request.getRemoteUser());
			testResult.setMeasureTime(new Date());
			testResult.setResultFlag("AAAAAA");
			fillUtil.fillResult(testResult, info);
			if (testResult.getEditMark() == 0) {
				testResult.setEditMark(Constants.MANUAL_EDIT_FLAG);
			} else if (testResult.getEditMark() % Constants.MANUAL_EDIT_FLAG != 0) {
				testResult.setEditMark(Constants.MANUAL_EDIT_FLAG * testResult.getEditMark());
			}
			testResultManager.save(testResult);
			formulaUtil.formula(info, request.getRemoteUser());
			
			info.setModifyFlag(1);
			info.setAuditStatus(0);
			patientInfoManager.save(info);
			
			TestModify testModify = new TestModify();
			testModify.setModifyTime(new Date());
			testModify.setModifyUser(request.getRemoteUser());
			testModify.setSampleNo(sampleNo);
			testModify.setTestId(testId);
			testModify.setNewValue(result);
			testModify.setOldValue(oldResult);
			testModify.setType(Constants.EDIT);
			testModifyManager.save(testModify);
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 编辑智能解释的某结果值
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/explain/edit*", method = RequestMethod.POST)
	@ResponseBody
	public boolean editExplain(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String modifyId = request.getParameter("id");
		String result = request.getParameter("result");
		String oldResult = request.getParameter("oldResult");
		String docNo = request.getParameter("docNo");
		String content = request.getParameter("content");

		if (!StringUtils.isEmpty(modifyId) && !StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setNewResult(result);
			reasoningModify.setOldResult(oldResult);
			reasoningModify.setContent(content);
			reasoningModify.setModifyId(modifyId);
			reasoningModify.setDocNo(docNo);
			reasoningModify.setType(Constants.EDIT);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/ajax/profileList*", method = RequestMethod.GET)
	@ResponseBody
	public void getProfileList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String lab = request.getParameter("lab");
		List<Profile> profileList = syncManager.getProfiles(lab);

		JSONArray array = new JSONArray();
		for (Profile profile : profileList) {
			JSONObject obj = new JSONObject();
			obj.put("name", profile.getName());
			obj.put("describe", profile.getDescribe());
			obj.put("device", profile.getDeviceId());
			// System.out.println(profile.getDeviceId() + ":" + profile.getJyz());
			obj.put("test", profile.getTest());
			array.put(obj);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}

	@RequestMapping(value = "/ajax/profileTest*", method = RequestMethod.POST)
	@ResponseBody
	public void getProfileTest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sample = request.getParameter("sample");
		String test = request.getParameter("test");
		User operator = UserUtil.getCurrentUser(request, userManager);
		operator.setLastProfile(test);
		userManager.saveUser(operator);
		if (test.endsWith(","))
			test = test.substring(0, test.length() - 1);
		if (idMap.size() == 0)
			initMap();
		String[] testId = test.split(",");
		Set<String> testSet = new HashSet<String>();
		List<TestResult> testResult = testResultManager.getBySampleNo(sample);
		for (TestResult tr : testResult)
			testSet.add(tr.getTestId());
		JSONArray array = new JSONArray();
		for (String t : testId) {
			if (testSet.contains(t))
				continue;
			String name = idMap.get(t).getChineseName();
			JSONObject obj = new JSONObject();
			obj.put("test", t);
			obj.put("name", name);
			array.put(obj);
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}

	@RequestMapping(value = "/ajax/profileJYZ*", method = RequestMethod.POST)
	@ResponseBody
	public String checkProfileJYZ(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User operator = UserUtil.getCurrentUser(request, userManager);
		String codes = operator.getLabCode();
		// System.out.println(codes);
		Set<String> returnCode = new HashSet<String>();
		if (!StringUtils.isEmpty(codes)) {
			String[] code = codes.split(",");
			for (String cd : code) {
				List<String> jyzList = syncManager.getProfileJYZ(cd, null);
				// System.out.println(jyzList);
				boolean flag = false;
				for (String jyz : jyzList) {
					if (!StringUtils.isEmpty(jyz)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					returnCode.add(cd);
				}
			}
		}
		return setToString(returnCode);
	}

	@RequestMapping(value = "/autoAudit*", method = RequestMethod.GET)
	@ResponseBody
	public boolean autoAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String status = request.getParameter("status");
		String scope = request.getParameter("scope");
		HttpSession session = request.getSession();
		try {
			// User operator = UserUtil.getCurrentUser(request, userManager);
			if ("1".equals(status)) {
				// operator.setActiveAuto(true);
				session.setAttribute("isAuto", true);
				session.setAttribute("scope", scope);
			} else {
				// operator.setActiveAuto(false);
				session.setAttribute("isAuto", false);
				session.removeAttribute("scope");
			}
			// userManager.save(operator);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@RequestMapping(value = "/add*", method = RequestMethod.POST)
	@ResponseBody
	public boolean addProject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String testResults = request.getParameter("test");
			String sample = request.getParameter("sample");
			String[] testResult = testResults.split(";");

			if (fillUtil == null) {
				List<Describe> desList = syncManager.getAllDescribe();
				List<ReferenceValue> refList = syncManager.getAllReferenceValue();
				fillUtil = FillFieldUtil.getInstance(desList, refList);
			}

			PatientInfo info = patientInfoManager.getBySampleNo(sample);

			for (String test : testResult) {
				String[] idValue = test.split(":");
				if (idValue.length == 2) {
					TestResult nt = new TestResult();
					/*
					 * TestResult tr = testResultManager.get(new TestResultPK(sample, idValue[0])); if (tr != null) { nt
					 * = tr; }
					 */
					nt.setTestId(idValue[0]);
					nt.setSampleNo(sample);
					nt.setTestResult(idValue[1]);
					nt.setOperator(request.getRemoteUser());
					nt.setCorrectFlag("1");
					nt.setMeasureTime(new Date());
					nt.setResultFlag("AAAAAA");
					nt.setEditMark(Constants.ADD_FLAG);
					Describe des = fillUtil.getDescribe(idValue[0]);
					if (des != null) {
						nt.setSampleType(des.getSAMPLETYPE());
						nt.setUnit(des.getUNIT());
					}
					fillUtil.fillResult(nt, info);
					testResultManager.save(nt);
					TestModify testModify = new TestModify();
					testModify.setModifyTime(new Date());
					testModify.setModifyUser(request.getRemoteUser());
					testModify.setSampleNo(sample);
					testModify.setTestId(idValue[0]);
					testModify.setNewValue(idValue[1]);
					testModify.setType(Constants.ADD);
					testModifyManager.save(testModify);
					info.setModifyFlag(1);
					// info.setWriteBack(1);
					patientInfoManager.save(info);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}

		// System.out.println(testResult);
		return true;
	}

	/**
	 * 添加一条智能解释
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addResult*", method = RequestMethod.POST)
	@ResponseBody
	public boolean addResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String content = request.getParameter("content");
		String result = request.getParameter("result");
		String docNo = request.getParameter("docNo");
		int addCount = reasoningModifyManager.getAddNumber();

		if (!StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setOldResult(result);
			reasoningModify.setNewResult(result);
			reasoningModify.setContent(content);
			reasoningModify.setDocNo(docNo);
			reasoningModify.setModifyId("add" + addCount);
			reasoningModify.setType(Constants.ADD);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 删除一条检验项目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete*", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteProject(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String testId = request.getParameter("id");
		String sampleNo = request.getParameter("sampleNo");

		if (!StringUtils.isEmpty(testId) && !StringUtils.isEmpty(sampleNo)) {
			TestResult testResult = testResultManager.get(new TestResultPK(sampleNo, testId));
			testResult.setEditMark(Constants.DELETE_FLAG);
			testResultManager.save(testResult);
			// testResultManager.remove(new TestResultPK(sampleNo, testId));
			TestModify testModify = new TestModify();
			testModify.setModifyTime(new Date());
			testModify.setModifyUser(request.getRemoteUser());
			testModify.setSampleNo(sampleNo);
			testModify.setTestId(testId);
			testModify.setNewValue(testResult.getTestResult());
			testModify.setType(Constants.DELETE);
			testModifyManager.save(testModify);
			PatientInfo info = patientInfoManager.getBySampleNo(sampleNo);
			info.setModifyFlag(1);
			// info.setWriteBack(1);
			patientInfoManager.save(info);
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 删除一条智能解释
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteResult*", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String modifyId = request.getParameter("id");
		String docNo = request.getParameter("docNo");

		if (!StringUtils.isEmpty(modifyId) && !StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setModifyId(modifyId);
			reasoningModify.setDocNo(docNo);
			reasoningModify.setType(Constants.DELETE);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}

	/**
	 * 拖拽智能解释
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/drag*", method = RequestMethod.POST)
	@ResponseBody
	public boolean dragResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String content = request.getParameter("content");
		String docNo = request.getParameter("docNo");
		int dragCount = reasoningModifyManager.getDragNumber();

		if (!StringUtils.isEmpty(content) && !StringUtils.isEmpty(docNo)) {
			ReasoningModify reasoningModify = new ReasoningModify();
			reasoningModify.setModifyTime(new Date());
			reasoningModify.setModifyUser(request.getRemoteUser());
			reasoningModify.setModifyId("drag" + dragCount);
			reasoningModify.setContent(content);
			reasoningModify.setDocNo(docNo);
			reasoningModify.setType(Constants.DRAG);
			reasoningModifyManager.save(reasoningModify);
		} else {
			return false;
		}

		return true;
	}

	@RequestMapping(value = "/labChange*", method = RequestMethod.POST)
	@ResponseBody
	public boolean labChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String lab = request.getParameter("lab");
		User operator = UserUtil.getCurrentUser(request, userManager);
		operator.setLastLibrary(lab);
		userManager.saveUser(operator);
		return true;
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
		Map<String, Object> userdata = new HashMap<String, Object>();
		String hisDate = "";
		String sameSample = "";

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();
		
		PatientInfo info = patientInfoManager.getBySampleNo(sampleNo);

		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		Map<String, String> resultMap4 = new HashMap<String, String>();
		Map<String, String> resultMap5 = new HashMap<String, String>();
		if (info != null) {
			List<PatientInfo> list = patientInfoManager.getHistorySample(info.getPatientId(), info.getLabdepartMent());
			long curInfoReceiveTime = info.getReceivetime().getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<TestResult> now = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : now) {
				testIdSet.add(t.getTestId());
			}
			String day = mdf.format(info.getReceivetime());
			
			for (PatientInfo pinfo : list) {
				boolean isHis = false;
				Set<TestResult> his = pinfo.getResults();
				for (TestResult test: his) {
					if (testIdSet.contains(test.getTestId())) {
						isHis = true;
						break;
					}
				}
				if (pinfo.getReceivetime() == null) {
					continue;
				}
				if (pinfo.getReceivetime().getTime() < curInfoReceiveTime && isHis) {
					if (index > 4)
						break;
					switch (index) {
					case 0:
						rmap = resultMap1;
						break;
					case 1:
						rmap = resultMap2;
						break;
					case 2:
						rmap = resultMap3;
						break;
					case 3:
						rmap = resultMap4;
						break;
					case 4:
						rmap = resultMap5;
						break;
					}
					for (TestResult result : pinfo.getResults()) {
						rmap.put(result.getTestId(), result.getTestResult());
					}
					if (!"".equals(hisDate)) {
						hisDate += ",";
					}
					String pDay = mdf.format(pinfo.getReceivetime());
					hisDate += pDay;
					index++;
					
					if (day.equals(pDay)) {
						if (!"".equals(sameSample)) {
							sameSample += ",";
						}
						sameSample += pinfo.getSampleNo();
					}
				}
			}
		}
		int color = 0;
		Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
		List<TestResult> list = syncManager.getTestBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEditMark() == Constants.DELETE_FLAG)
				continue;

			color = 0;
			String id = list.get(i).getTestId();
			if (colorMap.containsKey(id)) {
				color = colorMap.get(id);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
				String testId = list.get(i).getTestId();
				map.put("id", id);
				map.put("color", color);
				map.put("name", idMap.get(list.get(i).getTestId()).getChineseName());
				map.put("result", list.get(i).getTestResult());
				map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(testId) ? resultMap1.get(testId) : "");
				map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(testId) ? resultMap2.get(testId) : "");
				map.put("last2", resultMap3.size() != 0 && resultMap3.containsKey(testId) ? resultMap3.get(testId) : "");
				map.put("last3", resultMap4.size() != 0 && resultMap4.containsKey(testId) ? resultMap4.get(testId) : "");
				map.put("last4", resultMap5.size() != 0 && resultMap5.containsKey(testId) ? resultMap5.get(testId) : "");
				map.put("checktime", hmf.format(list.get(i).getMeasureTime()));
				map.put("device", list.get(i).getOperator());
				String lo = list.get(i).getRefLo();
				String hi = list.get(i).getRefHi();
				if (lo != null && hi != null) {
					map.put("scope", lo + "-" + hi);
				} else {
					map.put("scope", "");
				}
				map.put("unit", list.get(i).getUnit());
				map.put("knowledgeName", idMap.get(list.get(i).getTestId()).getKnowledgeName());
				map.put("editMark", list.get(i).getEditMark());
				dataRows.add(map);
			}

		}
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		userdata.put("hisDate", hisDate);
		userdata.put("sameSample", sameSample);
		dataResponse.setUserdata(userdata);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/**
	 * 获取某一样本的检验数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample0*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSample0(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("id");
		DataResponse dataResponse = new DataResponse();
		Map<String, Object> userdata = new HashMap<String, Object>();
		String hisDate = "";
		String sameSample = "";

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();

		PatientInfo info = patientInfoManager.getBySampleNo(sampleNo);
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		if (info != null) {
			List<PatientInfo> list = patientInfoManager.getHistorySample(info.getPatientId(), info.getLabdepartMent());
			long curInfoReceiveTime = info.getReceivetime().getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<TestResult> now = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : now) {
				testIdSet.add(t.getTestId());
			}
			String day = mdf.format(info.getReceivetime());
			for (PatientInfo pinfo : list) {
				boolean isHis = false;
				Set<TestResult> his = pinfo.getResults();
				for (TestResult test: his) {
					if (testIdSet.contains(test.getTestId())) {
						isHis = true;
						break;
					}
				}
				if (pinfo.getReceivetime().getTime() < curInfoReceiveTime && isHis) {
					if (index > 2)
						break;
					switch (index) {
					case 0:
						rmap = resultMap1;
						break;
					case 1:
						rmap = resultMap2;
						break;
					}
					for (TestResult result : pinfo.getResults()) {
						rmap.put(result.getTestId(), result.getTestResult());
					}
					if (!"".equals(hisDate)) {
						hisDate += ",";
					}
					String pDay = mdf.format(pinfo.getReceivetime());
					hisDate += pDay;
					index++;
					
					if (day.equals(pDay)) {
						if (!"".equals(sameSample)) {
							sameSample += ",";
						}
						sameSample += pinfo.getSampleNo();
					}
				}
			}
		}
		int color = 0;
		Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
		List<TestResult> list = testResultManager.getBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		int count = list.size();
		count = (count % 2 == 0) ? count / 2 : (count + 1) / 2;
		
		for (int i = 0; i < count; i++) {
			if (list.get(i).getEditMark() == Constants.DELETE_FLAG)
				continue;

			color = 0;
			String id = list.get(i).getTestId();
			if (colorMap.containsKey(id)) {
				color = colorMap.get(id);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
				String testId = list.get(i).getTestId();
				map.put("id", id);
				map.put("color", color);
				map.put("name", idMap.get(list.get(i).getTestId()).getChineseName());
				map.put("result", list.get(i).getTestResult());
				map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(testId) ? resultMap1.get(testId) : "");
				map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(testId) ? resultMap2.get(testId) : "");
				map.put("device", list.get(i).getOperator());
				String lo = list.get(i).getRefLo();
				String hi = list.get(i).getRefHi();
				if (lo != null && hi != null) {
					map.put("scope", lo + "-" + hi);
				} else {
					map.put("scope", "");
				}
				map.put("unit", list.get(i).getUnit());
				map.put("knowledgeName", idMap.get(list.get(i).getTestId()).getKnowledgeName());
				map.put("editMark", list.get(i).getEditMark());
				dataRows.add(map);
			}

		}
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		userdata.put("hisDate", hisDate);
		userdata.put("sameSample", sameSample);
		dataResponse.setUserdata(userdata);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/**
	 * 获取某一样本的检验数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sample1*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSample1(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("id");
		DataResponse dataResponse = new DataResponse();
		Map<String, Object> userdata = new HashMap<String, Object>();
		String hisDate = "";

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();

		PatientInfo info = patientInfoManager.getBySampleNo(sampleNo);
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		if (info != null) {
			List<PatientInfo> list = patientInfoManager.getHistorySample(info.getPatientId(), info.getLabdepartMent());
			long curInfoReceiveTime = info.getReceivetime().getTime();
			int index = 0;
			Map<String, String> rmap = null;
			Set<TestResult> now = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : now) {
				testIdSet.add(t.getTestId());
			}
			for (PatientInfo pinfo : list) {
				boolean isHis = false;
				Set<TestResult> his = pinfo.getResults();
				for (TestResult test: his) {
					if (testIdSet.contains(test.getTestId())) {
						isHis = true;
						break;
					}
				}
				if (pinfo.getReceivetime().getTime() < curInfoReceiveTime && isHis) {
					if (index > 2)
						break;
					switch (index) {
					case 0:
						rmap = resultMap1;
						break;
					case 1:
						rmap = resultMap2;
						break;
					}
					for (TestResult result : pinfo.getResults()) {
						rmap.put(result.getTestId(), result.getTestResult());
					}
					if (!"".equals(hisDate)) {
						hisDate += ",";
					}
					hisDate += mdf.format(pinfo.getReceivetime());
					index++;
				}
			}
		}
		int color = 0;
		Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
		List<TestResult> list = testResultManager.getBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		int count = list.size();
		count = (count % 2 == 0) ? count / 2 : (count + 1) / 2;
		
		for (int i = count; i < list.size(); i++) {
			if (list.get(i).getEditMark() == Constants.DELETE_FLAG)
				continue;

			color = 0;
			String id = list.get(i).getTestId();
			if (colorMap.containsKey(id)) {
				color = colorMap.get(id);
			}
			Map<String, Object> map = new HashMap<String, Object>();

			if (idMap.containsKey(id)) {
				String testId = list.get(i).getTestId();
				map.put("id", id);
				map.put("color", color);
				map.put("name", idMap.get(list.get(i).getTestId()).getChineseName());
				map.put("result", list.get(i).getTestResult());
				map.put("last", resultMap1.size() != 0 && resultMap1.containsKey(testId) ? resultMap1.get(testId) : "");
				map.put("last1", resultMap2.size() != 0 && resultMap2.containsKey(testId) ? resultMap2.get(testId) : "");
				map.put("device", list.get(i).getDeviceId());
				String lo = list.get(i).getRefLo();
				String hi = list.get(i).getRefHi();
				if (lo != null && hi != null) {
					map.put("scope", lo + "-" + hi);
				} else {
					map.put("scope", "");
				}
				map.put("unit", list.get(i).getUnit());
				map.put("knowledgeName", idMap.get(list.get(i).getTestId()).getKnowledgeName());
				map.put("editMark", list.get(i).getEditMark());
				dataRows.add(map);
			}

		}
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		userdata.put("hisDate", hisDate);
		dataResponse.setUserdata(userdata);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/**
	 * 获取某一样本的检验数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/twoColumn*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getTwoColumn(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("id");
		DataResponse dataResponse = new DataResponse();
		Map<String, Object> userdata = new HashMap<String, Object>();
		Set<String> hisDateSet = new HashSet<String>();

		if (sampleNo == null) {
			throw new NullPointerException();
		}
		if (idMap.size() == 0)
			initMap();

		PatientInfo info = patientInfoManager.getBySampleNo(sampleNo);
		Map<String, String> resultMap1 = new HashMap<String, String>();
		Map<String, String> resultMap2 = new HashMap<String, String>();
		Map<String, String> resultMap3 = new HashMap<String, String>();
		List<PatientInfo> list = patientInfoManager.getHistorySample(info.getPatientId(), info.getYlxh());
		long curInfoReceiveTime = info.getReceivetime().getTime();
		int index = 0;
		Map<String, String> rmap = null;
		
		for (PatientInfo pinfo : list) {
			if (pinfo.getReceivetime().getTime() < curInfoReceiveTime && index <= 2) {
				if (index == 0)
					rmap = resultMap1;
				else if (index == 1)
					rmap = resultMap2;
				else
					rmap = resultMap3;
				
				for (TestResult result : pinfo.getResults()) {
					rmap.put(result.getTestId(), result.getTestResult());
				}
				hisDateSet.add(mdf.format(pinfo.getReceivetime()));
				index++;
			}
		}
		
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		Map<String, Integer> colorMap = StringToMap(info.getMarkTests());
		List<TestResult> trList = testResultManager.getBySampleNo(sampleNo);
		List<TestResult> afterFilter = new ArrayList<TestResult>();
		
		for (int i = 0; i < trList.size(); i++) {
			TestResult tr = trList.get(i);
			if (tr.getEditMark() != Constants.DELETE_FLAG && idMap.containsKey(tr.getTestId()))
				afterFilter.add(tr);
		}
		
		int center = (afterFilter.size() + 1) / 2;
		
		for (int i = 0, j = center; i < center; i++, j++) {
			TestResult tr1 = afterFilter.get(i);
			TestResult tr2 = j < afterFilter.size() ? afterFilter.get(j) : null;
			Map<String, Object> map = new HashMap<String, Object>();
			
			{
				String id = tr1.getTestId();
				int color = colorMap.containsKey(id) ? colorMap.get(id) : 0;
				String lo = tr1.getRefLo();
				String hi = tr1.getRefHi();
				TestDescribe des = idMap.get(id);
				map.put("num", i+1);
				map.put("id", id);
				map.put("color", color);
				map.put("name", des.getChineseName());
				map.put("result", tr1.getTestResult());
				map.put("last", resultMap1.containsKey(id) ? resultMap1.get(id) : "");
				map.put("last1", resultMap2.containsKey(id) ? resultMap2.get(id) : "");
				map.put("last2", resultMap3.containsKey(id) ? resultMap3.get(id) : "");
				map.put("device", tr1.getOperator());
				map.put("scope", lo != null && hi != null ? lo + "-" + hi : "");
				map.put("unit", tr1.getUnit());
				map.put("knowledgeName", des.getKnowledgeName());
			}
			
			if (tr2 != null) {
				String id = tr2.getTestId();
				int color = colorMap.containsKey(id) ? colorMap.get(id) : 0;
				String lo = tr2.getRefLo();
				String hi = tr2.getRefHi();
				TestDescribe des = idMap.get(id);
				map.put("_num", j+1);
				map.put("_id", id);
				map.put("_color", color);
				map.put("_name", des.getChineseName());
				map.put("_result", tr2.getTestResult());
				map.put("_last", resultMap1.containsKey(id) ? resultMap1.get(id) : "");
				map.put("_last1", resultMap2.containsKey(id) ? resultMap2.get(id) : "");
				map.put("_last2", resultMap3.containsKey(id) ? resultMap3.get(id) : "");
				map.put("_device", tr2.getOperator());
				map.put("_scope", lo != null && hi != null ? lo + "-" + hi : "");
				map.put("_unit", tr2.getUnit());
				map.put("_knowledgeName", des.getKnowledgeName());
			}
			
			dataRows.add(map);
		}
		dataResponse.setRecords(center);
		dataResponse.setRows(dataRows);
		userdata.put("hisDate", setToString(hisDateSet));
		dataResponse.setUserdata(userdata);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	

	private Map<String, Integer> StringToMap(String ts) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : ts.split(";")) {
			if (!"".equals(s) && s.contains(":")) {
				String[] array = s.split(":");
				map.put(array[0], Integer.parseInt(array[1]));
			}
		}
		return map;
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
		String text = request.getParameter("text");
		String sample = request.getParameter("sample");
		String lab = request.getParameter("lab");
		int page = Integer.parseInt(pages);
		int row = Integer.parseInt(rows);
		int mark = 0;
		int status = -3;
		
		if (idMap.size() == 0)
			initMap();

		if (!StringUtils.isEmpty(request.getParameter("mark"))) {
			mark = Integer.parseInt(request.getParameter("mark"));
		}

		if (!StringUtils.isEmpty(request.getParameter("status"))) {
			status = Integer.parseInt(request.getParameter("status"));
		}

		if (!StringUtils.isEmpty(sample)) {
			text = sample;
		} else {
			text = new SimpleDateFormat("yyyyMMdd").format(new Date());
		}

		User operator = UserUtil.getCurrentUser(request, userManager);
		DataResponse dataResponse = new DataResponse();
		List<PatientInfo> list = new ArrayList<PatientInfo>();

		if (status < 1)
			mark = 0;

		if (!StringUtils.isEmpty(text)) {
			text = text.toUpperCase();
			switch (text.length()) {
			case 3:
				if ("ALL".equals(text)) {
					list = patientInfoManager.getSampleList("", lab, operator.getLabCode(), mark, status);
				}
				break;
			case 8:
				if (StringUtils.isNumeric(text)) {
					list = patientInfoManager.getSampleList(text, lab, operator.getLabCode(), mark, status);
				}
				break;
			case 11:
				if (StringUtils.isNumeric(text.substring(0, 8))) {
					if (operator.getLabCode().indexOf(text.substring(8)) != -1) {
						list = patientInfoManager.getSampleList(text.substring(0, 8), lab, text.substring(8),
								mark, status);
					}
				}
				break;
			case 14:
				if (StringUtils.isNumeric(text.substring(0, 8)) && StringUtils.isNumeric(text.substring(11))) {
					if (operator.getLabCode().indexOf(text.substring(8, 11)) != -1) {
						list = patientInfoManager.getListBySampleNo(text);
					}
				}
				break;
			case 18:
				if (text.indexOf('-') != 0 && StringUtils.isNumeric(text.substring(0, 8))
						&& StringUtils.isNumeric(text.substring(11, 14))
						&& StringUtils.isNumeric(text.substring(15, 18))
						&& operator.getLabCode().indexOf(text.substring(8, 11)) != -1) {
					List<PatientInfo> result = patientInfoManager.getSampleList(text.substring(0, 8), lab,
							text.substring(8, 11), mark, status);

					list = new ArrayList<PatientInfo>();
					int start = Integer.parseInt(text.substring(11, 14));
					int end = Integer.parseInt(text.substring(15, 18));
					// 过滤
					for (PatientInfo patient : result) {
						int index = Integer.parseInt(patient.getSampleNo().substring(11));
						if (index >= start && index <= end) {
							list.add(patient);
						}
					}
				}
				break;
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
			map.put("id", info.getId());
			map.put("mark", info.getAuditMarkValue());
			map.put("sample", info.getSampleNo());
			map.put("status", info.getAuditStatusValue());
			map.put("flag", info.getModifyFlag());
			map.put("size", 0);
			if (info.getResultStatus()>=5) {
				map.put("lisPass", "√");
			} else {
				map.put("lisPass", "");
			}
			dataRows.add(map);
			index++;
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
	@RequestMapping(value = "/list*", method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SectionUtil sectionutil = SectionUtil.getInstance(syncManager);
		String strToday = df.format(new Date());
		User operator = UserUtil.getCurrentUser(request, userManager);
		String library = "";
		String department = operator.getDepartment();
		Map<String, String> depart = new HashMap<String, String>();
		if (operator.getLastLibrary() != null) {
			library = operator.getLastLibrary();
		}
		if (department != null) {
			for (String s : department.split(",")) {
				depart.put(s, sectionutil.getValue(s));
				if (StringUtils.isEmpty(library)) {
					library = s;
				}
			}
		}
		Map<String, Code> actiCodeMap = new HashMap<String, Code>();
		String labCode = operator.getLabCode();
		String activeCode = operator.getActiveCode();
		if (!StringUtils.isEmpty(labCode)) {
			String[] codes = labCode.split(",");
			for (String code : codes) {
				Code nCode = new Code();
				nCode.setActive(false);
				nCode.setLabCode(code);
				actiCodeMap.put(code, nCode);
			}
		}
		if (!StringUtils.isEmpty(activeCode)) {
			String[] codes = activeCode.split(",");
			for (String code : codes) {
				if (actiCodeMap.containsKey(code)) {
					Code nCode = actiCodeMap.get(code);
					nCode.setActive(true);
				}
			}
		}

		HttpSession session = request.getSession();
		String scope = (String) session.getAttribute("scope");
		if (!StringUtils.isEmpty(scope)) {
			String[] sp = scope.split(";");
			for (String s : sp) {
				String[] codeScope = s.split(":");
				String[] loHi = codeScope[1].split("-");
				if (actiCodeMap.containsKey(codeScope[0])) {
					Code nCode = actiCodeMap.get(codeScope[0]);
					nCode.setLo(loHi[0]);
					nCode.setHi(loHi[1]);
				}
			}
		}

		Boolean isAuto = (Boolean) request.getSession().getAttribute("isAuto");
		if (isAuto == null) {
			isAuto = false;
		}
		
		List<Code> codeList = new ArrayList<Code>();
		Object[] obj = actiCodeMap.keySet().toArray();
		Arrays.sort(obj);
		for (Object o : obj) {
			codeList.add(actiCodeMap.get(o.toString()));
		}
		
		// System.out.println(library);
		if (operator.getLastProfile() != null) {
			request.setAttribute("lastProfile", operator.getLastProfile());
		}
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("date", dateformat.format(new Date()));
		request.setAttribute("activeAuto", isAuto);
		request.setAttribute("catcherUrl", Config.getCatcherUrl());
		request.setAttribute("strToday", strToday);
		request.setAttribute("userCode", operator.getLabCode());
		request.setAttribute("library", library);
		request.setAttribute("departList", depart);
		request.setAttribute("codeList", codeList);
		request.setAttribute("checkOperator", operator.getUsername());
		request.setAttribute("operator", operator.getLastName() + " " + operator.getFirstName());
		return new ModelAndView("explain/audit");
	}

	@RequestMapping(value = "/count*", method = RequestMethod.GET)
	@ResponseBody
	public String getSampleCount(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject json = new JSONObject();
		String strToday = df.format(new Date());
		User operator = UserUtil.getCurrentUser(request, userManager);

		String department = operator.getDepartment();
		if (StringUtils.isEmpty(operator.getLastLibrary()) && department != null) {
			String[] deps = department.split(",");
			if (deps.length >= 1) {
				operator.setLastLibrary(deps[0]);
				userManager.save(operator);
			}
		}
		if (!StringUtils.isEmpty(operator.getLastLibrary()) && !StringUtils.isEmpty(operator.getLabCode())) {
			// List<Integer> list = patientInfoManager.getAuditInfo("", operator.getLastLibrary(),
			// operator.getLabCode());
			List<Integer> todayList = patientInfoManager.getAuditInfo(strToday, operator.getLastLibrary(),
					operator.getLabCode(), operator.getUsername());
			// json.put("unaudit", list.get(0));
			// json.put("unpass", list.get(1));
			json.put("todayunaudit", todayList.get(0));
			json.put("todayunpass", todayList.get(1));
			json.put("dangerous", todayList.get(2));
			json.put("needwriteback", todayList.get(3));
			TaskManagerUtil manager = TaskManagerUtil.getInstance();
			long interval = new Date().getTime() - manager.getLastFinishTime(operator.getUsername());
			// 0:不需要自动审核
			// 1:正在自动审核
			// 2:需要自动审核
			if (!startBackAudit) {
				// System.out.println(interval);
				if (manager.isAuditing(operator.getUsername())) {
					json.put("status", 1);
					// } else if (todayList.get(0) > MAX_UNAUDIT_COUNT || todayList.get(0) != 0 && interval >
					// AUDIT_INTERVAL_TIME) {
				} else if (todayList.get(0) != 0 && interval > AUDIT_INTERVAL_TIME) {
					json.put("status", 2);
				} else {
					json.put("status", 0);
				}
			} else {
				json.put("status", 0);
			}
		} else {
			// json.put("unaudit", 0);
			// json.put("unpass", 0);
			json.put("todayunaudit", 0);
			json.put("todayunpass", 0);
			json.put("dangerous", 0);
			json.put("needwriteback", 0);
			json.put("status", 0);
		}

		return json.toString();
	}

	@RequestMapping(value = "/ajax/writeBack*", method = RequestMethod.GET)
	@ResponseBody
	public void getNeedWriteBack(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sample = df.format(new Date());
		User operator = UserUtil.getCurrentUser(request, userManager);
		String labdepartment = null;
		if (!operator.isAdmin()) {
			operator.getLastLibrary();
		}
		List<User> userList = syncManager.getAllWriteBack(sample, labdepartment);
		JSONArray array = new JSONArray();
		for (User user : userList) {
			JSONObject obj = new JSONObject();
			obj.put("checker", user.getUsername());
			obj.put("name", user.getLastName() + " " + user.getFirstName());
			obj.put("code", user.getLabCode());
			obj.put("lab", user.getLastLibrary());
			obj.put("count", user.getWriteBackCount());
			obj.put("list", user.getWebsite());
			array.put(obj);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}

	@RequestMapping(value = "/activeCode*", method = RequestMethod.POST)
	@ResponseBody
	public void activeCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String cd = request.getParameter("code");
		String active = request.getParameter("active");

		User operator = UserUtil.getCurrentUser(request, userManager);
		String activeCode = operator.getActiveCode();
		Set<String> codeSet = new HashSet<String>();

		if (!StringUtils.isEmpty(activeCode)) {
			String[] codes = activeCode.split(",");
			for (String code : codes) {
				codeSet.add(code);
			}
		}

		if ("true".equals(active)) {
			codeSet.add(cd);
		} else {
			codeSet.remove(cd);
		}
		operator.setActiveCode(setToString(codeSet));
		userManager.save(operator);
	}

	@RequestMapping(value = "/print*", method = RequestMethod.GET)
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("docId", request.getParameter("docId"));
		request.setAttribute("sampleNo", request.getParameter("sampleNo"));
		request.setAttribute("showLast", request.getParameter("last"));
		return new ModelAndView("auditPrint");
	}

	@RequestMapping(value = "/samplePrint*", method = RequestMethod.GET)
	public ModelAndView samplePrint(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("text", request.getParameter("text"));
		return new ModelAndView("samplePrint");
	}

	@RequestMapping(value = "/samplePrintData*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getSamplePrintData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String text = request.getParameter("text");
		User operator = UserUtil.getCurrentUser(request, userManager);
		int mark = 0;
		int status = 2;
		List<PatientInfo> list = new ArrayList<PatientInfo>();
		switch (text.length()) {
		case 8:
			if (StringUtils.isNumeric(text)) {
				list = patientInfoManager.getSampleList(text, operator.getDepartment(), operator.getLabCode(),
						mark, status);
			}
			break;
		case 11:
			if (StringUtils.isNumeric(text.substring(0, 8))) {
				if (operator.getLabCode().indexOf(text.substring(8)) != -1) {
					list = patientInfoManager.getSampleList(text.substring(0, 8), operator.getDepartment(),
							text.substring(8), mark, status);
				}
			}
			break;
		case 14:
			if (StringUtils.isNumeric(text.substring(0, 8)) && StringUtils.isNumeric(text.substring(11))) {
				if (operator.getLabCode().indexOf(text.substring(8, 11)) != -1) {
					PatientInfo info = patientInfoManager.getBySampleNo(text);
					if (info != null) {
						list = new ArrayList<PatientInfo>();
						list.add(info);
					}
				}
			}
			break;
		case 18:
			if (text.indexOf('-') != 0 && StringUtils.isNumeric(text.substring(0, 8))
					&& StringUtils.isNumeric(text.substring(11, 14)) && StringUtils.isNumeric(text.substring(15, 18))
					&& operator.getLabCode().indexOf(text.substring(8, 11)) != -1) {
				List<PatientInfo> result = patientInfoManager.getSampleList(text.substring(0, 8),
						operator.getDepartment(), text.substring(8, 11), mark, status);

				list = new ArrayList<PatientInfo>();
				int start = Integer.parseInt(text.substring(11, 14));
				int end = Integer.parseInt(text.substring(15, 18));
				// 过滤
				for (PatientInfo patient : result) {
					int index = Integer.parseInt(patient.getSampleNo().substring(11));
					if (index >= start && index <= end) {
						list.add(patient);
					}
				}
			}
			break;
		}

		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(1);
		dataResponse.setTotal(1);

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(list.size());
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			PatientInfo info = list.get(i);
			map.put("sample", info.getSampleNo());
			map.put("status", info.getAuditStatusValue());
			map.put("mark", info.getAuditMarkValue());
			String note = info.getNotes();
			Set<String> testSet = new HashSet<String>();
			if (!StringUtils.isEmpty(info.getRuleIds())) {
				for (Rule rule : ruleManager.getRuleList(info.getRuleIds())) {
					if (rule.getType() == 3 || rule.getType() == 4) {
						String result = rule.getResultName();
						String itemString = "";
						for (Item item : rule.getItems()) {
							testSet.add(item.getIndex().getIndexId());
							itemString = itemString + item.getIndex().getName() + "、";
						}
						if (note != null && !note.isEmpty()) {
							note = note + "<br/>" + itemString.substring(0, itemString.length() - 1) + "异常，" + result;
						} else {
							note = itemString.substring(0, itemString.length() - 1) + "异常，" + result;
						}
					}
				}
			}
			String testString = setToString(testSet);
			List<TestResult> testList = testResultManager.getListByTestString(info.getSampleNo(), testString);
			String testValue = "";
			if (idMap.size() == 0)
				initMap();
			for (TestResult t : testList) {
				testValue = testValue + idMap.get(t.getTestId()).getChineseName() + ": " + t.getTestResult() + ";<br/>";
			}
			map.put("reason", note);
			map.put("value", testValue);

			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	public void startBackAudit(String sample) throws Exception {

		/*
		 * final List<PatientInfo> patient = new ArrayList<PatientInfo>(); final Map<String, Diff> diff = new
		 * HashMap<String, Diff>(); final Map<Long, PatientInfo> diffData = new HashMap<Long, PatientInfo>(); final
		 * Map<String, Ratio> ratio = new HashMap<String, Ratio>();
		 * 
		 * initDrools(); // drools将规则初始化成 knowledgebase
		 * 
		 * try { patient.addAll(patientInfoManager.getHasResult(sample, ONCE_MAX_AUDIT)); // 由于延迟加载,在session关闭前获取检验数据
		 * for (PatientInfo info : patient) { List<PatientInfo> list = patientInfoManager.getDiffCheck(info); if
		 * (list.size() >= 2) { PatientInfo last_p = list.get(1); diffData.put(info.getId(), last_p); } }
		 * diff.putAll(AuditUtil.getDealDiff(ruleManager,"(0,1)")); ratio.putAll(AuditUtil.getDealRatio(ruleManager)); }
		 * catch (Exception e) { log.error("加载数据出错", e); return; }
		 * 
		 * for (PatientInfo info : patient) { try { boolean error = false; info.setAuditMark(1); info.setNotes("");
		 * info.setRuleIds(""); String lack = checkLack(info); // 检测是否少做
		 * 
		 * if (StringUtils.isEmpty(lack)) { // 未少做，执行差值比值校验 error = checkDiffAndRatio(diff, diffData, ratio, info,
		 * error); } else { // 将少做项生成字符创信息 error = buildLackNotes(info, lack); } // 获取并处理drools推理出的结果
		 * getDroolsResult(info, error);
		 * 
		 * patientInfoManager.save(info); Thread.sleep(100);
		 * 
		 * } catch (Exception e) { log.error("样本:" + info.getId() + "审核出错！", e); } } log.info(patient.size() +
		 * "个样本已审完");
		 * 
		 * return;
		 */
	}

	/**
	 * 自动审核
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/result*", method = RequestMethod.GET)
	public void getAuditResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sample = request.getParameter("sample").toUpperCase();
		final String currentUser = request.getRemoteUser();
		final List<PatientInfo> patient = new ArrayList<PatientInfo>();
		final Task task = createTask(sample, currentUser);
		TaskManagerUtil manager = TaskManagerUtil.getInstance();
		Map<Long, PatientInfo> diffData = new HashMap<Long, PatientInfo>();
		User operator = UserUtil.getCurrentUser(request, userManager);
		manager.addOperatorName(currentUser);
		manager.addTask(task);

		try {
			getPatientInfo(patient, sample, request, operator);
			task.setSampleCount(patient.size());
			getLastInfo(patient, diffData, task);	// 由于延迟加载,在session关闭前获取检验数据
		} catch (Exception e) {
			log.error(e.getMessage());
			manager.removeOperatorName(currentUser);
			task.setEndTime(new Date());
			task.setStatus(THREAD_STOPPED);
			return;
		}

		initDrools(); // drools将规则初始化成 knowledgebase

		final Check jyzCheck = new JyzCheck(syncManager, userManager);
		final Check lackCheck = new LackCheck(ylxhMap, idMap);
		final Check diffCheck = new DiffCheck(idMap, ruleManager, diffData);
		final Check ratioCheck = new RatioCheck(idMap, ruleManager);
		final DroolsCheck reTestCheck = new RetestCheck(ruleManager);
		final DroolsCheck dangerCheck = new DangerCheck(ruleManager);
		final DroolsCheck alarm2Check = new Alarm2Check(ruleManager);
		final DroolsCheck alarm3Check = new Alarm3Check(ruleManager);
		final DroolsCheck extremeCheck = new ExtremeCheck(ruleManager);
		final Check bayesCheck = new BayesCheck(bayesService);

		manager.execute(new Runnable() {

			@Override
			public void run() {
				int index = 0;
				for (PatientInfo info : patient) {
					try {
						if (info.getResults().size()>0) {
							initVariable(task, currentUser, info); // 初始化变量
							jyzCheck.doCheck(info);
							boolean lack = lackCheck.doCheck(info);
							diffCheck.doCheck(info);
							ratioCheck.doCheck(info);
							R r = DroolsRunner.getInstance().getResult(info.getResults(), info);
							if (!r.getRuleIds().isEmpty()) {
								reTestCheck.doCheck(info, r);
								alarm2Check.doCheck(info, r);
								alarm3Check.doCheck(info, r);
								extremeCheck.doCheck(info, r);
								if (!lack && info.getAuditMark() != Check.LACK_MARK) {
									info.setAuditMark(Check.LACK_MARK);
								}
								dangerCheck.doCheck(info, r);
							}
							bayesCheck.doCheck(info); // Bayes审核及学习
						} 
						savePatientInfo(info);
						saveAuditTrace(info);
					} catch (Exception e) {
						log.error("样本:" + info.getSampleNo() + "审核出错！", e);
					} finally {
						if (task.hasStopped())
							break; // 终止线程
						task.setFinishCount(++index);
					}
				}
				updateResultInfo(currentUser, task);
			}

			private void savePatientInfo(PatientInfo info) throws Exception {
				if (info.getAuditStatus() != Constants.STATUS_UNPASS) {
					info.setWriteBack(1);
				}
				patientInfoManager.save(info);
				Thread.sleep(100);
			}
			
			private void saveAuditTrace(PatientInfo info) throws Exception {
				AuditTrace a = new AuditTrace();
				a.setSampleno(info.getSampleNo());
				a.setChecktime(info.getCheckTime());
				a.setChecker(info.getCheckOperator());
				a.setType(1);
				a.setStatus(info.getAuditStatus());
				auditTraceManager.save(a);
			}

			private void updateResultInfo(final String currentUser, final Task task) {
				task.setEndTime(new Date());
				if (!task.hasStopped()) {
					task.setStatus(THREAD_FINISHED);
				}
				taskManager.save(task);
				TaskManagerUtil.getInstance().removeOperatorName(currentUser);
			}
		});

		return;
	}

	private void getLastInfo(final List<PatientInfo> patient, final Map<Long, PatientInfo> diffData, final Task task)
			throws Exception {
		if (patient.size() == 0) {
			throw new Exception("无数据！");
		}

		for (PatientInfo info : patient) {
			if (task.getStatus() == THREAD_STOPPED) {
				throw new Exception("作业被停止");
			}
			Set<TestResult> now = info.getResults();
			Set<String> testIdSet = new HashSet<String>();
			for (TestResult t : now) {
				testIdSet.add(t.getTestId());
			}
			System.out.println(info.getSampleNo()+" : " + now.size());
			//List<PatientInfo> list = patientInfoManager.getHistoryTable(info.getPatientId(), info.getLabdepartMent());
			List<PatientInfo> list = patientInfoManager.getDiffCheckHistory(info);
			for (PatientInfo p : list) {
				boolean isHis = false;
				Set<TestResult> his = p.getResults();
				for (TestResult t : his) {
					if (testIdSet.contains(t.getTestId())) {
						isHis = true;
						break;
					}
				}
				
				if (isHis) {
					diffData.put(info.getId(), p);
					break;
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private String getRemoveCodes(String sample, User operator) {
		String needAuditCode = "";
		if (df.format(new Date()).equals(sample)) {
			needAuditCode = TaskManagerUtil.getInstance().getNeedAuditCode(operator.getActiveCode());
		}
		return needAuditCode;
	}

	private void initVariable(final Task task, final String operator, PatientInfo info) {
		task.setStatus(THREAD_RUNNING);
		info.setMarkTests("");
		info.setAuditStatus(Check.PASS);
		info.setAuditMark(Check.AUTO_MARK);
		info.setNotes("");
		info.setRuleIds("");
		info.setIntelExplain("");
		info.setCheckTime(new Date());
		info.setCheckOperator(operator);
		info.setCheckerOpinion(Check.AUTO_AUDIT);
	}

	private void getPatientInfo(final List<PatientInfo> patient, String sample, HttpServletRequest request,
			User operator) throws Exception {

		int status = 0;
		String reAudit = request.getParameter("reaudit");
		String auto = request.getParameter("auto");

		String preText = sample;
		String code;
		if (auto == null) {
			code = operator.getLabCode();
		} else {
			code = operator.getActiveCode();
		}
		if (reAudit != null) {
			try {
				if (Boolean.valueOf(reAudit)) {
					status = -2;
				}
			} catch (Exception e) {
			}
		}

		if (StringUtils.isEmpty(code)) {
			throw new Exception("该用户没有被分配代码段！");
		}

		if (sample.length() >= 11 && code.indexOf(sample.substring(8, 11)) == -1) {
			throw new Exception("没有Code:" + sample.substring(8, 11) + "的权限!");
		}

		if (sample.length() == 8 && StringUtils.isNumeric(sample)) {
		} else if (sample.length() == 11 && StringUtils.isNumeric(sample.substring(0, 8))) {
			preText = sample.substring(0, 8);
			code = sample.substring(8);
		} else if (sample.length() == 14) {
			preText = sample;
			code = sample.substring(8, 11);
		} else if (sample.length() == 18) {
			preText = sample.substring(0, 8);
			code = sample.substring(8, 11);
		} else {
			throw new Exception("格式不符合要求!");
		}

		// 更新数据
		/*boolean needSync = false;
		if (needSync) {
			SyncUtil syncUtil = new SyncUtil(syncManager, preText, operator.getLastLibrary(), code);
			syncUtil.Sync();
		}*/
		List<PatientInfo> patientInfo = new ArrayList<PatientInfo>();

		if (sample.length() != 14) {
			List<PatientInfo> patientUnauditList = patientInfoManager.getSampleList(preText, operator.getLastLibrary(), code, 0, status);
			patientInfo.addAll(patientUnauditList);
			if (auto != null) {
				List<PatientInfo> patientLackList = patientInfoManager.getSampleList(preText, operator.getLastLibrary(), code, 4, 2);
				patientInfo.addAll(patientLackList);
			}
		} else {
			List<PatientInfo> simpleInfo = patientInfoManager.getListBySampleNo(preText);
			if (simpleInfo != null && operator.getLastLibrary().indexOf(simpleInfo.get(0).getLabdepartMent()) != -1) {
				patientInfo = simpleInfo;
			}
		}

		if (sample.length() == 18) {
			int start = Integer.valueOf(sample.substring(11, 14));
			int end = Integer.valueOf(sample.substring(15, 18));
			System.out.println(start + "," + end);
			Iterator<PatientInfo> itr = patientInfo.iterator();
			while (itr.hasNext()) {
				PatientInfo info = itr.next();
				int index = Integer.parseInt(info.getSampleNo().substring(11));
				if (index < start || index > end) {
					itr.remove();
				}
			}
		}

		if (auto != null) {
			HttpSession session = request.getSession();
			String scope = (String) session.getAttribute("scope");
			if (!StringUtils.isEmpty(scope)) {
				// System.out.println(scope);
				String[] sp = scope.split(";");
				for (String s : sp) {
					String[] codeScope = s.split(":");
					String[] loHi = codeScope[1].split("-");
					int start = Integer.valueOf(loHi[0]);
					int end = Integer.valueOf(loHi[1]);
					Iterator<PatientInfo> itr = patientInfo.iterator();
					while (itr.hasNext()) {
						PatientInfo info = itr.next();
						String preSampleNo = info.getSampleNo().substring(8, 11);
						if (preSampleNo.equals(codeScope[0])) {
							int index = Integer.parseInt(info.getSampleNo().substring(11));
							if (index < start || index > end) {
								itr.remove();
							}
						}
					}
				}
			}

		}
		
		patient.addAll(patientInfo);

		/*if (patientInfo.size() <= ONCE_MAX_AUDIT) {
			patient.addAll(patientInfo);
		} else {
			for (int i = 0; i < ONCE_MAX_AUDIT; i++) {
				patient.add(patientInfo.get(i));
			}
		}*/
		
		fillTestResult(patient, operator);
	}

	private void fillTestResult(List<PatientInfo> patient, User operator) throws Exception {
		
		if (fillUtil == null) {
			List<Describe> desList = syncManager.getAllDescribe();
			List<ReferenceValue> refList = syncManager.getAllReferenceValue();
			fillUtil = FillFieldUtil.getInstance(desList, refList);
		}
		
		if (formulaUtil == null) {
			formulaUtil = FormulaUtil.getInstance(syncManager, testResultManager, patientInfoManager, idMap, fillUtil);
		}
		
		for (PatientInfo info : patient) {
			try {
				formulaUtil.formula(info, operator.getUsername());
			} catch (Exception e) {
				log.error("样本"+info.getSampleNo()+"出错:\r\n", e);
			}
		}
	}


	private void initDrools() throws IOException {

		if (idMap.size() == 0)
			initMap();
		if (ylxhMap.size() == 0)
			initYLXHMap();

		if (!DroolsRunner.getInstance().isBaseInited()) {
			AnalyticUtil util = new AnalyticUtil(itemManager, resultManager);
			Reader reader = util.getReader(ruleManager.getRuleByTypes("0,3,4,5,6,7"));
			DroolsRunner.getInstance().buildKnowledgeBase(idMap, reader);
			reader.close();
		}
	}

	private Task createTask(String sample, final String currentUser) {

		// 创建一个工作实体
		Task task = new Task();
		task.setStartBy(currentUser);
		task.setStartTime(new Date());

		// 将task存入数据库，获得为唯一的id值
		Task newTask = taskManager.save(task);
		newTask.setSearchText(sample);
		return newTask;
	}

	@RequestMapping(value = "/manual*", method = RequestMethod.POST)
	@ResponseBody
	public boolean manualAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = false;

		String op = request.getParameter("operate");
		String note = request.getParameter("note");
		String sample = request.getParameter("sample");
		List<PatientInfo> list = patientInfoManager.getListBySampleNo(sample);

		try {
			for (PatientInfo info : list) {
				//info.setCheckerOpinion(note);
				//BayesUtil util = BayesUtil.getInstance(bayesService);
				info.setPassReason(note);
				if ("pass".equals(op)) {
					info.setAuditStatus(1);
				} else if ("unpass".equals(op)) {
					info.setAuditStatus(2);
				}
				info.setCheckOperator(request.getRemoteUser());
				info.setCheckTime(new Date());
				String profileName = info.getSampleNo().substring(8, 11);
				String deviceId = null;
				for (TestResult tr : info.getResults()) {
					if (deviceId == null) {
						deviceId = tr.getDeviceId();
						break;
					}
				}
				if (StringUtils.isEmpty(info.getChkoper2())) {
					info.setChkoper2(FillFieldUtil.getJYZ(syncManager, profileName, deviceId));
				}
				info.setWriteBack(1);
				info.setCheckerOpinion(Check.MANUAL_AUDIT);
				patientInfoManager.save(info);
				result = true;
				
				AuditTrace a = new AuditTrace();
				a.setSampleno(info.getSampleNo());
				a.setChecktime(info.getCheckTime());
				a.setChecker(info.getCheckOperator());
				a.setType(2);
				a.setStatus(info.getAuditStatus());
				auditTraceManager.save(a);
			}
		} catch (Exception e) {
			log.error("通过或不通过出错！", e);
		}
		return result;
	}

	@RequestMapping(value = "/batch*", method = RequestMethod.POST)
	@ResponseBody
	public boolean batchManualAudit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = true;

		String ids = request.getParameter("ids");
		String op = request.getParameter("op");
		int status = Constants.STATUS_UNAUDIT;
		boolean pass = false;
		if ("pass".equals(op)) {
			status = Constants.STATUS_PASSED;
			pass = true;
		} else if ("unpass".equals(op)) {
			status = Constants.STATUS_UNPASS;
		}

		//BayesUtil util = BayesUtil.getInstance(bayesService);
		for (String id : ids.split(",")) {
			PatientInfo info = patientInfoManager.get(Long.parseLong(id));
			if (info.getAuditStatus() == Constants.STATUS_PASSED + Constants.STATUS_UNPASS - status) {
				info.setAuditStatus(status);
				info.setCheckOperator(request.getRemoteUser());
				info.setCheckTime(new Date());
				String profileName = info.getSampleNo().substring(8, 11);
				String deviceId = null;
				for (TestResult tr : info.getResults()) {
					if (deviceId == null) {
						deviceId = tr.getDeviceId();
						break;
					}
				}
				if (StringUtils.isEmpty(info.getChkoper2())) {
					info.setChkoper2(FillFieldUtil.getJYZ(syncManager, profileName, deviceId));
				}
				info.setWriteBack(1);
				if (pass) {
					info.setPassReason("批量通过");
					AuditTrace a = new AuditTrace();
					a.setSampleno(info.getSampleNo());
					a.setChecktime(info.getCheckTime());
					a.setChecker(info.getCheckOperator());
					a.setType(2);
					a.setStatus(info.getAuditStatus());
					auditTraceManager.save(a);
					
				}
				patientInfoManager.save(info);
			}
		}

		return result;
	}

	@RequestMapping(value = "/tat*", method = RequestMethod.GET)
	@ResponseBody
	public String getTAT(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id = request.getParameter("id");

		if (id == null)
			throw new NullPointerException();

		JSONObject json = new JSONObject();
		PatientInfo info = patientInfoManager.get(Long.parseLong(id));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		json.put("request", info.getRequesttime() == null ? "" : sdf.format(info.getRequesttime()));
		json.put("execute", info.getExecutetime() == null ? "" : sdf.format(info.getExecutetime()));
		json.put("receive", info.getReceivetime() == null ? "" : sdf.format(info.getReceivetime()));
		json.put("audit", info.getCheckTime() == null ? "" : sdf.format(info.getCheckTime()));
		json.put("send", info.getSendTime() == null ? "" : sdf.format(info.getSendTime()));
		json.put("ksreceive", info.getKsReceiveTime() == null ? "" : sdf.format(info.getKsReceiveTime()));
		json.put("auditor", info.getCheckOperator() == null ? "" : info.getCheckOperator());
		
		
		Date resultTime = new Date(0);
		for (TestResult result : info.getResults()) {
			Date meaTime = result.getMeasureTime();
			if (meaTime != null && meaTime.getTime() > resultTime.getTime()) {
				resultTime = meaTime;
			}
		}
		json.put("result", sdf.format(resultTime));
		String diff = "";
		if (info.getCheckTime() != null && info.getExecutetime() != null) {
			long df = info.getCheckTime().getTime() - info.getExecutetime().getTime();
			diff = String.valueOf(df / 60000);
		}
		json.put("tat", diff);
		return json.toString();
	}

	/**
	 * 获取样本中检验项目的修改记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/testModify*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getTestModify(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("sampleNo");
		if (StringUtils.isEmpty(sampleNo))
			throw new NullPointerException();

		List<TestModify> modifyList = testModifyManager.getBySampleNo(sampleNo);
		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(1);
		dataResponse.setTotal(1);

		if (modifyList.size() == 0) {
			dataResponse.setRecords(0);
			return dataResponse;
		}

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(modifyList.size());
		if (idMap.size() == 0)
			initMap();

		for (TestModify t : modifyList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("test", idMap.get(t.getTestId()).getChineseName());
			map.put("type", t.getType());
			map.put("oldValue", t.getOldValue());
			map.put("newValue", t.getNewValue());
			map.put("modifyTime", sdf.format(t.getModifyTime()));
			map.put("modifyUser", t.getModifyUser());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	/**
	 * 获取样本中检验项目的修改记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trace*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getAuditTrace(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("sample");
		if (StringUtils.isEmpty(sampleNo))
			throw new NullPointerException();

		List<AuditTrace> traceList = auditTraceManager.getBySampleNo(sampleNo);
		DataResponse dataResponse = new DataResponse();

		if (traceList.size() == 0) {
			dataResponse.setRecords(0);
			return dataResponse;
		}

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		dataResponse.setRecords(traceList.size());
		if (idMap.size() == 0)
			initMap();

		for (AuditTrace t : traceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sampleno", t.getSampleno());
			map.put("checker", t.getChecker());
			map.put("checktime", sdf.format(t.getChecktime()));
			map.put("status", t.getStatusValue());
			map.put("type", t.getTypeValue());
			dataRows.add(map);
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	/**
	 * 获取样本比较的结果
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sampleCompare*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse sampleCompare(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String sampleNo = request.getParameter("sampleNo");
		if (StringUtils.isEmpty(sampleNo))
			throw new NullPointerException();
		List<SyncResult> localList = syncManager.getLocalBySampleNo(sampleNo);
		List<SyncResult> z1List = syncManager.getZ1BySampleNo(sampleNo);

		DataResponse dataResponse = new DataResponse();
		dataResponse.setPage(1);
		dataResponse.setTotal(1);

		if (sampleNo.length() != 14 || localList.size() == 0 || z1List.size() == 0) {
			dataResponse.setRecords(0);
			return dataResponse;
		}

		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();

		if (idMap.size() == 0)
			initMap();

		if (localList.size() > z1List.size()) {
			dataResponse.setRecords(localList.size());
			for (SyncResult r : localList) {
				if (idMap.containsKey(r.getTESTID())) {
					String testid = r.getTESTID();
					int color = 0;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("test", idMap.get(testid).getChineseName());
					map.put("reflo", r.getREFLO());
					map.put("refhi", r.getREFHI());
					map.put("result", r.getTESTRESULT());
					map.put("resultflag", r.getRESULTFLAG());
					map.put("resultstatus", r.getTESTSTATUS());
					for (SyncResult z1_r : z1List) {
						if (z1_r.getTESTID().equals(testid)) {
							if (!z1_r.equals(r)) {
								color = 1;
							}
							map.put("z1_reflo", z1_r.getREFLO());
							map.put("z1_refhi", z1_r.getREFHI());
							map.put("z1_result", z1_r.getTESTRESULT());
							map.put("z1_resultflag", z1_r.getRESULTFLAG());
							map.put("z1_resultstatus", z1_r.getTESTSTATUS());
						}
					}
					map.put("color", color);
					dataRows.add(map);
				}
			}
		} else {
			dataResponse.setRecords(z1List.size());
			for (SyncResult z1_r : z1List) {
				if (idMap.containsKey(z1_r.getTESTID())) {
					String testid = z1_r.getTESTID();
					int color = 0;
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("test", idMap.get(testid).getChineseName());
					map.put("z1_reflo", z1_r.getREFLO());
					map.put("z1_refhi", z1_r.getREFHI());
					map.put("z1_result", z1_r.getTESTRESULT());
					map.put("z1_resultflag", z1_r.getRESULTFLAG());
					map.put("z1_resultstatus", z1_r.getTESTSTATUS());
					for (SyncResult r : localList) {
						if (r.getTESTID().equals(testid)) {
							if (!r.equals(z1_r)) {
								color = 1;
							}
							map.put("reflo", r.getREFLO());
							map.put("refhi", r.getREFHI());
							map.put("result", r.getTESTRESULT());
							map.put("resultflag", r.getRESULTFLAG());
							map.put("resultstatus", r.getTESTSTATUS());
						}
					}
					map.put("color", color);
					dataRows.add(map);
				}
			}
		}

		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}

	@RequestMapping(value = "/statistic*", method = RequestMethod.GET)
	@ResponseBody
	public DataResponse getStatistics(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String code = StringUtils.upperCase(request.getParameter("code"));
		String fromNo = request.getParameter("from");
		String toNo = request.getParameter("to");
		String day = df.format(new Date());
		DecimalFormat deFormat = new DecimalFormat("#.####");
		
		List<PatientInfo> infoList = patientInfoManager.getSampleByPrefix(day + code);
		int start = Integer.parseInt(fromNo);
		int end = Integer.parseInt(toNo);
		
		List<PatientInfo> list = new ArrayList<PatientInfo>();
		// 过滤
		for (PatientInfo patient : infoList) {
			int index = Integer.parseInt(patient.getSampleNo().substring(11));
			if (index >= start && index <= end) {
				list.add(patient);
			}
		}
		
		Map<String, List<Double>> resultMap = new HashMap<String, List<Double>>();
		List<Statistic> statisticList = new ArrayList<Statistic>();
		
		for (PatientInfo info : list) {
			for (TestResult t : info.getResults()) {
				List<Double> resultList = new ArrayList<Double>();
				if(resultMap.containsKey(t.getTestId())){
					resultList = resultMap.get(t.getTestId());
				} else {
					resultMap.put(t.getTestId(), resultList);
				}
				try { 
					double b = Double.parseDouble(t.getTestResult()); 
					resultList.add(b);
				} catch (Exception e){
					log.error(e.getMessage());
				} 
			}
		}
		
		for (String tId : resultMap.keySet()) {
			Statistic s = new Statistic();
			int num = 0;
			Double average;
			Double max = 0.0;
			Double min = 10000.0;
			Double total = 0.0;
			Double standardDeviation;
			Double coefficientOfVariation;
			s.setTestid(tId);
			List<Double> result = resultMap.get(tId);
			for (Double d : result) {
				if(d > max){
					max = d;
				}
				if(d < min){
					min = d;
				}
				total = total + d;
				num = num +1;
			}
			average = total/result.size();
			s.setNum(num);
			s.setAverage(average);
			s.setMax(max);
			s.setMin(min);
			Double variance = 0.0;
			for (Double d : result) {
				variance = variance + Math.pow(d-average, 2);
			}
			standardDeviation = Math.sqrt(variance/result.size());
			coefficientOfVariation = standardDeviation*100/average;
			s.setStandardDeviation(standardDeviation);
			s.setCoefficientOfVariation(coefficientOfVariation);
			statisticList.add(s);
		}
		
		DataResponse dataResponse = new DataResponse();
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		if (idMap.size() == 0)
			initMap();

		for (Statistic s : statisticList) {
			if(idMap.containsKey(s.getTestid())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", s.getTestid());
				map.put("name", idMap.get(s.getTestid()).getChineseName());
				map.put("num", s.getNum());
				map.put("average", deFormat.format(s.getAverage()));
				map.put("max", s.getMax());
				map.put("min", s.getMin());
				map.put("standardDeviation", deFormat.format(s.getStandardDeviation()));
				map.put("coefficientOfVariation", deFormat.format(s.getCoefficientOfVariation()));
				dataRows.add(map);
			}
		}
		
		dataResponse.setRows(dataRows);
		dataResponse.setRecords(dataRows.size());
		
		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	synchronized private void initMap() {
		List<TestDescribe> list = testDescribeManager.getAll();
		for (TestDescribe t : list) {
			idMap.put(t.getTestId(), t);
		}
	}

	synchronized private void initYLXHMap() {
		List<Ylxh> list = ylxhManager.getAll();
		for (Ylxh y : list) {
			String testStr = y.getProfiletest();
			List<String> testList = new ArrayList<String>();
			if (testStr != null) {
				String[] tests = testStr.split(",");
				for (String test : tests) {
					if (test.length() == 4)
						testList.add(test);
				}
			}
			ylxhMap.put(y.getYlxh(), testList);
		}
	}
	
	synchronized private void initSLGIMap() {
		List<SyncLabGroupInfo> list = syncManager.getSyncLabGroupInfo();
		for (SyncLabGroupInfo s : list) {
			slgiMap.put(s.getSPNO(), s.getEXPECT_AVG());
		}
	}
	
	synchronized private void initDiagMap() {
		List<Diagnostic> list = syncManager.getDiagnostic();
		for (Diagnostic d : list) {
			diagMap.put(d.getDIAGNOSTIC(), d.getKNOWLEDGENAME());
		}
	}

	@Autowired
	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	@Autowired
	public void setResultManager(ResultManager resultManager) {
		this.resultManager = resultManager;
	}

	@Autowired
	public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}

	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
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
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
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
	public void setTestModifyManager(TestModifyManager testModifyManager) {
		this.testModifyManager = testModifyManager;
	}
	
	@Autowired
	public void setAuditTraceManager(AuditTraceManager auditTraceManager) {
		this.auditTraceManager = auditTraceManager;
	}

	@Autowired
	public void setReasoningModifyManager(ReasoningModifyManager reasoningModifyManager) {
		this.reasoningModifyManager = reasoningModifyManager;
	}

	@Autowired
	public void setYlxhManager(YlxhManager ylxhManager) {
		this.ylxhManager = ylxhManager;
	}

	@Autowired
	public void setBayesService(BayesService bayesService) {
		this.bayesService = bayesService;
	}

	private String setToString(Set<String> set) {

		String result = "";
		if (set.size() != 0) {
			for (String s : set) {
				if (!result.equals("")) {
					result += ",";
				}
				result += s;
			}
		}
		return result;
	}
}
