package com.ecode.admin.api;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin/v1")
public class Admin {
    private static final Logger log = LoggerFactory.getLogger(Admin.class);

    @GetMapping(value = "/index")
    public Object Index() {
        Map<String , Object> data = new LinkedHashMap<>();
        data.put("id","1");
        data.put("firstName","ean");
        data.put("lastName","dalin");
        data.put("gender","Male");
        return data;
    }

    @PostMapping(value = "/body")
    public Object body(Map<String , Object> data) {
        return data;
    }

    @PostMapping(value = "/txt")
    public Object body1(Map<String , Object> data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("dafta"+objectMapper.writeValueAsString(data));
        return data;
    }


    @PostMapping(value = "/txt1")
    public Object testing(Map<String , Object> data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("dafta"+objectMapper.writeValueAsString(data));
        Map<String, Object> d1 = (Map<String, Object>) data.get("data");
        Map<String, Object> v = (Map<String, Object>) d1.get("v4");
        return data;
    }
}
