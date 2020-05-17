package com.xuecheng.manage_media.service.impl;

import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import com.xuecheng.manage_media.service.MediaFileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MediaFileServiceImpl implements MediaFileService {

    private static Logger logger = LoggerFactory.getLogger(MediaFileService.class);

    @Autowired
    MediaFileRepository mediaFileRepository;

    /**
     * 分页查询文件信息
     * @param page 页码
     * @param size 每页数量
     * @param queryMediaFileRequest 查询条件
     * @return QueryResponseResult
     */
    @Override
    public QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        MediaFile mediaFile = new MediaFile();
        //查询条件
        if(queryMediaFileRequest == null){
            queryMediaFileRequest = new QueryMediaFileRequest();
        }

        //查询条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains()) //模糊匹配
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains()) //模糊匹配文件原始名称
                .withMatcher("processStatus", ExampleMatcher.GenericPropertyMatchers.exact());//精确匹配
        //设置查询条件对象
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getTag())){
            //设置标签
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())){
            //设置文件原始名称
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())){
            //设置处理状态
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }

        //定义Example实例
        Example<MediaFile> example = Example.of(mediaFile, exampleMatcher);

        //校验page和size参数的合法性,并设置默认值
        if(page <=0){
            page = 0;
        }else{
            page = page -1;
        }
        if(size <=0){
            size = 10;
        }

        //分页对象
        PageRequest pageRequest = new PageRequest(page, size);
        //分页查询
        Page<MediaFile> all = mediaFileRepository.findAll(example, pageRequest);
        //设置响应对象属性
        QueryResult<MediaFile> mediaFileQueryResult = new QueryResult<MediaFile>();
        mediaFileQueryResult.setList(all.getContent());
        mediaFileQueryResult.setTotal(all.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS,mediaFileQueryResult);
    }
}
