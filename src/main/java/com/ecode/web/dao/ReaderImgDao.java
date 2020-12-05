package com.ecode.web.dao;

import com.ecode.core.map.MMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReaderImgDao {
    MMap getResourcesImageById(MMap param);
}
