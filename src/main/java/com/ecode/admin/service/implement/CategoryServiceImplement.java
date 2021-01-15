package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.CategoryDao;
import com.ecode.admin.service.CategoryService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class CategoryServiceImplement implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public MultiMap retrieveList(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "status");
        return categoryDao.retrieveList(param);
    }

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "name", "status", "user_id");
        return categoryDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "status", "user_id");
        return categoryDao.delete(param);
    }

    @Override
    public MMap retrieveCategoryById(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "status");
        return categoryDao.retrieveCategoryById(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "status", "name", "user_id");
        return categoryDao.update(param);
    }

    @Override
    public int count() {
        return categoryDao.count();
    }

    @Override
    public int sequence() {
        return categoryDao.sequence();
    }
}
