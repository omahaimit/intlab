package com.zju.dao;

import java.util.List;

import com.zju.model.Record;

public interface RecordMapper {

	List<Record> getRecordList(String date);
	
	int getRecordSize(String date);
	
	int getPatientSize(String date);
	
	void insert(Record record);
}
