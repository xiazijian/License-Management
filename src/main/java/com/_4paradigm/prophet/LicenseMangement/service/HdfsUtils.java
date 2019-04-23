package com._4paradigm.prophet.LicenseMangement.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

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



}














