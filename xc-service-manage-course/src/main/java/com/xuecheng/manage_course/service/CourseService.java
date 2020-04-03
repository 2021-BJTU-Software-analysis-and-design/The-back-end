package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;

    /**
     * 分页查询课程信息
     */
    public QueryResponseResult findCourseList(int pageNum, int size, CourseListRequest courseListRequest){
        PageHelper.startPage(pageNum,size);  //设置分页参数
        Page<CourseBase> courseList = courseMapper.findCourseList(courseListRequest);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(courseList.getResult());
        queryResult.setTotal(courseList.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
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
}
