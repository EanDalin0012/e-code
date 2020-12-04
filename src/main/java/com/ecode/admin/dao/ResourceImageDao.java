package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.map.MMap;

@Mapper
public interface ResourceImageDao {
	int save(MMap param);
	int delete(MMap param);
	int update(MMap param);
	String getResourcesImageById(MMap param);
}
