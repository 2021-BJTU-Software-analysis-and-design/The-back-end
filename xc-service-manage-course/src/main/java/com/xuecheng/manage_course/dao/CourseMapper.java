package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   //根据课程id查询课程信息
   CourseBase findCourseBaseById(String id);

   //分页查询个人信息
   Page<CourseInfo> findCourseList(CourseListRequest courseListRequest);

}
