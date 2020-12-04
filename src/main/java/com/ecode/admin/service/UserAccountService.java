package com.ecode.admin.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

public interface UserAccountService {
	int updateUserAccount(MMap param) throws ValidatorException;
    MMap retrieveUserAccountByID(MMap param) throws ValidatorException;
    MultiMap getList(MMap param);
    int count();
}
