package com.xuecheng.learning.service;

import com.xuecheng.framework.domain.learning.response.GetMediaResult;

public interface LearningService {
    /**
     * 远程调用搜索服务获取已发布媒体信息中的url
     * @param courseId 课程id
     * @param teachplanId  媒体信息id
     * @return GetMediaResult
     */
    GetMediaResult getMediaPlayUrl(String courseId, String teachplanId);
}
