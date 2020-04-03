package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 课程管理相关的API接口
 */
@Api(value="课程管理API",description = "用于对课程计划的增删查改")
public interface CourseControllerApi {


    @ApiOperation("分页查询课程列表1")
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("课程计划查询1")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划1")
    public ResponseResult addTeachplan(Teachplan teachplan);

}
