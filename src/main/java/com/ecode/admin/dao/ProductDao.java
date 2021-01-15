package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Mapper
public interface ProductDao {
    MultiMap retrieveList(MMap param);

    int save(MMap param);

    int delete(MMap param);

    int update(MMap param);

    int updateShowOnWeb(MMap param);

    int updateShowOnMobile(MMap param);

    int count();

    int sequence();
}
