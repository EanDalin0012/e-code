package com.ecode.admin.service;

import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;
import org.springframework.stereotype.Repository;

@Repository
public interface TestingDecryptionService {
    int save(MMap param);
    MultiMap retrieve();
}
