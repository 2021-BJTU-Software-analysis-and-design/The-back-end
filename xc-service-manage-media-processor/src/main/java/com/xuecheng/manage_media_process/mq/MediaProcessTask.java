package com.xuecheng.manage_media_process.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.MediaFileProcess_m3u8;
import com.xuecheng.framework.utils.HlsVideoUtil;
import com.xuecheng.framework.utils.Mp4VideoUtil;
import com.xuecheng.manage_media_process.dao.MediaFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MediaProcessTask {
    //日志对象
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaProcessTask.class);

    //ffmpeg绝对路径
    @Value("${xc-service-manage-media.ffmpeg-path}")
    String ffmpeg_path;

    //上传文件根目录
    @Value("${xc-service-manage-media.video-location}")
    String serverPath;

    @Autowired
    MediaFileRepository mediaFileRepository;

    @RabbitListener(queues = "${xc-service-manage-media.mq.queue-media-video-processor}" , containerFactory="customContainerFactory")
    public void receiveMediaProcessTask(String msg){
        //将接收到的消息转换为json数据
        Map msgMap = JSON.parseObject(msg);
        LOGGER.info("receive media process task msg :{} ",msgMap);
        //解析消息
        //媒资文件id
        String mediaId = (String) msgMap.get("mediaId");
        //获取媒资文件信息
        Optional<MediaFile> byId = mediaFileRepository.findById(mediaId);
        if(!byId.isPresent()){
            return;
        }
        MediaFile mediaFile = byId.get();
        //媒资文件类型
        String fileType = mediaFile.getFileType();
        //目前只处理avi文件
        if(fileType == null || !fileType.equals("avi")){
            mediaFile.setProcessStatus("303004"); // 处理状态为无需处理
            mediaFileRepository.save(mediaFile);
        }else{
            mediaFile.setProcessStatus("303001"); //处理状态为未处理
        }
        //生成MP4
        String videoPath = serverPath + mediaFile.getFilePath() + mediaFile.getFileName();
        String mp4Name = mediaFile.getFileId() + ".mp4";
        String mp4FloderPath = serverPath  + mediaFile.getFilePath();
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpeg_path, videoPath, mp4Name, mp4FloderPath);
        String result = mp4VideoUtil.generateMp4();
        if(result == null || !result.equals("success")){
            //操作失败写入处理日志
            mediaFile.setProcessStatus("303003");//处理状态为处理失败
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setErrormsg(result);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            mediaFileRepository.save(mediaFile);
            return;
        }

        //生成m3u8列表
        //生成m3u8
        String mp4VideoPath = serverPath + mediaFile.getFilePath()+ mp4Name;//此地址为mp4的地址
        String m3u8Name = mediaFile.getFileId()+".m3u8";
        String m3u8FolderPath = serverPath + mediaFile.getFilePath()+"hls/";
        //调用工具类进行生成m3u8
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path, mp4VideoPath, m3u8Name, m3u8FolderPath);
        String m3u8Result = hlsVideoUtil.generateM3u8();

        if(m3u8Result==null || !m3u8Result.equals("success")){
            //操作失败写入处理日志
            mediaFile.setProcessStatus("303003");//处理状态为处理失败
            MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
            mediaFileProcess_m3u8.setErrormsg(m3u8Result);
            mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
            mediaFileRepository.save(mediaFile);
            return ;
        }

        //获取m3u8列表
        List<String> ts_list = hlsVideoUtil.get_ts_list();
        //更新处理状态为成功
        mediaFile.setProcessStatus("303002");//处理状态为处理成功
        MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
        mediaFileProcess_m3u8.setTslist(ts_list);
        mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
        //m3u8文件url
        mediaFile.setFileUrl(mediaFile.getFilePath()+"hls/"+m3u8Name);
        mediaFileRepository.save(mediaFile);
    }
}

