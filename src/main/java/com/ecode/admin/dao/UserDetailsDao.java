package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Mapper
public interface UserDetailsDao {
	int save(MMap param);
    MultiMap dataDetails(MMap param);
}
