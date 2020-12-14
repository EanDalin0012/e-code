package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.ProductDescriptionDao;
import com.ecode.admin.service.ProductDescriptionService;
import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;

@Service
public class ProductDescriptionServiceImplement implements ProductDescriptionService{

	@Autowired
	private ProductDescriptionDao productDescriptionDao;
	@Override
	public MultiMap retrieveList(MMap param) {
		// TODO Auto-generated method stub
		return productDescriptionDao.retrieveList(param);
	}

	@Override
	public MultiMap retrieveByID(MMap param) {
		// TODO Auto-generated method stub
		return productDescriptionDao.retrieveByID(param);
	}

	@Override
	public int save(MMap param) {
		// TODO Auto-generated method stub
		return productDescriptionDao.save(param);
	}

	@Override
	public int delete(MMap param) {
		// TODO Auto-generated method stub
		return productDescriptionDao.delete(param);
	}

	@Override
	public int update(MMap param) {
		// TODO Auto-generated method stub
		return productDescriptionDao.update(param);
	}

}
