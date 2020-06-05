package com.xuecheng.learning.service;

import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.model.response.ResponseResult;

import java.util.Date;

public interface LearningService {
    /**
     * 远程调用搜索服务获取已发布媒体信息中的url
     * @param courseId 课程id
     * @param teachplanId  媒体信息id
     * @return GetMediaResult
     */
    GetMediaResult getMediaPlayUrl(String courseId, String teachplanId);

    /**
     * 添加选课
     * @param userId 用户id
     * @param courseId 课程id
     * @param valid 课程有效性,标识该课程是否已经到期
     * @param startTime 选课开始时间
     * @param endTime 结束时间
     * @param xcTask 具体课程信息
     * @return ResponseResult
     */
    ResponseResult addChooseCourse(String userId, String courseId, String valid, Date startTime, Date endTime, XcTask xcTask);
}
