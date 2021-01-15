package com.ecode;

import org.junit.jupiter.api.Test;

import com.ecode.core.util.SystemUtil;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Testing {

    //	@Test
    public void replaceMommatoEmpty() {
        String str = "1,228.99,0";
        String newStr = str.replaceAll("\\,", "");
        System.out.println(newStr);
    }

    public static void main(String[] args) throws Exception {

        String path = "/uploads/products/1fce9580a-842e-4d9a-8dab-0a660c16d81c-20201205032605.jpg";
        String filepath = SystemUtil.projectPath() + path;

        Path firstPath = Paths.get(filepath);
        Path secondPath = Paths.get("/docs/status.txt");

        System.out.println("exists: " + Files.exists(firstPath));
        System.out.println("notExists: " + Files.notExists(firstPath));

    }
}
