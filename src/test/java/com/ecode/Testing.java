package com.ecode;

import org.junit.jupiter.api.Test;

public class Testing {
	
	@Test
	public void replaceMommatoEmpty() {
		 String str = "1,228.99,0";
	     String newStr = str.replaceAll("\\,", "");
	     System.out.println(newStr);
	}
}
