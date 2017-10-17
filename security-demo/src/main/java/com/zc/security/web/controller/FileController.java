package com.zc.security.web.controller;


import com.zc.security.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;

@RestController
public class FileController {


    private static String PATH = "/Users/zhongcheng/IdeaProjects/security/security-demo/src/main/java/com/zc/security/web/controller";

    @PostMapping("/file")
    public FileInfo file(MultipartFile file) throws Exception {

        String fileName = file.getName();
        long size = file.getSize();

        System.out.println("fileName >>> " + fileName);
        System.out.println("size >>> " + size);

        File f = new File(PATH,  System.currentTimeMillis() / 1000 + ".txt");
        file.transferTo(f);

        return new FileInfo(f.getAbsolutePath());
    }


    @GetMapping("/file/{id}")
    public void file(@PathVariable("id") String id, HttpServletResponse response) throws Exception {

        try (
                OutputStream outs = response.getOutputStream();
                FileInputStream ins = new FileInputStream(new File(PATH, id + ".txt"));
        ) {

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(ins, outs);
            outs.flush();

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}
