package com.ecode.admin.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin/v1")
public class Admin {

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
}
