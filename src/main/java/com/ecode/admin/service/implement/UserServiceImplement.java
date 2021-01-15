package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.UserDao;
import com.ecode.admin.service.UserService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public MultiMap getList(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "status");
        return userDao.getList(param);
    }

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "user_name", "passwd", "is_first_login", "enable", "account_lock", "credential_expired", "account_expired", "status", "user_id");
        return userDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id");
        return userDao.delete(param);
    }


    @Override
    public MMap loadUserByUserName(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "user_name");
        return userDao.loadUserByUserName(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "firstName", "lastName", "dateBirth", "email", "password", "contact", "gender", "addressID", "userID");
        return userDao.update(param);
    }

    @Override
    public int count() {
        return userDao.count();
    }

    @Override
    public int sequence() {
        return userDao.sequence();
    }
}
