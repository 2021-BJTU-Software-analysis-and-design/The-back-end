package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.utils.XcOauth2Util;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseContorller extends BaseController implements CourseControllerApi {
    @Autowired
    CourseService courseService;

    /**
     * 查询所有课程信息
     * @param page 页码
     * @param size 数量
     * @param courseListRequest 查询参数
     * @return QueryResponseResult
     */
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") int page, @PathVariable("size") int size, CourseListRequest courseListRequest) {
        return courseService.findCourseList(page,size,courseListRequest);
    }

    /**
     * 查询指定公司下的所有课程
     * @param page 页码
     * @param size 数量
     * @param courseListRequest 查询参数
     * @return QueryResponseResult
     */
    @GetMapping("/coursebase/company/list/{page}/{size}")
    @Override
    public QueryResponseResult findCourseListByCompany(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            CourseListRequest courseListRequest
    ){
        //调用工具类取出用户信息
        XcOauth2Util xcOauth2Util = new XcOauth2Util();
        XcOauth2Util.UserJwt userJwt = xcOauth2Util.getUserJwtFromHeader(request);
        String companyId = userJwt.getCompanyId();
        return courseService.findCourseListByCompany(companyId, page, size, courseListRequest);
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

    /**
     * 根据课程id获取该课程的课程图片信息
     * @param courseId
     * @return 由于这里每个课程只有一个图片，所以只返回一个 CoursePic 对象
     */
    @PreAuthorize("hasAuthority('course_pic_list')")
    @Override
    @GetMapping("/coursepic/get/{courseId}")
    public CoursePic getCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.getCoursePic(courseId);
    }

    /**
     * 删除课程图片信息
     * @param courseId
     * @return
     */

    @PreAuthorize("hasAuthority('course_pic_delete')")
    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    /**
     * 课程预览数据模型查询
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/preview/model/{id}")
    public CourseView courseView(@PathVariable("id") String courseId) {
        return courseService.getCourseView(courseId);
    }

    /**
     * 课程预览
     * @param courseId
     * @return
     */
    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult CoursePublishPreview(@PathVariable("id") String courseId) {
        return courseService.coursePublishPreview(courseId);
    }

    /**
     * 课程发布
     * @param courseId
     * @return
     */
    @Override
    @PostMapping("/publish/{id}")
    public CoursePublishResult CoursePublish(@PathVariable("id") String courseId) {
        return courseService.coursePublish(courseId);
    }

}
