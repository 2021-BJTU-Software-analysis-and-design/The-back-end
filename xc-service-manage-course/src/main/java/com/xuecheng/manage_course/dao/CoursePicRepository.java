package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator.
 */
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    /**
     * 根据课程id删除图片信息
     * @param courseId
     * @return 返回删除影响的行,小于1则表示删除失败
     */
    long deleteByCourseid(String courseId);
}
