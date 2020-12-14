package com.ecode.admin.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecode.admin.dao.ProductDetailReferencesDao;
import com.ecode.admin.service.ProductDetailReferencesService;
import com.ecode.admin.util.ValidatorUtil;
import com.ecode.core.exception.ValidatorException;
import com.ecode.core.map.MMap;

@Service
public class ProductDetailReferencesServiceImplement implements ProductDetailReferencesService{

	@Autowired
	private ProductDetailReferencesDao productDetailReferencesDao;
	
	@Override
	public int save(MMap param) throws ValidatorException {
		ValidatorUtil.validate(param, "product_id","staus","product_detials_id","resource_id");
		return productDetailReferencesDao.save(param);
	}

	@Override
	public int delete(MMap param) {
		// TODO Auto-generated method stub
		return productDetailReferencesDao.delete(param);
	}

}
