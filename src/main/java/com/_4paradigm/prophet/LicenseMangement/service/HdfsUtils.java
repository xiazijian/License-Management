package com._4paradigm.prophet.LicenseMangement.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
            //然后添加一个配置文件在压缩包中
            /*PASLicense pasLicense = new PASLicense();
            pasLicense.setMachineCode("xwyxwy");
            pasLicense.setMaxGpuUnits(3);
            pasLicense.setIssuedDate("2019-1-1");
            pasLicense.setProduct("ee");*/
            String json = "{\"algoId\":\"0848b3ed-1812-42ae-a268-96868f1df560\",\"algoName\":\"inception_v4\",\"apps\":[],\"config\":{\"algorithm\":{\"num_epochs\":3,\"batch_size\":96,\"optimizer\":\"momentum\",\"learning_rate_decay_type\":\"piecewise\",\"learning_rate\":0.01,\"end_learning_rate\":1.0E-4,\"learning_rate_decay_factor\":0.1,\"num_epochs_per_decay\":20,\"autotune\":true,\"momentum\":0.9,\"model_name\":\"inception_v4\"},\"env\":{},\"gpuResourceName\":\"alpha.kubernetes.io/nvidia-gpu\",\"image\":\"env/release/1.3.1/prophet/app/autocv-train-python-3.6.tar:41\",\"mode\":{\"algorithm\":\"EXPERT\",\"preprocessor\":\"EXPERT\",\"resource\":\"EXPERT\"},\"preprocessor\":{\"validation_dataset_index\":\"\",\"train_ratio\":\"0.85\",\"preprocessor_name\":\"cls_augment\"},\"resource\":{\"cpuLimit\":2,\"gpuLimit\":1,\"memoryLimit\":14336}},\"configStatus\":\"NORMAL\",\"createTime\":1556182382627,\"createdBy\":1,\"dagId\":\"eb89d1061fa946ec9261ee6312cd6ad2\",\"dataSet\":[{\"name\":\"mnist200_7903205571546290\",\"prn\":\"autocv/mnist200_7903205571546290.image-group\",\"updateTime\":0}],\"deleted\":false,\"endTime\":1556182679394,\"id\":\"eb89d106-1fa9-46ec-9261-ee6312cd6ad2\",\"imageCount\":200,\"inStore\":false,\"indicator\":0.20000000298023224,\"modelPrn\":\"autocv/eb89d106-1fa9-46ec-9261-ee6312cd6ad2-AutoCVGeneralTraining-7537de.model\",\"modelSize\":166899222,\"name\":\"inception_v4\",\"progress\":[0.9900000095367432,0.6666666865348816],\"project\":{\"createTime\":1555866093846,\"createdBy\":1,\"deleted\":false,\"description\":\"\",\"experiments\":[{\"algoId\":\"0848b3ed-1812-42ae-a268-96868f1df560\",\"apps\":[],\"config\":{\"algorithm\":{\"num_epochs\":3,\"batch_size\":96,\"optimizer\":\"momentum\",\"learning_rate_decay_type\":\"piecewise\",\"learning_rate\":0.01,\"end_learning_rate\":1.0E-4,\"learning_rate_decay_factor\":0.1,\"num_epochs_per_decay\":20,\"autotune\":true,\"momentum\":0.9,\"model_name\":\"inception_v4\"},\"env\":{},\"gpuResourceName\":\"alpha.kubernetes.io/nvidia-gpu\",\"image\":\"env/release/1.3.1/prophet/app/autocv-train-python-3.6.tar:41\",\"mode\":{\"algorithm\":\"EXPERT\",\"preprocessor\":\"EXPERT\",\"resource\":\"EXPERT\"},\"preprocessor\":{\"validation_dataset_index\":\"\",\"train_ratio\":\"0.85\",\"preprocessor_name\":\"cls_augment\"},\"resource\":{\"cpuLimit\":2,\"gpuLimit\":1,\"memoryLimit\":14336}},\"configStatus\":\"NORMAL\",\"createTime\":1556096609863,\"createdBy\":1,\"dagId\":\"042e15313fc1428b9f705637c57e5031\",\"dataSet\":[{\"name\":\"mnist200_7903205571546290\",\"prn\":\"autocv/mnist200_7903205571546290.image-group\",\"updateTime\":0}],\"deleted\":false,\"endTime\":1556096705570,\"id\":\"042e1531-3fc1-428b-9f70-5637c57e5031\",\"imageCount\":200,\"inStore\":false,\"metricsState\":{\"allMetadata\":{},\"fileStates\":{},\"refImageStates\":{}},\"name\":\"inception_v4\",\"progress\":[0.0,0.0],\"project\":{\"$ref\":\"$.project\"},\"runId\":10616,\"running\":0,\"scenario\":\"IMAGE_CLASSIFICATION\",\"stage\":\"{\\\"preprocessor\\\":\\\"TERMINATED\\\",\\\"algorithm\\\":\\\"TERMINATED\\\"}\",\"startTime\":1556096611800,\"status\":\"TERMINATED\",\"updateTime\":1556096705572,\"version\":4,\"versionNumber\":4,\"workspace\":\"hdfs://nameservice1/user/autocv-130-gz/autocv/autocv/workspace/042e1531-3fc1-428b-9f70-5637c57e5031/\"},{\"algoId\":\"0848b3ed-1812-42ae-a268-96868f1df560\",\"apps\":[],\"config\":{\"algorithm\":{\"num_epochs\":3,\"batch_size\":96,\"optimizer\":\"momentum\",\"learning_rate_decay_type\":\"piecewise\",\"learning_rate\":0.01,\"end_learning_rate\":1.0E-4,\"learning_rate_decay_factor\":0.1,\"num_epochs_per_decay\":20,\"autotune\":true,\"momentum\":0.9,\"model_name\":\"inception_v4\"},\"env\":{},\"mode\":{\"algorithm\":\"EXPERT\",\"preprocessor\":\"EXPERT\",\"resource\":\"EXPERT\"},\"preprocessor\":{\"validation_dataset_index\":\"\",\"train_ratio\":\"0.85\",\"preprocessor_name\":\"cls_augment\"},\"resource\":{\"cpuLimit\":2,\"gpuLimit\":1,\"memoryLimit\":14336}},\"configStatus\":\"NORMAL\",\"createTime\":1555933693493,\"createdBy\":1,\"dataSet\":[{\"name\":\"mnist200_7903205571546290\",\"prn\":\"autocv/mnist200_7903205571546290.image-group\",\"updateTime\":0}],\"deleted\":false,\"endTime\":1556096485342,\"id\":\"7948dacf-5ca4-409b-a727-35e743ad7756\",\"imageCount\":200,\"inStore\":false,\"name\":\"inception_v4\",\"progress\":[0.0,0.0],\"project\":{\"$ref\":\"$.project\"},\"running\":0,\"scenario\":\"IMAGE_CLASSIFICATION\",\"status\":\"TERMINATED\",\"updateTime\":1556096485345,\"version\":2,\"versionNumber\":2,\"workspace\":\"hdfs://nameservice1/user/autocv-130-gz/autocv/autocv/workspace/7948dacf-5ca4-409b-a727-35e743ad7756/\"},{\"algoId\":\"0848b3ed-1812-42ae-a268-96868f1df560\",\"apps\":[],\"config\":{\"algorithm\":{\"num_epochs\":3,\"batch_size\":96,\"optimizer\":\"momentum\",\"learning_rate_decay_type\":\"piecewise\",\"learning_rate\":0.01,\"end_learning_rate\":1.0E-4,\"learning_rate_decay_factor\":0.1,\"num_epochs_per_decay\":20,\"autotune\":true,\"momentum\":0.9,\"model_name\":\"inception_v4\"},\"env\":{},\"gpuResourceName\":\"alpha.kubernetes.io/nvidia-gpu\",\"image\":\"env/release/1.3.1/prophet/app/autocv-train-python-3.6.tar:41\",\"mode\":{\"algorithm\":\"EXPERT\",\"preprocessor\":\"EXPERT\",\"resource\":\"EXPERT\"},\"preprocessor\":{\"validation_dataset_index\":\"\",\"train_ratio\":\"0.85\",\"preprocessor_name\":\"cls_augment\"},\"resource\":{\"cpuLimit\":2,\"gpuLimit\":1,\"memoryLimit\":14336}},\"configStatus\":\"NORMAL\",\"createTime\":1556096477037,\"createdBy\":1,\"dagId\":\"7d7b330918024211b707445a3ae7f099\",\"dataSet\":[{\"name\":\"mnist200_7903205571546290\",\"prn\":\"autocv/mnist200_7903205571546290.image-group\",\"updateTime\":0}],\"deleted\":false,\"endTime\":1556096580643,\"id\":\"7d7b3309-1802-4211-b707-445a3ae7f099\",\"imageCount\":200,\"inStore\":false,\"metricsState\":{\"allMetadata\":{},\"fileStates\":{},\"refImageStates\":{}},\"name\":\"inception_v4\",\"progress\":[0.0,0.0],\"project\":{\"$ref\":\"$.project\"},\"runId\":10615,\"running\":0,\"scenario\":\"IMAGE_CLASSIFICATION\",\"stage\":\"{\\\"preprocessor\\\":\\\"TERMINATED\\\",\\\"algorithm\\\":\\\"TERMINATED\\\"}\",\"startTime\":1556096479478,\"status\":\"TERMINATED\",\"updateTime\":1556096580645,\"version\":4,\"versionNumber\":3,\"workspace\":\"hdfs://nameservice1/user/autocv-130-gz/autocv/autocv/workspace/7d7b3309-1802-4211-b707-445a3ae7f099/\"},{\"algoId\":\"0848b3ed-1812-42ae-a268-96868f1df560\",\"apps\":[],\"config\":{\"algorithm\":{\"num_epochs\":3,\"batch_size\":96,\"optimizer\":\"momentum\",\"learning_rate_decay_type\":\"piecewise\",\"learning_rate\":0.01,\"end_learning_rate\":1.0E-4,\"learning_rate_decay_factor\":0.1,\"num_epochs_per_decay\":20,\"autotune\":true,\"momentum\":0.9,\"model_name\":\"inception_v4\"},\"env\":{},\"gpuResourceName\":\"alpha.kubernetes.io/nvidia-gpu\",\"image\":\"env/release/1.3.1/prophet/app/autocv-train-python-3.6.tar:41\",\"mode\":{\"algorithm\":\"EXPERT\",\"preprocessor\":\"EXPERT\",\"resource\":\"EXPERT\"},\"preprocessor\":{\"validation_dataset_index\":\"\",\"train_ratio\":\"0.85\",\"preprocessor_name\":\"cls_augment\"},\"resource\":{\"cpuLimit\":2,\"gpuLimit\":1,\"memoryLimit\":14336}},\"configStatus\":\"NORMAL\",\"createTime\":1556096712757,\"createdBy\":1,\"dagId\":\"9da6e2ff3810491e96a7801cd4ceb63b\",\"dataSet\":[{\"name\":\"mnist200_7903205571546290\",\"prn\":\"autocv/mnist200_7903205571546290.image-group\",\"updateTime\":0}],\"deleted\":false,\"endTime\":1556096741290,\"id\":\"9da6e2ff-3810-491e-96a7-801cd4ceb63b\",\"imageCount\":200,\"inStore\":false,\"metricsState\":{\"allMetadata\":{},\"fileStates\":{},\"refImageStates\":{}},\"name\":\"inception_v4\",\"progress\":[0.0,0.0],\"project\":{\"$ref\":\"$.project\"},\"runId\":10617,\"running\":0,\"scenario\":\"IMAGE_CLASSIFICATION\",\"stage\":\"{\\\"preprocessor\\\":\\\"TERMINATED\\\",\\\"algorithm\\\":\\\"TERMINATED\\\"}\",\"startTime\":1556096715226,\"status\":\"TERMINATED\",\"updateTime\":1556096741293,\"version\":4,\"versionNumber\":5,\"workspace\":\"hdfs://nameservice1/user/autocv-130-gz/autocv/autocv/workspace/9da6e2ff-3810-491e-96a7-801cd4ceb63b/\"},{\"algoId\":\"0848b3ed-1812-42ae-a268-96868f1df560\",\"apps\":[],\"config\":{\"algorithm\":{\"num_epochs\":3,\"batch_size\":96,\"model_dir\":\"/tmp/tfmodel\",\"optimizer\":\"momentum\",\"momentum\":0.9,\"learning_rate_decay_type\":\"piecewise\",\"learning_rate\":0.01,\"end_learning_rate\":1.0E-4,\"learning_rate_decay_factor\":0.1,\"num_epochs_per_decay\":20,\"autotune\":true,\"model_name\":\"inception_v4\"},\"env\":{},\"gpuResourceName\":\"alpha.kubernetes.io/nvidia-gpu\",\"image\":\"env/release/1.3.1/prophet/app/autocv-train-python-3.6.tar:37\",\"mode\":{\"algorithm\":\"EXPERT\",\"preprocessor\":\"EXPERT\",\"resource\":\"EXPERT\"},\"preprocessor\":{\"validation_dataset_index\":\"\",\"output_dir\":\"\",\"src_json_path\":\"\",\"train_ratio\":\"0.85\",\"preprocessor_name\":\"cls_augment\"},\"resource\":{\"cpuLimit\":2,\"gpuLimit\":1,\"memoryLimit\":14336}},\"configStatus\":\"NORMAL\",\"createTime\":1555866095454,\"createdBy\":1,\"dagId\":\"b4030267cb1f49a68a31d86d6d67caa3\",\"dataSet\":[{\"name\":\"mnist200_7903205571546290\",\"prn\":\"autocv/mnist200_7903205571546290.image-group\",\"updateTime\":0}],\"deleted\":false,\"endTime\":1555866179754,\"id\":\"b4030267-cb1f-49a6-8a31-d86d6d67caa3\",\"imageCount\":200,\"inStore\":false,\"name\":\"inception_v4\",\"progress\":[0.0,0.0],\"project\":{\"$ref\":\"$.project\"},\"runId\":10170,\"running\":0,\"scenario\":\"IMAGE_CLASSIFICATION\",\"stage\":\"{\\\"preprocessor\\\":\\\"FAILED\\\",\\\"algorithm\\\":\\\"SUCCEEDED\\\"}\",\"startTime\":1555866135770,\"status\":\"FAILED\",\"updateTime\":1555866179758,\"version\":6,\"versionNumber\":1,\"workspace\":\"hdfs://nameservice1/user/autocv-130-gz/autocv/autocv/workspace/b4030267-cb1f-49a6-8a31-d86d6d67caa3/\"},{\"$ref\":\"$\"}],\"id\":\"2823e471-7eb1-4667-93d1-e5a6fe7fd189\",\"inStore\":false,\"name\":\"inception_v4小数据\",\"scenario\":\"IMAGE_CLASSIFICATION\",\"updateTime\":1555866093846,\"version\":0},\"projectId\":\"2823e471-7eb1-4667-93d1-e5a6fe7fd189\",\"projectName\":\"inception_v4小数据\",\"runId\":10709,\"running\":0,\"scenario\":\"IMAGE_CLASSIFICATION\",\"stage\":\"{\\\"preprocessor\\\":\\\"SUCCEEDED\\\",\\\"algorithm\\\":\\\"SUCCEEDED\\\"}\",\"startTime\":1556182385229,\"status\":\"SUCCEEDED\",\"updateTime\":1556182679398,\"version\":15,\"versionNumber\":6,\"workspace\":\"hdfs://nameservice1/user/autocv-130-gz/autocv/autocv/workspace/eb89d106-1fa9-46ec-9261-ee6312cd6ad2/\"}";
            ZipEntry scenarioZipEntry = new ZipEntry("meta");
            zipOutputStream.putNextEntry(scenarioZipEntry);
            InputStream inputStream = new ByteArrayInputStream(json.getBytes());
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

    public Boolean checkAndUpload(String path,String scenario) throws IOException{
        System.out.println("进入了checkAndUpload");
        Boolean result = false;
        FileSystem fileSystem = getFileSystem();
        Path p = new Path("/"+path+"/"+"meta");
        System.out.println("p:"+p.toString());
        FSDataInputStream fsDataInputStream = fileSystem.open(p);
        System.out.println("fsdata:"+fsDataInputStream.available());
        /*BufferedInputStream bufferedInputStream = new BufferedInputStream(fsDataInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = bufferedInputStream.read(buffer))>0){
            System.out.println("进入while");
            byteArrayOutputStream.write(buffer);
        }
        System.out.println("byteArrayOutputStream:"+byteArrayOutputStream.size());
        String config = byteArrayOutputStream.toString("utf-8");*/
        String config = org.apache.commons.io.IOUtils.toString(fsDataInputStream,"utf-8");
        System.out.println("config:"+config);

        Map map = JSONObject.parseObject(config);
        System.out.println(map.isEmpty());
        System.out.println(map);
        result = map.get("scenario").toString().equals(scenario);
        if(!result){
            fileSystem.delete(new Path("/"+path),true);
        }else {
            fileSystem.delete(new Path("/"+path+"/"+"meta"),true);
        }
        return  result;
    }

    public String upload(InputStream zipStream) throws IOException {
        System.out.println("进入了upload");
        System.out.println("zipStream："+zipStream.available());
        FileSystem fileSystem = getFileSystem();

            ZipInputStream zipInputStream = new ZipInputStream(zipStream);
            ZipEntry entry = zipInputStream.getNextEntry();

            String path = UUID.randomUUID().toString();
            while (entry != null) {
                System.out.println("entryname:" + entry.getName());
                if (!entry.isDirectory()) {
                    try(FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/" + path + "/" + entry.getName()))){
                        IOUtils.copyBytes(zipInputStream,fsDataOutputStream,10240);
                    }
                    //注释部分的写法是将所有字节填充进byteArrayOutputStream，如果文件很大就会导致内存溢出，所以直接读点就往hdfs写点，如上：
                    /*int len = 0;
                    byte[] buffer = new byte[1024*10];
                    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                        while ((len = zipInputStream.read(buffer)) > 0) {
                            System.out.println("进入upload的while");
                            byteArrayOutputStream.write(buffer, 0, len);
                        }
                        System.out.println("upload byteArrayOutputStream：" + byteArrayOutputStream.size());
                        System.out.println(new Path("/" + path + "/" + entry.getName()));
                        try (FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/" + path + "/" + entry.getName()))) {
                                IOUtils.copyBytes(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), fsDataOutputStream, 1024);

                        }
                    }*/
                } else {
                    fileSystem.mkdirs(new Path("/" + path + "/" + entry.getName()));
                }

                entry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
            return path;

    }




}














