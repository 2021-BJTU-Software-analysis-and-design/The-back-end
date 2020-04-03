package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.CategoryNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "分类查询API",description = "用于查询课程分类")
public interface CategoryControllerApi {

    @ApiOperation("查询分类")
    public CategoryNode findList();
}
