package com.xuecheng.manage_media.controller;

import com.xuecheng.api.media.MediaUploadControllerApi;
import com.xuecheng.framework.domain.media.response.CheckChunkResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_media.service.MediaUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media/upload")
public class MediaUploadController implements MediaUploadControllerApi {

    @Autowired
    MediaUploadService mediaUploadService;

    @PostMapping("/register")
    @Override
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        return mediaUploadService.register(fileMd5,fileName,fileSize,mimetype,fileExt);
    }

    @PostMapping("/checkchunk")
    @Override
    public CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize) {
        return mediaUploadService.checkChunk(fileMd5,chunk,chunkSize);
    }

    @PostMapping("/uploadchunk")
    @Override
    public ResponseResult uploadChunk(MultipartFile file, Integer chunk, String fileMd5) {
            return mediaUploadService.uploadChunk(file, chunk, fileMd5);
    }

    @PostMapping("/mergechunks")
    @Override
    public ResponseResult mergeChunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {
        return mediaUploadService.mergeChunks(fileMd5,fileName,fileSize,mimetype,fileExt);
    }

    @Override
    @GetMapping("/process/{mediaId}")
    public ResponseResult process(@PathVariable("mediaId") String mediaId) {
        return mediaUploadService.sendProcessVideoMsg(mediaId);
    }
}
