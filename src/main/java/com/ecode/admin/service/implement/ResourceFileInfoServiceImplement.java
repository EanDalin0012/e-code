package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.ResourceFileInfoDao;
import com.ecode.admin.service.ResourceFileInfoService;
import com.ecode.core.map.MMap;


@Service
public class ResourceFileInfoServiceImplement implements ResourceFileInfoService {

    @Autowired
    private ResourceFileInfoDao resourceFileInfoDao;

    @Override
    public long getLastId() {
        return resourceFileInfoDao.getLastId();
    }

    @Override
    public int fileUpload(MMap param) {
        return resourceFileInfoDao.fileUpload(param);
    }

    @Override
    public MMap getResourceById(MMap param) {
        return resourceFileInfoDao.getResourceById(param);
    }

    @Override
    public int deleteById(MMap param) {
        return resourceFileInfoDao.deleteById(param);
    }
}
