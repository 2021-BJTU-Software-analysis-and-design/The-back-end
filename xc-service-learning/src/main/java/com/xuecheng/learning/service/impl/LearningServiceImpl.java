package com.xuecheng.learning.service.impl;

import com.netflix.discovery.converters.Auto;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.learning.XcLearningCourse;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.framework.domain.learning.response.LearningCode;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.learning.client.CourseSearchClient;
import com.xuecheng.learning.dao.XcLearningCourseRepository;
import com.xuecheng.learning.dao.XcTaskHisRepository;
import com.xuecheng.learning.service.LearningService;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;


@Service
public class LearningServiceImpl implements LearningService {

    @Autowired
    CourseSearchClient courseSearchClient;

    @Autowired
    XcLearningCourseRepository xcLearningCourseRepository;

    @Autowired
    XcTaskHisRepository xcTaskHisRepository;

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

    /**
     * 为用户添加选课信息的实现
     * @param userId 用户id
     * @param courseId 课程id
     * @param valid 课程有效性,标识该课程是否已经到期
     * @param startTime 选课开始时间
     * @param endTime 结束时间
     * @param xcTask 具体课程信息
     * @return
     */
    @Transactional
    @Override
    public ResponseResult addChooseCourse(String userId, String courseId, String valid, Date startTime, Date endTime, XcTask xcTask) {
        //空指针判断
        if (StringUtils.isEmpty(courseId)) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_COURSEID_ISNULL);
        }
        if (StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_USERID_ISNULL);
        }
        if(xcTask == null || StringUtils.isEmpty(xcTask.getId())){
            ExceptionCast.cast(LearningCode.CHOOSECOURSE_TASKID_ISNULL);
        }
        //如果查询到任务记录,则表示该任务已经完成,返回成功标识
        Optional<XcTaskHis> optional = xcTaskHisRepository.findById(xcTask.getId());
        if(optional.isPresent()){
            return new ResponseResult(CommonCode.SUCCESS);
        }

        //查询是否已经添加了选课
        XcLearningCourse byUserIdAndCourseId = xcLearningCourseRepository.findXcLearningCourseByUserIdAndCourseId(userId, courseId);
        if ((byUserIdAndCourseId == null)) {
            byUserIdAndCourseId = new XcLearningCourse();
            byUserIdAndCourseId.setUserId(userId);
            byUserIdAndCourseId.setCourseId(courseId);
            byUserIdAndCourseId.setValid(valid);
            byUserIdAndCourseId.setStartTime(startTime);
            byUserIdAndCourseId.setEndTime(endTime);
            byUserIdAndCourseId.setStatus("501001");
        }else{
            //该课程如果已经选过,则更新该课程的信息
            byUserIdAndCourseId.setValid(valid);
            byUserIdAndCourseId.setStartTime(startTime);
            byUserIdAndCourseId.setEndTime(endTime);
            byUserIdAndCourseId.setStatus("501001");
        }

        xcLearningCourseRepository.save(byUserIdAndCourseId);
        //向历史任务表播入记录
        Optional<XcTaskHis> optionalXcTaskHis = xcTaskHisRepository.findById(xcTask.getId());
        if(!optionalXcTaskHis.isPresent()){
            //添加历史任务
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            xcTaskHisRepository.save(xcTaskHis);
        }

        return new ResponseResult(CommonCode.SUCCESS);
    }
}
