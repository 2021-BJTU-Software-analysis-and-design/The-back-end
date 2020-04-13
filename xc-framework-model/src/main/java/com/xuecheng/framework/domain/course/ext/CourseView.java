package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

//Serializable ?
@Data
public class CourseView implements Serializable {
    CourseBase courseBase; //课程基本信息
    CourseMarket courseMarket; //课程营销信息
    CoursePic coursePic;  //课程图片
    TeachplanNode teachplanNode; //课程营销计划
}
