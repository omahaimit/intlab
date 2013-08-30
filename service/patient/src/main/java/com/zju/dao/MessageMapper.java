package com.zju.dao;

import java.util.List;

import com.zju.model.Message;

public interface MessageMapper {

	List<Message> getMesssageList(String date);
	
	int getMesssageSize(String date);
	
	int getMsgRespondSize(String date);
	
	void batchInsert(List<Message> messages);
	
	void insert(Message message);
	
	void updateStatus(String sampleNo);
}
