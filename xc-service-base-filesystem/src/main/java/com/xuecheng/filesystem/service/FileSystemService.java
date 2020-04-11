package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileSystemService {
    @Value("${xuecheng.fastdfs.tracker_servers}")
    String tracker_servers;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    String charset;

    @Autowired
    FileSystemRepository fileSystemRepository;

    /**
     * 上传文件到FastDFS
     * @param multipartFile 文件数据
     * @param fileTag 文件标签
     * @param businessKey 业务key
     * @param metaData 文件元数据
     * @return
     */
    public UploadFileResult uploadFile(MultipartFile multipartFile,
                                       String fileTag,
                                       String businessKey,
                                       String metaData){
        //验证提交的文件是否为空
        if(multipartFile == null){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }

        //第一步：将文件上传到 FastDFS中
        String fileId = this.fdfsUpload(multipartFile);
        if(StringUtils.isEmpty(fileId)){    //上传文件为空时抛出异常
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }

        //第二步：将文件储存到mongoDB内

        //设置相关的文件信息
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFileName(multipartFile.getOriginalFilename());
        fileSystem.setBusinesskey(businessKey);
        fileSystem.setFilePath(fileId); //FastDFS的fileId就是实际的物理路径
        fileSystem.setFileSize(multipartFile.getSize());
        fileSystem.setFiletag(fileTag);
        fileSystem.setFileType(multipartFile.getContentType());
        //文件元数据需要转换成Map对象
        try{
            if(StringUtils.isNotEmpty(metaData)){
                Map metaDataMap = JSON.parseObject(metaData, Map.class);
                fileSystem.setMetadata(metaDataMap);
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
        FileSystem save = fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,save);
    }

    //上传文件到FastDFS
    private String fdfsUpload(MultipartFile multipartFile){
        //初始化FastDFS的环境
        try {
            initFdfsConfig();
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //得到 Storage 连接信息
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer);
            //获取 Storage 客户端
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storeStorage);

            //上传文件
            byte[] fileBytes = multipartFile.getBytes();   // 获取文件信息
            String originalFilename = multipartFile.getOriginalFilename();
            // 获取文件拓展名
            String extStr = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //上传文件
            String fileId = storageClient1.upload_file1(fileBytes, extStr, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //初始化FastDFS环境
    private void initFdfsConfig(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            e.printStackTrace();
            //抛出异常
            ExceptionCast.cast(FileSystemCode.FS_INITFDFS_ERROR);
        }
    }
}
