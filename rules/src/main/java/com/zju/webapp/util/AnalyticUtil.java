package com.zju.webapp.util;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.zju.model.Item;
import com.zju.model.Library;
import com.zju.model.Result;
import com.zju.model.Rule;
import com.zju.service.ItemManager;
import com.zju.service.ResultManager;

public class AnalyticUtil {

	private ItemManager itemManager = null;
	private ResultManager resultManager = null;
	private Set<String> reasonSet = null;

	public AnalyticUtil(ItemManager i, ResultManager r) {
		itemManager = i;
		resultManager = r;
	}

	public Reader getReader(List<Rule> rules) {
		reasonSet = new HashSet<String>();
		for (Rule rule : rules) {
			// 防止session被关闭后,无法延迟加载
			rule.getResults().size();
			if (!StringUtils.isEmpty(rule.getReReasoning())) {
				String[] reasons = rule.getReReasoning().split(",");
				for(String reason : reasons) {
					if (!reasonSet.contains(reason)) {
						reasonSet.add(reason);
					}
				}
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("import com.zju.drools.I;\n");
		builder.append("import com.zju.drools.SI;\n");
		builder.append("import com.zju.drools.P;\n");
		builder.append("import com.zju.drools.R;\n");
		builder.append("\nglobal R r;\n");
		try {
			for (Rule r : rules) {
				try {
					String str = getRuleStream(r);
					builder.append("\n");
					builder.append(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringReader(builder.toString());
	}

	public String getRuleStream(Rule rule) throws Exception {

		StringBuilder builder = new StringBuilder();
		Set<String> removeSet = new HashSet<String>();

		builder.append("rule\"" + rule.getId() + "\"\nwhen\n\t");
		// 知识点解析
		if (!StringUtils.isEmpty(rule.getRelation())) {
			JSONObject root = new JSONObject(rule.getRelation());
			getItem(root, removeSet, builder);
		}
		// 下面为结果的解析部分
		builder.append("\nthen\n");
		for (String remove : removeSet) {
			builder.append("\tr.remove(\"");
			builder.append(remove);
			builder.append("\");\n");
		}

		for (Result result : rule.getResults()) {
			builder.append("\tr.setResultSet(\"");
			builder.append(result.getContent());
			builder.append("\");\n");
		}

		if (rule.getType() != 1 && rule.getType() != 2) {
			builder.append("\tr.addRuleId(\"");
			builder.append(rule.getId().toString());
			builder.append("\");\n");
		}

		for (Result r : rule.getResults()) {
			if (reasonSet.contains(r.getId().toString())) {
				builder.append("\tinsert(new SI(\"");
				builder.append((long) (90000 + r.getId()));
				builder.append("\",\"");
				builder.append(r.getContent());
				builder.append("\"));\n");
			}
		}

		builder.append("end\n");

		return builder.toString();
	}

	private void getItem(JSONObject root, Set<String> removeSet, StringBuilder sb) throws Exception {
		
		if ("and".equals(root.get("id"))) {
			JSONArray array = root.getJSONArray("children");
			sb.append("(");
			for (int i = 0; i < array.length(); i++) {
				getItem(array.getJSONObject(i), removeSet, sb);
				if (i != array.length() - 1) {
					sb.append(" and ");
				}
			}
			sb.append(")");
		} else if ("or".equals(root.get("id"))) {
			JSONArray array = root.getJSONArray("children");
			sb.append("(");
			for (int i = 0; i < array.length(); i++) {
				getItem(array.getJSONObject(i), removeSet, sb);
				if (i != array.length() - 1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
		} else if ("not".equals(root.get("id"))) {
			JSONArray array = root.getJSONArray("children");
			sb.append("not(");
			for (int i = 0; i < array.length(); i++) {
				getItem(array.getJSONObject(i), removeSet, sb);
			}
			sb.append(")");
		} else {
			sb.append(getItemStr(root.get("id").toString(), removeSet));
		}
	}

	private String getItemStr(String id, Set<String> removeSet) {

		StringBuilder builder = new StringBuilder();

		Long ID = Long.parseLong(id.substring(1));
		if (id.startsWith("I")) {
			Item item = itemManager.getWithIndex(ID);
			String value = item.getValue();
			String unit = item.getUnit();
			char c = value.charAt(0);
			if (c == '<' || c == '>' || c == '=') {
				builder.append("I(");
				builder.append("c==").append(Integer.parseInt(item.getIndex().getIndexId()));
				builder.append(",v");
				if (c == '=')
					builder.append("=");
				builder.append(value);
				builder.append(",s=='");
				builder.append(item.getIndex().getSampleFrom());
				builder.append("'");
				if (!StringUtils.isEmpty(unit)) {
					builder.append(",u=='");
					builder.append(unit);
					builder.append("'");
				}
			} else {
				builder.append("SI(");
				builder.append("c==").append(Integer.parseInt(item.getIndex().getIndexId()));
				builder.append(",sv=='");
				builder.append(value);
				builder.append("',s=='");
				builder.append(item.getIndex().getSampleFrom());
				builder.append("'");
			}
			builder.append(")");

		} else if (id.startsWith("P")) {
			Library p = PatientUtil.getInstance().getInfo(ID, itemManager);
			if (p != null) {
				String info = p.getSign();
				builder.append("P(");
				if (info.indexOf(',') != -1) {
					String[] two = info.split(",");
					builder.append("s=='");
					builder.append(two[0]);
					builder.append("',l=='");
					builder.append(two[1]);
				} else {
					if ("M".equals(info) || "F".equals(info)) {
						builder.append("s=='");
						builder.append(info);
					} else {
						builder.append("l=='");
						builder.append(info);
					}
				}
				builder.append("')");
			}
		} else if (id.startsWith("R")) {
			builder.append("SI(c==");
			Result r = resultManager.get(ID);
			builder.append(90000 + r.getId());
			builder.append(",sv=='");
			builder.append(r.getContent());
			removeSet.add(r.getContent());
			builder.append("')");
		} else {

		}
		return builder.toString();
	}
}
