package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.service.JudgeProduct;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@CrossOrigin
@RestController
public class ApplyLicense {
    @PostMapping(value="generate")
    public String generateLicense(HttpEntity httpEntity, HttpServletResponse response)  {
        System.out.println(httpEntity.getBody());
        Map json = (Map) httpEntity.getBody();
        String result=null;
        try{
             result = JudgeProduct.judgeProductGenerateLicense(json);
             /*File file = new File(result);

            response.setHeader("Content-Disposition", "attachment; filename=license.txt");
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(result.getBytes());
            outputStream.flush();
            outputStream.close();*/

        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }
}
