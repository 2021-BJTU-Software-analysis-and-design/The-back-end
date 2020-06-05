package com.xuecheng.manage_cms.client;

import com.xuecheng.framework.domain.course.ext.CourseView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "XC-SERVICE-MANAGE-COURSE")
public interface CourseManageClient {
    /**
     * 远程调用课程管理服务的数据模型接口
     * @param courseId
     * @return
     */
    @GetMapping("/course/preview/model/{id}")
    CourseView courseView(@PathVariable("id") String courseId);
}
