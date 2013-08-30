package com.zju.drools;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;

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
import com.zju.model.TestResult;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

	public KnowledgeBase readKnowledgeBase(Reader reader) throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newReaderResource(reader), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
	
	public void getResult(List<TestResult> list, Reader reader, PatientInfo patient, HashMap<String, String> idMap) {
		try {
			KnowledgeBase kbase = readKnowledgeBase(reader);
			kbase.toString();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			ksession.setGlobal("r", new R());
			P p = new P();
			p.setId(patient.getPatientId());
			p.setA(patient.getAge());
			p.setS(patient.getSex());
			ksession.insert(p);
			for (TestResult t : list) {
				if (idMap.containsKey(t.getTestId())) {
					I i = new I();
					i.setC(Integer.parseInt(t.getTestId()));
					i.setS(t.getSampleType());
					i.setU(t.getUnit());
					i.setV(Float.parseFloat(t.getTestResult()));
					ksession.insert(i);
				}
			}

			ksession.fireAllRules();
			R result = (R) ksession.getGlobal("r");
			System.out.println(result.getResultSet().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
