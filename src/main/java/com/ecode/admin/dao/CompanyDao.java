package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Mapper
public interface CompanyDao {
	MultiMap getList(MMap param);
    int save(MMap param);
    int delete(MMap param);
    MMap getValueById(MMap param);
    int update(MMap param);
    int count();
}
