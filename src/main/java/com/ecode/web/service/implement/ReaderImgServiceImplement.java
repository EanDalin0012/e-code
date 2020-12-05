package com.ecode.web.service.implement;

import com.ecode.core.map.MMap;
import com.ecode.web.dao.ReaderImgDao;
import com.ecode.web.service.ReaderImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderImgServiceImplement implements ReaderImgService {

    @Autowired
    private ReaderImgDao readerImgDao;
    @Override
    public MMap getResourcesImageById(MMap param) {
        return readerImgDao.getResourcesImageById(param);
    }
}
