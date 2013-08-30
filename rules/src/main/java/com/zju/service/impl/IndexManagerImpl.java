package com.zju.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.zju.dao.IndexDao;
import com.zju.model.Index;
import com.zju.model.Library;
import com.zju.service.IndexManager;
import com.zju.service.IndexService;

public class IndexManagerImpl extends GenericManagerImpl<Index, Long> implements IndexManager, IndexService {

	private IndexDao indexDao;
	
	@Autowired
	public void setIndexDao(IndexDao indexDao) {
		this.dao = indexDao;
		this.indexDao = indexDao;
	}

	public List<Index> getIndexs(String indexName) {
		return indexDao.getIndexs(indexName);
	}

	public List<Index> getIndexs(int pageNum, String field, boolean isAsc) {
		return indexDao.getIndexs(pageNum, field, isAsc);
	}

	public List<Index> getIndexs(String sample, int pageNum, String field, boolean isAsc) {
		return indexDao.getIndexsByCategory(sample, pageNum, field, isAsc);
	}

	public Index addIndex(Index index) {
		return indexDao.save(index);
	}

	public Index updateIndex(Index index) {
		return indexDao.save(index);
	}

	public int getIndexsCount() {
		return indexDao.getIndexsCount();
	}

	public int getIndexsCount(String sample) {
		return indexDao.getIndexsCount(sample);
	}

	@Override
	public List<Library> getSampleList() {
		return indexDao.getSampleList();
	}

	@Override
	public Index getIndex(String indexId) {
		return indexDao.getIndex(indexId);
	}

	@Override
	public List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc) {
		return indexDao.getIndexsByName(name, pageNum, field, isAsc);
	}

	@Override
	public int getIndexsByNameCount(String name) {
		
		return indexDao.getIndexsByNameCount(name);
	}

	@Override
	public List<Library> getLibrary(int type) {
		return indexDao.getLibrary(type);
	}
}
