package com.xuecheng.search.controller;

import com.xuecheng.api.search.EsCourseConrollerApi;
import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseConrollerApi {
    @Autowired
    EsCourseService esCourseService;


    /**
     * 课程信息搜索
     * @param page 页码
     * @param size 每页数量
     * @param courseSearchParam 搜索参数
     * @return
     * @throws IOException
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CoursePub> findList(@PathVariable("page") int page,@PathVariable("size") int size, CourseSearchParam courseSearchParam) throws IOException {
        return esCourseService.findList(page,size,courseSearchParam);
    }

    /**
     * 根据id搜索课程发布信息
     * @param id 课程id
     * @return JSON数据
     */
    @Override
    @GetMapping("/getdetail/{id}")
    public Map<String, CoursePub> getdetail(@PathVariable("id")String id) {
        return esCourseService.getdetail(id);
    }
}
