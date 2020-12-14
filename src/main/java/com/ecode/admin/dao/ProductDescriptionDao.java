package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Mapper
public interface ProductDescriptionDao {
	MultiMap retrieveList(MMap param);
	MultiMap retrieveByID(MMap param);
    int save(MMap param);
    int delete(MMap param);
    int update(MMap param);
}
