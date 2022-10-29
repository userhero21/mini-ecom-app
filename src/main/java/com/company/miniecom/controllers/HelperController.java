package com.company.miniecom.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class HelperController {

    @Value("${img.upload.location}")
    String location;


    @RequestMapping("/showCover")
    public void showCover(@RequestParam("img") String img, HttpServletResponse resp) {
        try {
            ServletOutputStream outputStream = resp.getOutputStream();
            Path path = Path.of(location + img);
            Files.copy(path, outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
