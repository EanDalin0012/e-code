package com.ecode.admin.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;

public interface ResourceImageService {
    int save(MMap param) throws ValidatorException;

    int delete(MMap param) throws ValidatorException;

    int update(MMap param) throws ValidatorException;

    String getResourcesImageById(MMap param) throws ValidatorException;
}
