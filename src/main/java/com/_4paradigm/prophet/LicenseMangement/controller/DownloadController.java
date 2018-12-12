package com._4paradigm.prophet.LicenseMangement.controller;


import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
@CrossOrigin
public class DownloadController {
    @PostMapping(value = "/download",produces = "application/json; charset=utf-8")
    public void download(HttpEntity httpEntity, HttpServletResponse response ) throws IOException {
        Map json = (Map) httpEntity.getBody();
        String data = json.get("data").toString().substring(1,json.get("data").toString().length()-1);

        OutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment; filename=license.txt");
        try {
            outputStream.write(data.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outputStream.close();
        }

    }
    /*@GetMapping(value = "/download",produces = "application/json; charset=utf-8")
    public void download(HttpServletResponse response ) throws IOException {

        String data = "ahsdjhajdhjashdjashdjhajdhajh";

        OutputStream outputStream = response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment; filename=license.txt");
        try {
            outputStream.write(data.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            outputStream.close();
        }

    }*/
}
