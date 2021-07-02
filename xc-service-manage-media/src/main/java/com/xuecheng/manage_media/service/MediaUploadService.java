package com.xuecheng.manage_media.service;

import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 媒体文件上传服务
 */
public interface MediaUploadService {
    /**
     * 文件信息校验并注册
     * @param fileMd5 文件md5值
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param mimetype 文件类型
     * @param fileExt 文件扩展名
     * @return 通用的响应信息
     */
    ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt);

    /**
     * 检查文件块是否存在
     * @param fileMd5 文件md5
     * @param chunk 块编号
     * @param chunkSize 块大小
     * @return 检查文件块信息的响应
     */
    CheckChunkResult checkChunk(String fileMd5,Integer chunk,Integer chunkSize);

    /**
     * 上传分块文件
     * @param file 上传的文件
     * @param chunk 分块号
     * @param fileMd5 文件MD5
     * @return ResponseResult
     */
    ResponseResult uploadChunk(MultipartFile file,Integer chunk,String fileMd5);

    /**
     * 合并文件块
     * @param fileMd5 文件MD5
     * @param fileName 文件名称
     * @param fileSize 文件大小
     * @param mimetype 文件类型
     * @param fileExt 文件拓展名
     * @return ResponseResult
     */
    ResponseResult mergeChunks(String fileMd5,String fileName,Long fileSize,String mimetype,String fileExt);

    public ResponseResult sendProcessVideoMsg(String mediaId);
}
