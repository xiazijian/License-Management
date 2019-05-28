package com._4paradigm.prophet.LicenseMangement.controller;

import com._4paradigm.prophet.LicenseMangement.entity.BaseResponse;
import com._4paradigm.prophet.LicenseMangement.entity.PathRequest;
import com._4paradigm.prophet.LicenseMangement.service.HdfsUtils;
import com._4paradigm.prophet.LicenseMangement.service.InsertLicenseRecord;
import com._4paradigm.prophet.LicenseMangement.service.JudgeProduct;
import com.alibaba.fastjson.JSON;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "api")
public class ApplyLicense {
    @Autowired
    private InsertLicenseRecord insertLicenseRecord;
    @Autowired
    private HdfsUtils hdfsUtils;

    @PostMapping(value="he")
    public void ha(HttpEntity httpEntity){
        System.out.println(httpEntity.getBody());
        Map json = (Map) httpEntity.getBody();
        System.out.println(json.get("algo"));
        System.out.println(json.get("map"));
        Map<String,String> map = (Map<String, String>) json.get("map");
        System.out.println(map.get("a"));
    }

    @GetMapping(value = "/create")
    public void create(HttpServletResponse response ) throws IOException {
        String path = "/user/xiaweiyi/he.txt";
        File file = new File("/Users/xiaweiyi/Downloads/he.txt");
        System.out.println(file.length());
        FileInputStream fileInputStream = new FileInputStream(file);
        //hdfsUtils.createFile(path,fileInputStream);

    }
    @GetMapping(value = "/testHdfs")
    public String testHdfs(HttpServletResponse response) throws IOException {
        try {
            //hdfsUtils.readFile("/user/xiaweiyi/algo/OCR/classification/filesMD5.txt");
           hdfsUtils.downloadExperiment("/05bdf670-059d-4a9e-b506-1405bbd0464a",response);
            //response = hdfsUtils.downloadFile(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hehe";

    }
    @GetMapping(value = "/{id}/testThread")
    public BaseResponse testThread(@PathVariable(value = "id") String id){
        String x= "haha";
        new Thread(){
           public void run(){
               for(int i=0;i<100000;i++){
                   System.out.println(x+id);
               }
           }
        }.start();
        System.out.println("xwy");
        return BaseResponse.success("hehe");
    }
    //测试当浏览器发送一个请求，当没有收到response的时候主动关闭了后台会收到什么异常
    @PostMapping(value = "testStopRequest")
    public BaseResponse testStopRequest(){
        for(int i=0;i<1000;i++){
            for (int j= 0;j<1000;j++){
                System.out.println(i+"::"+j);
            }
        }
        return BaseResponse.success("xwy");
    }

    @PostMapping(value = "testRes")
    public BaseResponse test(HttpServletResponse response) throws IOException {
        response.sendError(403,"hehe");
        return new BaseResponse(BaseResponse.STATUS_EXPERIMENT_TYPE_ERROR);
    }
    @GetMapping(value = "/list")
    public void list() throws IOException {
        hdfsUtils.listFile("/05bdf670-059d-4a9e-b506-1405bbd0464a");
    }
    @PostMapping("/post")
    public void p (@RequestBody PathRequest path){
        System.out.println("path::"+path.getPath());
    }

    @PostMapping("/uploadCheck")
    public BaseResponse uploadCheck(HttpServletRequest request) throws IOException, FileUploadException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("unsupported protocol... must be multipart content!");
            return new BaseResponse(BaseResponse.STATUS_BAD_REQUEST);
        }
        ServletFileUpload upload = new ServletFileUpload();
        FileItemIterator iter = upload.getItemIterator(request);
        Boolean result = true;
        int count = 0;
        String project = "";
        String experiment = null;
        while(iter.hasNext()){
            FileItemStream item = iter.next();
            InputStream inputStream = item.openStream();
            System.out.println("item.openStream()-inputStream.available:"+inputStream.available());
            count++;
            if(item.isFormField()){
                project = Streams.asString(inputStream, "UTF-8");
                //文件先接收到
                if(count!=1){

                    result = hdfsUtils.checkAndUpload(experiment,project);
                }
            }else{
                experiment = hdfsUtils.upload(inputStream);
                // 先收到projectid
               if(count!=1){
                    result = hdfsUtils.checkAndUpload(experiment,project);
                }
            }
        }
        if(result){
            return BaseResponse.success(experiment);
        }else{
            return new BaseResponse(BaseResponse.STATUS_INTERNAL_ERROR);
        }

    }
    //生成license
    @PostMapping(value="generate")
    public String generateLicense(HttpEntity httpEntity, HttpServletResponse response)  {
        System.out.println(httpEntity.getBody());
        System.out.println("json:"+JSON.toJSONString(httpEntity.getBody()));
        Map json = (Map) httpEntity.getBody();
        String IssuedDate = (String) json.get("IssuedDate");
        String ExpiredDate = (String) json.get("ExpiredDate");
        System.out.println(IssuedDate+"|"+ExpiredDate);
        String config = JSON.toJSONString(httpEntity.getBody());
        String result=null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try{
            simpleDateFormat.parse(IssuedDate);
            simpleDateFormat.parse(ExpiredDate);
            try{
                result = JudgeProduct.judgeProductGenerateLicense(json);
                Map<String,String> map = new HashMap<>();
                map.put("name", (String) json.get("username"));
                map.put("license",result);
                map.put("configuration",config.substring(1,config.length()-1));
                insertLicenseRecord.insert(map);
                System.out.println(result);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }catch (ParseException e){
            response.setStatus(500);
        }
        return result;
    }
}
