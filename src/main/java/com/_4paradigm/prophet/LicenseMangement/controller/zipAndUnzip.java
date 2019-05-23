package com._4paradigm.prophet.LicenseMangement.controller;


import com.alibaba.fastjson.JSON;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class zipAndUnzip {
    private static final Logger logger = LoggerFactory.getLogger(zipAndUnzip.class);
    public static void main(String[] args) throws Exception {
        String path = "/Users/xiaweiyi/Downloads/experience.zip";
        /*String re = DigestUtils.md5Hex(new FileInputStream(path));
        System.out.println(re);
        System.out.println(DigestUtils.md5Hex(new FileInputStream("/Users/xiaweiyi/Downloads/a/hehe")));*/
       //unzip("/Users/xiaweiyi/Downloads/experience.zip","/Users/xiaweiyi/Downloads/experience");
        //System.out.println(UUID.randomUUID().toString());
        /*String h = "00212649-2061-4bac-9de5-78c1aa430d78_IMAGE_CLASSIFICATION/meta";
        String[] e = h.split("/");
        String a = StringUtils.substringAfter(h,"/");
        System.out.println(a);*/

        /*Pattern pattern = Pattern.compile("model\\.ckpt");
        String name = "autotune/model/model.ckpt-264.data-00001-of-00002";
        System.out.println(!pattern.matcher(name).find());*/
        String x = "\"he\"";
        System.out.println(x);
        String name = "autotune/model/model.ckpt-264.data-00001-of-00002";
        Map<String,String> extraMap = new HashMap<>();
        extraMap.put("URL","/autocv-server/v1/experiments/{experimentId}/start");
        extraMap.put("项目名称",name);
        String extra = JSON.toJSONString(extraMap).toString();
        logger.info("新建实验的extra:{}",extra);
        //System.out.println(extra);

    }
    public Map<String,String> compareAndUpdateFile( Map<String,String> oldFile, Map<String,String> newFile){
        Map<String,String> map = new HashMap<>();
        for(String key:map.keySet()){
            System.out.println("Key:"+key);
            System.out.println("value:"+map.get(key));
        }

        return map;
    }
    public static void unzip(String sourceZip,String destDir) throws Exception{
        try{
            Project p = new Project();
            Expand e = new Expand();
            e.setProject(p);
            e.setSrc(new File(sourceZip));
            e.setOverwrite(false);
            e.setDest(new File(destDir));
           /*
           ant下的zip工具默认压缩编码为UTF-8编码，
           而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
           所以解压缩时要制定编码格式
           */
            //e.setEncoding("gbk");
            e.execute();
        }catch(Exception e){
            throw e;
        }
    }
    public static Map<String,String> readFiles(String filepath){
        Map<String,String> map = new HashMap<>();
        File file = new File(filepath);
        String[] filelist = file.list();
        for(int i=0;i<filelist.length;i++){
            File f = new File(filepath+"/"+filelist[i]);
            map.put(f.getName(),f.getPath());
            System.out.println("name:"+f.getName());
            System.out.println("path:"+f.getPath());
        }
        return map;
    }


}
