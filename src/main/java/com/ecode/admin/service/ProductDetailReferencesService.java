package com.ecode.admin.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;

public interface ProductDetailReferencesService {
    int save(MMap param) throws ValidatorException;

    int delete(MMap param) throws ValidatorException;
}
