package com._4paradigm.prophet.LicenseMangement.service;

import com._4paradigm.prophet.LicenseMangement.entity.PASLicense;
import com.alibaba.fastjson.JSON;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class HdfsUtils {
    @Value("${spring.hadoop.fsUri}")
    private String hdfsPath;

    /**
     * @获取hdfs配置
     * @return
     */
    private Configuration getHdfsConfig(){
        Configuration configuration = new Configuration();
        return configuration;
    }
    /**
     * @获取hfds对象
     */
    private FileSystem getFileSystem(){
        //客户端去操作hdfs时，是有一个用户身份的,
        // 默认情况下，hdfs客户端api会从jvm中获取一个参数来作为自己的用户身份：-DHADOOP_USER_NAME=hadoop

        //也可以在构造客户端fs对象时，通过参数传递进去
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(new URI(hdfsPath),getHdfsConfig(),"xiaweiyi");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return fileSystem;
    }

    /**
     *
     * @param filePath
     * @throws Exception
     */
    public void readFile(String filePath) throws Exception {
        FileSystem fs = getFileSystem();
        Path path = new Path(filePath);
        InputStream in = null;
        try {
         in = fs.open(path);
        //复制到标准输出流
        IOUtils.copyBytes(in, System.out, 4096, false);
         System.out.println( "\n读取文件成功！" );
        } catch (Exception e) {
         System.out.println( "\n读取文件失败！" );
     }
     finally {
     IOUtils.closeStream(in);
     }
 }

    public void createFile(String filePath,InputStream inputStream) throws IOException {
        FileSystem fileSystem = getFileSystem();
        Path path = new Path(filePath);

        FSDataOutputStream fsDataOutputStream = fileSystem.create(path, new Progressable() {
            @Override
            public void progress() {
                System.out.println(".");
            }
        });
        IOUtils.copyBytes(inputStream,fsDataOutputStream,4096,true);
    }

    public HttpServletResponse downloadFile(HttpServletResponse response) throws IOException {
        File file = new File("/Users/xiaweiyi/Downloads/algo/OCR/classification/ha");
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream =null;
        ZipOutputStream zipOutputStream =null;

        zipOutputStream = new ZipOutputStream(response.getOutputStream());
        response.setHeader("Content-Disposition", "attachment; filename=experience.zip");
        fileInputStream = new FileInputStream(file);
        bufferedInputStream = new BufferedInputStream(fileInputStream,1024*10);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zipOutputStream.putNextEntry(zipEntry);
        byte[] bufs = new byte[1024 * 10];
        int read =0;
        while((read = bufferedInputStream.read(bufs,0,1024*10))!=-1){
            System.out.println(read);
            zipOutputStream.write(bufs,0,read);
        }
        zipOutputStream.close();
        return response;

    }
    //递归实现压缩
    public void compress(String name,FileStatus fileStatus,ZipOutputStream zipOutputStream,FileSystem fileSystem) throws IOException {
        FSDataInputStream fsDataInputStream = null;
        BufferedInputStream bufferedInputStream = null;
            if(!fileStatus.isDirectory()){
                    zipOutputStream.putNextEntry(new ZipEntry(name));
                    fsDataInputStream = fileSystem.open(fileStatus.getPath());
                    bufferedInputStream =new BufferedInputStream(fsDataInputStream,1024*10);
                    byte[] bufs = new byte[1024 * 10];
                    int read = 0;
                    while((read = bufferedInputStream.read(bufs,0,1024*10))!=-1){
                        System.out.println(read);
                        zipOutputStream.write(bufs,0,read);
                    }
                    bufferedInputStream.close();
            }else{
                FileStatus[] fileStatuses = fileSystem.listStatus(fileStatus.getPath());
                if(fileStatuses.length==0){
                    zipOutputStream.putNextEntry(new ZipEntry(name+"/"));
                }else{
                    for(FileStatus nextfileStatus:fileStatuses){
                        String[] f = nextfileStatus.getPath().toString().split("/");
                        compress(name+"/"+f[f.length-1],nextfileStatus,zipOutputStream,fileSystem);
                    }
                }
            }


    }
    public void downloadExperiment(String path, HttpServletResponse response) {
        Path path1 = new Path(path);
        FileSystem fileSystem = null;

        ZipOutputStream zipOutputStream = null;
            try {
                zipOutputStream =new ZipOutputStream(response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        /*response.reset();
        response.setContentType("application/octet-stream");*/
        String zipname = UUID.randomUUID().toString()+"_"+"xwy"+".zip";
        response.setHeader("Content-Disposition", "attachment; filename="+zipname);
        try{
            fileSystem = getFileSystem();
            FileStatus[] fileStatuses = fileSystem.listStatus(path1);
            for(FileStatus fileStatus:fileStatuses){
                String[] f = fileStatus.getPath().toString().split("/");
                compress(f[f.length-1],fileStatus,zipOutputStream,fileSystem);
            }
            PASLicense pasLicense = new PASLicense();
            pasLicense.setMachineCode("xwyxwy");
            pasLicense.setMaxGpuUnits(3);
            pasLicense.setIssuedDate("2019-1-1");
            pasLicense.setProduct("ee");
            ZipEntry scenarioZipEntry = new ZipEntry("meta");
            zipOutputStream.putNextEntry(scenarioZipEntry);
            InputStream inputStream = new ByteArrayInputStream(JSON.toJSONBytes(pasLicense));
            byte[] bytes = new byte[1024];
            int read = 0;
            while((read = inputStream.read(bytes,0,1024))!=-1){
                zipOutputStream.write(bytes,0,read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }




}














