package com.zju.webapp.controller.explain;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.drools.DroolsTest;
import com.zju.drools.I;
import com.zju.drools.P;
import com.zju.drools.R;
import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.model.User;
import com.zju.service.IndexManager;
import com.zju.service.ItemManager;
import com.zju.service.PatientInfoManager;
import com.zju.service.ResultManager;
import com.zju.service.RuleManager;
import com.zju.service.TestDescribeManager;
import com.zju.service.TestResultManager;
import com.zju.service.UserManager;
import com.zju.webapp.util.AnalyticUtil;
import com.zju.webapp.util.DataResponse;
import com.zju.webapp.util.SampleUtil;


/**
 * Class to controll the role_patient view.
 */
@Controller
@RequestMapping("/explain/doctor*")
public class DoctorController {
	private PatientInfoManager patientInfoManager;
	private TestResultManager testResultManager;
	private TestDescribeManager testDescribeManager;
	private UserManager userManager;
	private IndexManager indexManager;
	private ItemManager itemManager;
	private ResultManager resultManager;
	private RuleManager ruleManager;
	private KnowledgeBase kbase=null;
	
	private static HashMap<String,String> idMap=new HashMap<String,String>();
	
	@Autowired
	public void setIndexManager(IndexManager indexManager) {
		this.indexManager = indexManager;
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
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(idMap.size()==0){
			initMap();
		}
		if(kbase==null){
			AnalyticUtil util = new AnalyticUtil(itemManager, resultManager);
			Reader reader = util.getReader(ruleManager.getAll());
			kbase=new DroolsTest().readKnowledgeBase(reader);
		}
		
		ModelAndView view=new ModelAndView();
		HashMap<String,List<PatientInfo>> patientMap=new HashMap<String,List<PatientInfo>>();
		String doctorId=request.getRemoteUser();
		/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR , inputDayOfYear-7);
		Date date7daysago=cal.getTime();
		date7daysago=formatter.parse(formatter.format(date7daysago));*/
		String fromDate="2012-08-01";
		String toDate="2012-08-31";
		if(request.getParameter("fromDate")!=null&&request.getParameter("toDate")!=null){
			fromDate=request.getParameter("fromDate");
			toDate=request.getParameter("toDate");
			request.setAttribute("fromDate", fromDate);
			request.setAttribute("toDate", toDate);
		}
		List<PatientInfo> list=patientInfoManager.getForDoctor(doctorId, fromDate, toDate);
		List<PatientInfo> patientList=new ArrayList<PatientInfo>();
		for(PatientInfo p:list){
			String id=p.getPatientId();
			if(patientMap.containsKey(id)){
				patientMap.get(id).add(p);
			}else{
				patientList.add(p);
				List<PatientInfo> l=new ArrayList<PatientInfo>();
				l.add(p);
				patientMap.put(p.getPatientId(), l);
			}
		}
		view.addObject("patientList", patientList);
		List<PatientInfo> sampleList=new ArrayList<PatientInfo>();
		if(request.getParameter("patientId")!=null){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
			sampleList=patientMap.get(request.getParameter("patientId"));
			view.addObject("sampleList", sampleList);
			
		}
        //return new ModelAndView().addObject(patientInfoManager.getForPatient(patientId, formatter.format(date7daysago), formatter.format(date)));
		return view;
	}
	
	

	/**
	 *  获取某一样本的检验数据
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

		if (StringUtils.isEmpty(sampleNo)) {
			return null;
		}
		if (idMap.size() == 0) initMap();
		List<TestResult> list = testResultManager.getBySampleNo(sampleNo);
		List<Map<String, Object>> dataRows = new ArrayList<Map<String, Object>>();
		
		dataResponse.setRecords(list.size());
		for (int i = 0; i < list.size(); i++) {
			if(idMap.containsKey(list.get(i).getTestId())){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", list.get(i).getTestId());
				map.put("name", idMap.get(list.get(i).getTestId()));
				map.put("result", list.get(i).getResultView());
				map.put("scope", list.get(i).getReference());
				map.put("unit", list.get(i).getUnit());
				dataRows.add(map);
			}
		}
		dataResponse.setRows(dataRows);

		response.setContentType("text/html;charset=UTF-8");
		return dataResponse;
	}
	
	private String getResult(PatientInfo info) {
		try {
			List<TestResult> list = testResultManager.getByPatientId(info.getPatientId());
			HashMap<String,TestResult> test=new HashMap<String,TestResult>();
			for(TestResult t:list){
				if(idMap.containsKey(t.getTestId())){
					test.put(t.getTestId(), t);
				}
			}
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        	ksession.setGlobal("r", new R());
			P p=new P();
			p.setId(info.getPatientId());
			p.setA(info.getAge());
			p.setS(info.getSex());
			ksession.insert(p);
			for(TestResult t:test.values()){
				if(t.getTestId().equals("0017")){
					List<TestResult> checklist = 
							testResultManager.getForDifCheck(info.getPatientId(), t.getTestId(), t.getMeasureTime());
					System.out.println(checklist.size());
					if(checklist.size()>=2){
						float currValue=Float.parseFloat(checklist.get(0).getTestResult());
						float lastValue=Float.parseFloat(checklist.get(1).getTestResult());
						float value=((currValue-lastValue)/currValue)*100/15;
						I i=new I(t.getTestId(),t.getSampleType(),t.getUnit(),"");
						i.setV(value);
						ksession.insert(i);
					}
				}
				I i=new I(t.getTestId(),t.getSampleType(),t.getUnit(),t.getTestResult());
				ksession.insert(i);
			}
			
			ksession.fireAllRules();
			R result=(R) ksession.getGlobal("r");
			ksession.dispose();
			Set<String> explain=result.getResultSet();
			StringBuilder sb=new StringBuilder("");
			if(explain.size()>0){
				for(String s:explain){
					sb.append(s+"<br/>");
				}
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}

	/**
	 *  获取样本中的病人信息
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
		if (StringUtils.isEmpty(id) || !StringUtils.isNumeric(id)) {
			return null;
		}
		
		PatientInfo info = patientInfoManager.get(Long.parseLong(id));
		String doctorId=request.getRemoteUser();
		User user=userManager.getUserByUsername(doctorId);
		String history=user.getHistoryList();
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (info != null) {
			map.put("id", info.getPatientId());
			map.put("name", info.getPatientName());
			map.put("age", String.valueOf(info.getAge()));
			String ex = info.getExaminaim().trim();
			if (ex.length() > 16) {
				ex = ex.substring(0, 16) + "...";
			}
			map.put("examinaim", ex);
			map.put("section", info.getSection());
			String sex = "";
			if (info.getSex() == 1)
				sex = "男";
			else if (info.getSex() == 2)
				sex = "女";
			else
				sex = "未知";
			map.put("sex", sex);
			map.put("type", SampleUtil.getInstance().getSampleList(indexManager).get(String.valueOf(info.getSampleType())));
			
			//智能解释
			String intelExplain=getResult(info);
			map.put("intelExplain", intelExplain);
			/*final PatientInfo _info = info;
			Thread first = new Thread(new Runnable(){

				@Override
				public void run() {
					String str = getResult(_info);
					System.out.println("1:");
					System.out.println(str);
				}
				
			});
			Thread second = new Thread(new Runnable(){

				@Override
				public void run() {
					String str = getResult(_info);
					System.out.println("2:");
					System.out.println(str);
				}
				
			});
			
			first.start();
			second.start();*/
			
			// 获取历史数据
			List<Object> res = getHistory(info,history);
			if (res != null) {
				map.put("history", res);
			}
			else map.put("history", "");
		}
		return map;
	}
	
	private List<Object> getHistory(PatientInfo info, String history) throws Exception {
		class Temp {
			@SuppressWarnings("unused")
			public String name;
			public List<Object> array;
			public List<Object> array1;
			public List<Object> array2;
		}
		if(history==null){
			history="()";
		}
		List<TestResult> testList = testResultManager.getBySampleNo(info.getSampleNo());
		if (testList != null && testList.size() != 0) {
			for (TestResult test : testList) {
				if (!StringUtils.isEmpty(test.getResultFlag()) && test.getResultFlag().charAt(0) != 'A') {
					if(!history.contains(test.getTestId())){
						if(history.equals("()")){
							history=history.replace(")", "'"+test.getTestId()+"')");
						}else{
							history=history.replace(")", ",'"+test.getTestId()+"')");
						}
							
					}
				}
			}
			if (!history.equals("")) {
				if (idMap.size() == 0)
					initMap();
				List<TestResult> result = testResultManager.getHistory(info.getPatientId(), history);
				Map<String, Object> root = new HashMap<String, Object>();
				for (int i = 0; i < result.size(); i++) {
					String testId = result.get(i).getTestId();
					Temp cur = null;
					if (!root.containsKey(testId)) {
						cur = new Temp();
						cur.name = idMap.get(result.get(i).getTestId());
						cur.array = new ArrayList<Object>();
						cur.array1 = new ArrayList<Object>();
						cur.array2 = new ArrayList<Object>();
						root.put(testId, cur);
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
					} catch(Exception e) {
						continue;
					}
					view.add(element);
					view1.add(element1);
					view2.add(element2);
				}
				List<Object> lastResult = new ArrayList<Object>();
				for(String key : root.keySet()) {
					List<Object> array = ((Temp)root.get(key)).array;
					if (array.size() > 1) {
						lastResult.add(root.get(key));
					}
				}
				if (lastResult.size() == 0) return null;
				else return lastResult;
			}
		}
		return null;
	}
	
	private void initMap() {
		List<TestDescribe> list=testDescribeManager.getAll();
		for(TestDescribe t:list){
			idMap.put(t.getTestId(), t.getChineseName());
		}
	}
}
