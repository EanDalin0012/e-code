package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.ProductDao;
import com.ecode.admin.service.ProductService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class ProductServiceImplement implements ProductService{
	
	@Autowired
    private ProductDao productDao;

    @Override
    public MultiMap retrieveList(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "status");
        return productDao.retrieveList(param);
    }

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "name", "status", "user_id", "category_id");
        return productDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "status","user_id");
        return productDao.delete(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "name", "status", "user_id");
        return productDao.update(param);
    }

    @Override
    public int updateShowOnWeb(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "web_show", "status", "user_id");
        return productDao.updateShowOnWeb(param);
    }

    @Override
    public int updateShowOnMobile(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "mobile_show", "status", "user_id");
        return productDao.updateShowOnMobile(param);
    }

    @Override
    public int count() {
        return productDao.count();
    }

    @Override
    public int sequence() {
        return productDao.sequence();
    }
}
