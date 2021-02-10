package com.ecode.admin.dao;

import com.ecode.core.map.MMap;
import com.ecode.core.map.MultiMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestingDecryptionDao {
    int save(MMap param);
    MultiMap retrieve();
}
