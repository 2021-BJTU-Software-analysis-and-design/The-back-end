package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseMarketControllerApi;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseMarketController implements CourseMarketControllerApi {
    @Autowired
    CourseMarketService courseMarketService;

    @GetMapping("/coursemarket/{id}")
    @Override
    public CourseMarket findCourseMarketById(@PathVariable("id") String id) {
        return courseMarketService.findCourseMarketById(id);
    }

    @PutMapping("/coursemarket/update/{id}")
    @Override
    public ResponseResult updateCourseMarket(@PathVariable("id") String id,@RequestBody CourseMarket courseMarket) {
        return courseMarketService.updateCourseMarket(id,courseMarket);
    }
}
