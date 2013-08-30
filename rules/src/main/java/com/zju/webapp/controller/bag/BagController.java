package com.zju.webapp.controller.bag;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

import com.zju.model.Bag;
import com.zju.service.BagManager;

@Controller
@RequestMapping("/bag/ajax")
public class BagController {

	private BagManager bagManager = null;
	private String bagJson = null;
	private AtomicBoolean isChanged = new AtomicBoolean(true);

	@Autowired
	public void setBagManager(BagManager bagManager) {
		this.bagManager = bagManager;
	}

	@RequestMapping(value = "/getBag", method = { RequestMethod.GET })
	@ResponseBody
	public String getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (isChanged.get()) {
			List<Bag> bags = bagManager.getBag();
			JSONArray cell = new JSONArray();
			for (int i = 0; i < bags.size(); i++) {
				JSONObject sb = new JSONObject();
				sb.put("id", bags.get(i).getId());
				sb.put("pId", bags.get(i).getParenetID());
				sb.put("name", bags.get(i).getName());
				cell.put(sb);
			}
			bagJson = cell.toString();
			if (bags.size() != 0)
				isChanged.set(false);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(bagJson);
		return null;
	}

	@RequestMapping(value = "/searchBag", method = { RequestMethod.GET })
	@ResponseBody
	public String search(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String name = request.getParameter("name");
		if (StringUtils.isEmpty(name)) {
			return null;
		}

		List<Bag> bags = bagManager.getBagByName(name);
		JSONArray array = new JSONArray();

		if (bags != null) {
			for (Bag b : bags) {
				JSONObject o = new JSONObject();
				o.put("id", b.getId());
				o.put("name", b.getName());
				array.put(o);
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(array.toString());
		return null;
	}

	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public void updateData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		Long id = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		Bag bag = new Bag();
		if (action.equals("add")) {
			bag.setParenetID(id);
			bag.setName(name);
			bagManager.save(bag);
		} else if (action.equals("rename")) {
			bag = bagManager.get(id);
			bag.setName(name);
			bagManager.save(bag);
		} else if (action.equals("remove")) {
			List<Bag> bags = bagManager.getBag(id);
			if (bags.size() > 0) {
				for (Bag b : bags) {
					bagManager.remove(b.getId());
				}
			}
			bagManager.remove(id);
		} else if (action.equals("draginner")) {
			Long id2 = Long.parseLong(name);
			bag = bagManager.get(id);
			bag.setParenetID(id2);
			bagManager.save(bag);
		} else {
			Long id2 = Long.parseLong(name);
			Bag bag2 = bagManager.get(id2);
			bag = bagManager.get(id);
			bag.setParenetID(bag2.getParenetID());
			bagManager.save(bag);
		}
		isChanged.set(true);
	}
}
