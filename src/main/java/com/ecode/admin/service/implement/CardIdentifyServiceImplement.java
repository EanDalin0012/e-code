package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.CardIdentifyDao;
import com.ecode.admin.service.CardIdentifyService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class CardIdentifyServiceImplement implements CardIdentifyService{

	@Autowired
    private CardIdentifyDao cardIdentifyDao;

    @Override
    public MultiMap retrieveList(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "status");
        return cardIdentifyDao.retrieveList(param);
    }

    @Override
    public MMap retrieveCardIdentifyById(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "card_id");
        return cardIdentifyDao.retrieveCardIdentifyById(param);
    }

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "card_id","status");
        return cardIdentifyDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id","status");
        return cardIdentifyDao.delete(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id","card_id","status");
        return cardIdentifyDao.update(param);
    }

    @Override
    public int count() {
        return cardIdentifyDao.count();
    }

}
