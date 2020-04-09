package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseMarketService {
   @Autowired
   CourseMarketRepository courseMarketRepository;

   public CourseMarket findCourseMarketById(String id){
       Optional<CourseMarket> byId = courseMarketRepository.findById(id);
       if(!byId.isPresent()){
           return null;
       }

       CourseMarket courseMarket = byId.get();
       return courseMarket;
   }

   public ResponseResult updateCourseMarket(String id,CourseMarket courseMarket){
       CourseMarket courseMarketById = this.findCourseMarketById(id);
       if(courseMarketById == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
       }

       CourseMarket save = courseMarketRepository.save(courseMarket);
       return new ResponseResult(CommonCode.SUCCESS);
   }
}
