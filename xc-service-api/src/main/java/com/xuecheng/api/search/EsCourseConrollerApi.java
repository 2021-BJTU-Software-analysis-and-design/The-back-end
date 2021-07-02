package com.xuecheng.api.search;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.course.TeachplanMediaPub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.Map;

@Api(value = "课程搜索", description = "基于ES构建的课程搜索API",tags = {"课程搜索"})
public interface EsCourseConrollerApi {
    @ApiOperation("课程详细搜索")
    public QueryResponseResult<CoursePub> findList(int page, int size, CourseSearchParam courseSearchParam) throws IOException;

    @ApiOperation("根据id搜索课程发布信息")
    public Map<String,CoursePub> getdetail(String id);

    @ApiOperation("根据课程计划查询媒资信息")
    public TeachplanMediaPub getmedia(String teachplanId);
}