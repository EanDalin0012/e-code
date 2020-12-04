package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.map.MMap;

@Mapper
public interface ResourceFileInfoDao {
	long getLastId();
    int fileUpload(MMap param);
    MMap getResourceById(MMap param);
    int deleteById(MMap param);
}
