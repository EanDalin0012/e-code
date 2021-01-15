package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.ResourceImageDao;
import com.ecode.admin.service.ResourceImageService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;

@Service
public class ResourceImageServiceImplement implements ResourceImageService {

    @Autowired
    private ResourceImageDao resourceImageDao;

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "original_name", "file_size", "file_type", "file_extension", "file_source");
        return resourceImageDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id");
        return resourceImageDao.delete(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id");
        return resourceImageDao.update(param);
    }

    @Override
    public String getResourcesImageById(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "uuid");
        return resourceImageDao.getResourcesImageById(param);
    }
}
