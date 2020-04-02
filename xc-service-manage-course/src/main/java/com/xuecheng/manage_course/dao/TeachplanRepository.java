package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    //定义方法根据课程id和父节点查询出节点列表，可以使用此方法实现查询根节点
    public List<Teachplan> findByCourseidAndParentid(String courseId,String parentId);
}
