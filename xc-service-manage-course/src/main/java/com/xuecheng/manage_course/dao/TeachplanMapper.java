package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachplanMapper {
    public TeachplanNode selectList(String courseId);

    public void deletePlan(String id);

    public Teachplan selectPlan(String planid);

}
