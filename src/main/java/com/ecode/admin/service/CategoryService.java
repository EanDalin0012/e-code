package com.ecode.admin.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

import java.io.IOException;

public interface CategoryService {
    MultiMap retrieveList(MMap param) throws ValidatorException;

    int save(MMap param) throws ValidatorException, IOException;

    int delete(MMap param) throws ValidatorException;

    MMap retrieveCategoryById(MMap param) throws ValidatorException;

    int update(MMap param) throws ValidatorException;

    int count();

    int sequence();
}
