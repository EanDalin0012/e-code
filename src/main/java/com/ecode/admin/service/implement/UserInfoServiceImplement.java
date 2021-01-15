package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.UserInfoDao;
import com.ecode.admin.service.UserInfoService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class UserInfoServiceImplement implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public MultiMap retrieveList(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "status");
        return userInfoDao.retrieveList(param);
    }

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "first_name", "last_name", "gender", "date_birth", "email", "contact", "status", "user_id");
        return userInfoDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "status", "id");
        return userInfoDao.delete(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "first_name", "last_name", "gender", "date_birth", "email", "contact", "status", "user_id");
        return userInfoDao.update(param);
    }

    @Override
    public int count() {
        return userInfoDao.count();
    }
}
