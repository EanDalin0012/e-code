package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;

@Mapper
public interface ProductDetailReferencesDao {
	int save(MMap param) ;
	int delete(MMap param) ;
}
