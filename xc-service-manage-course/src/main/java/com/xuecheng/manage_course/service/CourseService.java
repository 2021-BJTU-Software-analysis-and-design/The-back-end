package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.CoursePicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    CourseMapper courseMapper;

    /**
     * 分页查询课程信息
     */
    public QueryResponseResult findCourseList(int pageNum, int size, CourseListRequest courseListRequest){
        if(pageNum<=0){
            pageNum = 0;
        }
        if(size<=0){
            size = 20;
        }
        PageHelper.startPage(pageNum,size);  //设置分页参数
        Page<CourseBase> courseList = courseMapper.findCourseList(courseListRequest);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(courseList.getResult());
        queryResult.setTotal(courseList.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }
    /**
     * 根绝课程id查询课程信息
     */
    public CourseBase getCourseById(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_NOTEXIST);
        }

        CourseBase courseBase = optional.get();
        return courseBase;
    }

    /**
     * 添加课程
     * @param courseBase
     * @return
     */
    public ResponseResult saveCourse(CourseBase courseBase){

        //校验数据唯一性，name
        CourseBase byName = courseBaseRepository.findByName(courseBase.getName());
        if(byName!= null){
            ExceptionCast.cast(CourseCode.COURSE_NAME_ISEXIST);
        }

        CourseBase save = courseBaseRepository.save(courseBase);

        if(save == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        return new ResponseResult(CommonCode.SUCCESS);
    }

    public ResponseResult updateCourse(String courseId,CourseBase courseBase) {
        //先校验这个id是否存在
        Optional<CourseBase> byId = courseBaseRepository.findById(courseId);
        if(!byId.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_NOTEXIST);
        }

        CourseBase save = courseBaseRepository.save(courseBase);
        if(save == null){
            return new ResponseResult(CommonCode.FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 保存课程图片信息到数据库
     * @param courseId 课程id
     * @param pic 图片id
     * @return
     */
    public ResponseResult saveCoursePic(String courseId, String pic) {
        CoursePic coursePic = null;
        //判断该课程id是否已经存在图片
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        if(byId.isPresent()){
            coursePic = byId.get();
        }
        //不存在则重新创建一个课程图片对象并保存信息
        if(coursePic == null){
            coursePic = new CoursePic();
        }

        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        CoursePic save = coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
