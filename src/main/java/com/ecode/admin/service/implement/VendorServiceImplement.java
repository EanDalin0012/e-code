package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecode.admin.dao.VendorDao;
import com.ecode.admin.service.VendorService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class VendorServiceImplement implements VendorService {

    @Autowired
    private VendorDao vendorDao;

    @Override
    public MultiMap retrieveList(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "status");
        return vendorDao.retrieveList(param);
    }

    @Override
    public int save(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "name", "contact", "user_id", "status");
        return vendorDao.save(param);
    }

    @Override
    public int delete(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "user_id", "status");
        return vendorDao.delete(param);
    }

    @Override
    public int update(MMap param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "name", "contact", "user_id", "status");
        return vendorDao.update(param);
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int sequence() {
        return vendorDao.sequence();
    }

}
