package com.ecode.admin.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

public interface ProductDescriptionService {
    MultiMap retrieveList(MMap param);

    int save(MMap param);

    int delete(MMap param);

    int update(MMap param);

    MMap retrieveProductDescriptionByProductID(MMap param) throws ValidatorException;
}
