package com.ecode.admin.util;

import java.util.UUID;

import com.ecode.core.util.SystemDateUtil;

public class Uuid {
	public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid +"-"+ SystemDateUtil.getDateFormat("yyyyMMdd-hhmmss");
        return id;
    }
}
