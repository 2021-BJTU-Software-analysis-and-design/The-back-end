package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 课程计划管理相关的API接口
 */
@Api(value="课程管理API",description = "用于对课程的增删查改")
    public interface CourseControllerApi {
    @ApiOperation("分页查询课程列表1")
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("查询指定公司下的所有课程")
    public QueryResponseResult<CourseInfo> findCourseListByCompany(
            int page,
            int size,
            CourseListRequest courseListRequest
    );

    @ApiOperation("新增课程")
    public ResponseResult saveCourse(CourseBase courseBase);

    @ApiOperation("根据课程id查询课程信息")
    public CourseBase getCourseById(String courseId);

    @ApiOperation("更新课程信息")
    public ResponseResult updateCourse(String courseId,CourseBase courseBase);

    @ApiOperation("保存课程图片信息")
    public ResponseResult saveCoursePic(String courseId, String pic);

    @ApiOperation("获得课程图片信息")
    public CoursePic getCoursePic(String courseId);

    @ApiOperation("删除课程图片信息")
    public ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("课程视图查询")
    public CourseView courseView(String courseId);

    @ApiOperation("课程发布预览")
    public CoursePublishResult CoursePublishPreview(String courseId);

    @ApiOperation("课程发布")
    public CoursePublishResult CoursePublish(String courseId);

}