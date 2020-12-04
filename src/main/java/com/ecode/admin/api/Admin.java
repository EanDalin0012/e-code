package com.ecode.admin.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Admin {
	
	@GetMapping(value = "/index")
	public String Index() {
		return "Index";
	}

}
