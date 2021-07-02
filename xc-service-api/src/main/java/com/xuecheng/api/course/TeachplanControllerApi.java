package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 课程计划管理相关的API接口
 */
@Api(value="课程计划管理API",description = "用于对课程计划的增删查改")
public interface TeachplanControllerApi {

    //课程计划相关操作
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    public ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("保存媒资信息")
    public ResponseResult saveTeachplanMedia(TeachplanMedia teachplanMedia);

    @ApiOperation("删除课程计划")
    public ResponseResult deleteTeachplan(String id);

}
