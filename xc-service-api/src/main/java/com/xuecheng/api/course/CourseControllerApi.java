package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 课程计划管理相关的API接口
 */
@Api(value="课程管理API",description = "用于对课程的增删查改")
public interface CourseControllerApi {
    @ApiOperation("分页查询课程列表1")
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

}
