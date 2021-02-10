package com.ecode.admin.service.implement;

import com.ecode.admin.dao.TestingDecryptionDao;
import com.ecode.admin.service.TestingDecryptionService;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestingDecryptionServiceImplement implements TestingDecryptionService {
    @Autowired
    private TestingDecryptionDao testingDecryptionDao;
    @Override
    public int save(MMap param) {
        return testingDecryptionDao.save(param);
    }

    @Override
    public MultiMap retrieve() {
        return testingDecryptionDao.retrieve();
    }
}
