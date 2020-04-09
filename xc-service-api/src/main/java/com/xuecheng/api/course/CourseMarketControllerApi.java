package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程营销信息查询", description = "对课程营销信息的增删查改")
public interface CourseMarketControllerApi {
    @ApiOperation("获取课程营销信息")
    CourseMarket findCourseMarketById(String id);

    @ApiOperation("更新课程营销信息")
    ResponseResult updateCourseMarket(String id,CourseMarket courseMarket);
}
