package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.UserAccountDao;
import com.ecode.admin.service.UserAccountService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class UserAccountServiceImplement implements UserAccountService{

	@Autowired
    private UserAccountDao userAccountDao;

    @Override
    public int updateUserAccount(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "userName", "enabled", "accountLocked", "credentialsExpired", "accountExpired");
        return userAccountDao.updateUserAccount(param);
    }

    @Override
    public MMap retrieveUserAccountByID(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id");
        return userAccountDao.retrieveUserAccountByID(param);
    }

    @Override
    public MultiMap getList(MMap param) {
        return userAccountDao.getList(param);
    }

    @Override
    public int count() {
        return userAccountDao.count();
    }
}
