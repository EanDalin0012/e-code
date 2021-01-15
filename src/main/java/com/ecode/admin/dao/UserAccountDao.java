package com.ecode.admin.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Mapper
public interface UserAccountDao {
    int updateUserAccount(MMap param);

    MultiMap getList(MMap param);

    MultiMap retrieveList(MMap param);

    MMap retrieveUserAccountByID(MMap param);

    int save(MMap param);

    int delete(MMap param);

    int update(MMap param);

    int count();
}
