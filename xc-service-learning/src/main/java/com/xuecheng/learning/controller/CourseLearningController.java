package com.xuecheng.learning.controller;

import com.xuecheng.api.course.CourseLearningControllerApi;
import com.xuecheng.framework.domain.learning.response.GetMediaResult;
import com.xuecheng.learning.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning/course")
public class CourseLearningController implements CourseLearningControllerApi {
    @Autowired
    LearningService learningService;

    @GetMapping("/getmedia/{courseId}/{teachplanId}")
    @Override
    public GetMediaResult getMediaPlayUrl(@PathVariable("courseId") String courseId, @PathVariable("teachplanId") String teachplanId) {
        return learningService.getMediaPlayUrl(courseId, teachplanId);
    }
}
