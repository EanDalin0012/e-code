package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.CompanyDao;
import com.ecode.admin.service.CompanyService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class CompanyServiceImplement implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public MultiMap getList(MMap param) {
        return companyDao.getList(param);
    }

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "name", "contact", "email");
        return companyDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id");
        return companyDao.delete(param);
    }

    @Override
    public MMap getValueById(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id");
        return companyDao.getValueById(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "name", "contact", "email");
        return companyDao.update(param);
    }

    @Override
    public int count() {
        return companyDao.count();
    }
}
