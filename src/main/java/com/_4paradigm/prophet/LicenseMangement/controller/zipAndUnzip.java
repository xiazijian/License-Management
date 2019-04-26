package com._4paradigm.prophet.LicenseMangement.controller;


import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class zipAndUnzip {
    public static void main(String[] args) throws Exception {
        String path = "/Users/xiaweiyi/Downloads/experience.zip";
        /*String re = DigestUtils.md5Hex(new FileInputStream(path));
        System.out.println(re);
        System.out.println(DigestUtils.md5Hex(new FileInputStream("/Users/xiaweiyi/Downloads/a/hehe")));*/
       //unzip("/Users/xiaweiyi/Downloads/experience.zip","/Users/xiaweiyi/Downloads/experience");
        System.out.println(UUID.randomUUID().toString());

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
