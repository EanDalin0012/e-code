package com.ecode.admin.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

public interface UserService {
	MultiMap getList(MMap param) throws  ValidatorException;
    int save(MMap param) throws  ValidatorException;
    int delete(MMap param) throws  ValidatorException;
    MMap loadUserByUserName(MMap param) throws ValidatorException;
    int update(MMap param) throws  ValidatorException;
    int count();
    int sequence();
}
