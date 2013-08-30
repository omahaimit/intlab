package com.zju.drools;

import java.util.HashSet;
import java.util.Set;

public class R {
	private Set<String> resultSet = new HashSet<String>();
	private Set<String> ruleIdSet = new HashSet<String>();

	public Set<String> getResultSet() {
		return resultSet;
	}

	public void setResultSet(String result) {
		this.resultSet.add(result);
	}

	public void remove(String result) {
		this.resultSet.remove(result);
	}

	public Set<String> getRuleIds() {
		return ruleIdSet;
	}

	public void addRuleId(String ruleId) {
		this.ruleIdSet.add(ruleId);
	}
}
