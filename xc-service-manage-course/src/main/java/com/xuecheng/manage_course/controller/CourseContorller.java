package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseContorller implements CourseControllerApi {
    @Autowired
    CourseService courseService;

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        return courseService.findCourseList(page,size,courseListRequest);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult saveCourse(@RequestBody CourseBase courseBase) {
        return courseService.saveCourse(courseBase);
    }

    @Override
    @GetMapping("/coursebase/{id}")
    public CourseBase getCourseById(@PathVariable("id") String courseId) {
        return courseService.getCourseById(courseId);
    }

    @Override
    @PutMapping("/coursebase/update/{id}")
    public ResponseResult updateCourse(@PathVariable("id") String courseId, @RequestBody CourseBase courseBase) {
        return courseService.updateCourse(courseId, courseBase);
    }

    /**
     * 保存课程信息与图片的对应关系
     * @param courseId 课程id
     * @param pic 图片文件id
     * @return
     */
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult saveCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        return courseService.saveCoursePic(courseId,pic);
    }
}
