package com.ecode.admin.service;

import com.ecode.core.map.MMap;

public interface ResourceFileInfoService {
	long getLastId();
    int fileUpload(MMap param);
    MMap getResourceById(MMap param);
    int deleteById(MMap param);
}
