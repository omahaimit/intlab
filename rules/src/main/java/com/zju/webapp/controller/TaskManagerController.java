package com.zju.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.drools.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zju.model.Task;
import com.zju.webapp.controller.explain.AuditController;
import com.zju.webapp.util.TaskManagerUtil;

@Controller
@RequestMapping("/task*")
public class TaskManagerController {
	
	private static Log log = LogFactory.getLog(TaskManagerController.class);

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
	
		TaskManagerUtil manager = TaskManagerUtil.getInstance();
		request.setAttribute("taskList", manager.getTaskList());
		request.setAttribute("backAuditInterval", AuditController.backAuditInterval);
		request.setAttribute("currentStatus", AuditController.startBackAudit);
		return new ModelAndView("admin/task");
	}
	
	@RequestMapping(value = "/ajax/cancel*", method = RequestMethod.GET)
	public void stopThread(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		if (!StringUtils.isEmpty(id)) {
			TaskManagerUtil.getInstance().stopThread(Long.parseLong(id));
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/audit*", method = RequestMethod.POST)
	public boolean audit(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		boolean result = true;
		String intl = request.getParameter("interval");
		AuditController.backAuditInterval = Integer.parseInt(intl);
		//AuditController.startBackAudit = ! AuditController.startBackAudit;
		
		if (!AuditController.startBackAudit) {
			AuditController.startBackAudit = true;
			startAudit();
		} else {
			AuditController.startBackAudit = false;
		}
		return result;
	}
	
	private void startAudit() {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (AuditController.startBackAudit) {
					Date today = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String dateStr = sdf.format(today);
					long startTime = System.currentTimeMillis();
					try {
						auditController.startBackAudit(dateStr);
					} catch (Exception e) {
						log.error("自动审核异常", e);
					}
					long endTime = System.currentTimeMillis();
					
					long interval = AuditController.backAuditInterval * 60 * 1000;
					long cost = endTime - startTime;
					if (interval > cost) {
						try {
							Thread.sleep(interval - cost);
						} catch (InterruptedException e) {}
					}
				}
				
			}
		});
		
		thread.start();
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/progress*", method = RequestMethod.GET)
	public void getProgress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TaskManagerUtil manager = TaskManagerUtil.getInstance();
		JSONArray array = new JSONArray();
		for (Task t : manager.getTaskList()) {
			JSONObject obj = new JSONObject();
			obj.put("id", t.getId());
			obj.put("status", t.getStatus());
			obj.put("value", (int)(t.getProValue() * 100));
			obj.put("finish", t.getFinishCount());
			obj.put("total", t.getSampleCount());
			obj.put("result", t.getHasResultNum());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (t.getEndTime() == null) {
				obj.put("end", "");
			} else {
				obj.put("end", df.format(t.getEndTime()));
			}
			array.put(obj);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajax/audit*", method = RequestMethod.GET)
	public void getAuditProgress(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TaskManagerUtil manager = TaskManagerUtil.getInstance();
		JSONArray array = new JSONArray();
		for (Task t : manager.getTaskList()) {
			if (request.getRemoteUser().equals(t.getStartBy())) {
				JSONObject obj = new JSONObject();
				obj.put("id", t.getId());
				obj.put("status", t.getStatus());
				obj.put("value", (int)(t.getProValue() * 100));
				obj.put("text", t.getSearchText());
				obj.put("ratio", t.getFinishCount() + "/" + t.getSampleCount());
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				obj.put("start", df.format(t.getStartTime()));
				if (t.getEndTime() == null) {
					obj.put("end", "");
				} else {
					obj.put("end", df.format(t.getEndTime()));
				}
				array.put(obj);
			}
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(array.toString());
	}
	
	private AuditController auditController = null;

	@Autowired
	public void setAuditController(AuditController auditController) {
		this.auditController = auditController;
	}
}
