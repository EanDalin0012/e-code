package com.ecode.core.util;

import java.util.UUID;

public class Uuid {
    public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid +"-"+ SystemDateUtil.getDateFormat("yyyyMMdd-hhmmss");
        return id;
    }
}
