package com.zju.webapp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.zju.model.Task;

public class TaskManagerUtil {

	private static final long MSD = 1000 * 600; 	//1000 * 3600 * 24;  //使用10分钟
	private static TaskManagerUtil manager = new TaskManagerUtil();
	private Date lastExecTime = new Date();
	private Map<Long, Task> tasks = new HashMap<Long, Task>();
	private Set<String> operatorOnAuditing = new HashSet<String>();  //正在自动审核的检验员
	private Set<String> codeOnAuditing = new HashSet<String>();
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 10, 3, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());

	private TaskManagerUtil() {
	}

	public static TaskManagerUtil getInstance() {
		return manager;
	}

	public void addTask(Task task) {
		tasks.put(task.getId(), task);
	}

	public void removeTask(Task task) {
		tasks.remove(task);
	}
	
	private boolean codeIsAuditing(String code) {
		if (codeOnAuditing.contains(code)) {
			return true;
		}
		return false;
	}
	
	private void addCode(String code) {
		codeOnAuditing.add(code);
	}
	
	private void removeCode(String code) {
		if (codeOnAuditing.contains(code)) {
			codeOnAuditing.remove(code);
		}
	}
	
	public String getNeedAuditCode(String preCode) {

		synchronized (this) {
			if (StringUtils.isEmpty(preCode)) {
				return "";
			} else {
				String result = "";
				String[] codes = preCode.split(",");
				for (String code : codes) {
					if (!codeIsAuditing(code)) {
						manager.addCode(code);
						result += code + ",";
					}
				}
				if (result.length() != 0) {
					result = result.substring(0, result.length() - 1);
				}
				return result;
			}
		}
	}
	
	public void removeCodes(String codes) {
		synchronized (this) {
			if (!StringUtils.isEmpty(codes)) {
				String[] cds = codes.split(",");
				for (String code : cds) {
					removeCode(code);
				}
			}
		}
	}
	
	public boolean isAuditing(String name) {
		if (operatorOnAuditing.contains(name)) {
			return true;
		}
		return false;
	}
	
	public void addOperatorName(String name) {
		operatorOnAuditing.add(name);
	}
	
	public void removeOperatorName(String name) {
		if (operatorOnAuditing.contains(name)) {
			operatorOnAuditing.remove(name);
		}
	}

	public List<Task> getTaskList() {
		List<Task> list = new ArrayList<Task>(tasks.values());
		Collections.sort(list, new Comparator<Task>() {

			@Override
			public int compare(Task o1, Task o2) {
				return o1.getStartTime().compareTo(o2.getStartTime());
			}
		});
		return list;
	}
	
	public long getLastFinishTime(String name) {
		
		long time = 0;
		if (name == null)
			throw new NullPointerException();
		
		for(Task t : tasks.values()) {
			if (name.equals(t.getStartBy())) {
				if (t.getEndTime() != null) {
					if (t.getEndTime().getTime() > time)
						time = t.getEndTime().getTime();
				}
			}
		}
		
		return time;
	}
	
	public void execute(Runnable thread) {
		Date now = new Date();
		long nowD = now.getTime() / MSD;
		long lastD = lastExecTime.getTime() / MSD;
		if (nowD != lastD) {
			// 移除昨天的工作
			Iterator<Map.Entry<Long, Task>> it = tasks.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Long, Task> entry = it.next();
				long key = entry.getKey();
				if ((tasks.get(key).getStartTime().getTime() / MSD) != nowD) {
					if (tasks.get(key).getStatus() > 1) {
						it.remove();
					}
				}
			}
		}
		lastExecTime = now;
		threadPool.execute(thread);
	}
	
	public void stopThread(Long id) {
		if (tasks.containsKey(id)) {
			if (tasks.get(id).getStatus() <= 1) {
				tasks.get(id).setStatus(3);
			}
		}
	}
}
