package com.xuecheng.test.fastdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.Transient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastDFS {
    /**
     * 测试文件上传
     */
    @Test
    public void testFileUpload(){
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //连接 Tracker
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取 Storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建 Storage Client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);
            //向 Storage 服务器上传文件,拿到文件id
            String filePath = "d:/test1.html";
            String fileId = storageClient1.upload_file1(filePath, "html", null);
            System.out.println("上传成功：" + fileId);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 文件下载
     */
    @Test
    public void TestFileDownload(){
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //连接 Tracker
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取 Storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建 Storage Client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

            String fileId = "group1/M00/00/00/CgEBmF6QKa-Abjv7AAAGqbcDljA60.html";
            String saveToPath = "d:/test.html";
            //下载文件
            byte[] bytes = storageClient1.download_file1(fileId);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(saveToPath));
            fileOutputStream.write(bytes);
            System.out.println("下载成功! " + saveToPath);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 文件信息查询
     */
    @Test
    public void TestFileInfoQuery(){
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //连接 Tracker
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取 Storage
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //创建 Storage Client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

            String fileId = "group1/M00/00/00/CgEBmF6QKa-Abjv7AAAGqbcDljA60.html";
            FileInfo fileInfo = storageClient1.query_file_info1(fileId);
            System.out.println("文件信息: "+ fileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
