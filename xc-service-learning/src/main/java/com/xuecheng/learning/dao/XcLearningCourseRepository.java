package com.xuecheng.learning.dao;

import com.xuecheng.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XcLearningCourseRepository  extends JpaRepository<XcLearningCourse,String> {

    //根据用户id和课程id来判断用户是否已选课
    XcLearningCourse findXcLearningCourseByUserIdAndCourseId(String userId, String courseId);
}
