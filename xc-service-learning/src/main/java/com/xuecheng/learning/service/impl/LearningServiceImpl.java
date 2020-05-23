package com.xuecheng.learning.service.impl;

import com.netflix.discovery.converters.Auto;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.framework.domain.learning.response.LearningCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.learning.client.CourseSearchClient;
import com.xuecheng.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LearningServiceImpl implements LearningService {

    @Autowired
    CourseSearchClient courseSearchClient;

    /**
     * 远程调用搜索服务获取已发布媒体信息中的url
     * @param courseId 课程id
     * @param teachplanId  媒体信息id
     * @return
     */
    @Override
    public GetMediaResult getMediaPlayUrl(String courseId, String teachplanId) {
        //校验学生权限,是否已付费等

        //远程调用搜索服务进行查询媒体信息
        TeachplanMediaPub mediaPub = courseSearchClient.getmedia(teachplanId);
        if(mediaPub == null || mediaPub.getMediaUrl() == null) ExceptionCast.cast(LearningCode.LEARNING_GET_MEDIA_ERROR);
        return new GetMediaResult(CommonCode.SUCCESS, mediaPub.getMediaUrl());
    }
}
