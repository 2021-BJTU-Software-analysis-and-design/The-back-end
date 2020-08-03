package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.TeachplanControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import com.xuecheng.manage_course.service.TeachplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class TeachplanController implements TeachplanControllerApi {
    @Autowired
    TeachplanService teachplanService;

//    @PreAuthorize("hasAuthority('course_teachplan_list')")
    @GetMapping("/teachplan/list/{courseId}")
    @Override
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId) {
        return teachplanService.findTeachplanList(courseId);
    }

    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan) {
        return teachplanService.addTeachplan(teachplan);
    }

    @Override
    @PostMapping("/savemedia")
    public ResponseResult saveTeachplanMedia(@RequestBody TeachplanMedia teachplanMedia) {
        return teachplanService.saveTeachplanMedia(teachplanMedia);
    }
}
