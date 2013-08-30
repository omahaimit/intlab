package com.zju.drools;

import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import com.zju.model.PatientInfo;
import com.zju.model.TestDescribe;
import com.zju.model.TestResult;
import com.zju.webapp.util.IndexMapUtil;

public class DroolsRunner {

	private KnowledgeBase kbase = null;
	private Map<String, TestDescribe> idMap = null;
	private static IndexMapUtil util = IndexMapUtil.getInstance(); //检验项映射

	private static DroolsRunner runner = new DroolsRunner();

	private DroolsRunner() {
	}

	public static DroolsRunner getInstance() {
		return runner;
	}
	
	public boolean isBaseInited() {
		if (kbase == null)
			return false;
		else 
			return true;
	}

	public void buildKnowledgeBase(Map<String, TestDescribe> map, Reader reader) {

		try {
			if (kbase == null) {
				synchronized (this) {
					if (kbase == null) {
						if (this.idMap == null)
							this.idMap = map;
						KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
						kbuilder.add(ResourceFactory.newReaderResource(reader), ResourceType.DRL);
						KnowledgeBuilderErrors errors = kbuilder.getErrors();
						if (errors.size() > 0) {
							for (KnowledgeBuilderError error : errors) {
								System.err.println(error);
							}
							throw new IllegalArgumentException("Could not parse knowledge.");
						}
						kbase = KnowledgeBaseFactory.newKnowledgeBase();
						kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
						System.out.println("kbase构造完成!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Set<String> getDiffCheckResult(Set<TestResult> result, Set<TestResult> l_result, Map<String, Diff> diff) {
		Set<String> diffRes = new HashSet<String>();
		Map<String, TestResult> map = new HashMap<String, TestResult>();
		for (TestResult t : result) {
			map.put(t.getTestId(), t);
		}
		for (TestResult lt : l_result) {
			TestResult nt = map.get(lt.getTestId());
			if(nt != null && nt.getTestResult() != null && lt.getTestResult() != null) {
				if(nt.getTestId().equals("9255")||nt.getTestId().equals("9256")
						||nt.getTestId().equals("9068")){
					if(!lt.getTestResult().equals(nt.getTestResult())){
						diffRes.add(lt.getTestId());
					}
				}
				
				if (Pattern.matches("[0-9]*(\\.?)[0-9]*", nt.getTestResult())
						&& Pattern.matches("[0-9]*(\\.?)[0-9]*", lt.getTestResult())) {
					if (diff.containsKey(lt.getTestId())) {
						int algorithm = diff.get(lt.getTestId()).getAlgorithm();
						float reflo = diff.get(lt.getTestId()).getReflo();
						float refhi = diff.get(lt.getTestId()).getRefhi();
						
						float n_value = Float.parseFloat(nt.getTestResult());
						float l_value = Float.parseFloat(lt.getTestResult());
						float value = 0;
						float interval = ((float)((nt.getMeasureTime().getTime() - lt.getMeasureTime().getTime())) / 1000 / 60 / 60 / 24); // 间隔时间，暂设为7天
						float fenmu = n_value;
						if (l_value < fenmu) {
							fenmu = l_value;
						}
						if (n_value != 0) {
							switch (algorithm) {
							case 1: {
								value = n_value - l_value;
								break;
							}
							case 2: {
								value = (n_value - l_value) / fenmu * 100;
								break;
							}
							case 3: {
								value = (n_value - l_value) / interval;
								break;
							}
							case 4: {
								value = (n_value - l_value) / n_value * 100 / interval;
								break;
							}
							}
							if (value > refhi || value < reflo) {
								diffRes.add(lt.getTestId());
							}
						}
					}
				}
			}
		}
		return diffRes;
	}

	public Set<String> getRatioCheckResult(Set<TestResult> results, Map<String, Ratio> ratio) {
		Set<String> ratioRes = new HashSet<String>();
		Map<String, TestResult> map = new HashMap<String, TestResult>();
		for (TestResult t : results) {
			map.put(t.getTestId(), t);
		}
		for (TestResult t : results) {
			if (ratio.containsKey(t.getTestId())) {
				Ratio ra = ratio.get(t.getTestId());
				TestResult denominator = t;
				TestResult numerator = map.get(ra.getNumeratorId());
				if (denominator.getTestResult() != null && numerator != null && numerator.getTestResult() != null && Pattern.matches("[0-9]*(\\.?)[0-9]*", denominator.getTestResult())
						&& Pattern.matches("[0-9]*(\\.?)[0-9]*", numerator.getTestResult())) {
					float value = Float.parseFloat(numerator.getTestResult())
							/ Float.parseFloat(denominator.getTestResult());
					if (ra.getReflo() == ra.getRefhi()) {
						if (value / ra.getRefhi() < 0.9 || value / ra.getRefhi() > 1.1) {
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						}
					} else {
						if(ra.getReflo()==0 && value > ra.getRefhi()){
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						} else if (ra.getRefhi()==0 && value < ra.getReflo()){
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						} else if (value > ra.getRefhi() || value < ra.getReflo()) {
							ratioRes.add(denominator.getTestId());
							ratioRes.add(numerator.getTestId());
						}
					}
				}
			}
		}
		return ratioRes;
	}

	public R getResult(Set<TestResult> set, PatientInfo patient) {
		StatefulKnowledgeSession ksession = null;
		R result = null;
		try {
			if (kbase != null && idMap != null) {
				ksession = kbase.newStatefulKnowledgeSession();
				ksession.setGlobal("r", new R());
				P p = new P();
				p.setId(patient.getPatientId());
				p.setA(patient.getAge());
				p.setS(patient.getSex());
				ksession.insert(p);
				for (TestResult t : set) {
					if (idMap.containsKey(t.getTestId())) {
						String testId = util.getValue(t.getTestId());
						I i = new I(testId, t.getSampleType(), t.getUnit(), t.getTestResult());
						ksession.insert(i);
					}
				}
				ksession.fireAllRules();
				result = (R) ksession.getGlobal("r");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ksession != null) {
				ksession.dispose();
			}
		}
		return result;
	}
	
	public R getResult(List<TestResult> list, PatientInfo info) {
		HashMap<String,TestResult> test=new HashMap<String,TestResult>();
		for(TestResult t:list){
			if(idMap.containsKey(t.getTestId())){
				test.put(t.getTestId(), t);
			}
		}
		StatefulKnowledgeSession ksession = null;
		R result = null;
		try {
			if (kbase != null && idMap != null) {
				ksession = kbase.newStatefulKnowledgeSession();
				ksession.setGlobal("r", new R());
				P p = new P();
				p.setId(info.getPatientId());
				p.setA(info.getAge());
				p.setS(info.getSex());
				ksession.insert(p);
				for (TestResult t : test.values()) {
					if (idMap.containsKey(t.getTestId())) {
						String testId = util.getValue(t.getTestId());
						I i = new I(testId, t.getSampleType(), t.getUnit(), t.getTestResult());
						ksession.insert(i);
					}
				}
				ksession.fireAllRules();
				result = (R) ksession.getGlobal("r");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ksession != null) {
				ksession.dispose();
			}
		}
		return result;
	}
}
