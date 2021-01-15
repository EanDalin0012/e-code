package com.ecode.core.service;

import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;

public interface DefaultAuthenticationProviderService {
    MMap getUserByName(MMap param) throws Exception;
}
