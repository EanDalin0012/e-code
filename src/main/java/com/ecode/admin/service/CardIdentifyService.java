package com.ecode.admin.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

public interface CardIdentifyService {
    MultiMap retrieveList(MMap param) throws ValidatorException;

    MMap retrieveCardIdentifyById(MMap param) throws ValidatorException;

    int save(MMap param) throws ValidatorException;

    int delete(MMap param) throws ValidatorException;

    int update(MMap param) throws ValidatorException;

    int count();
}
