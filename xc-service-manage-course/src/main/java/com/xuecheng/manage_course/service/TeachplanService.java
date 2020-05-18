package com.xuecheng.manage_course.service;


import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanMediaRepository;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanMediaRepository teachplanMediaRepositoy;

    @Autowired
    TeachplanRepository teachplanRepository;

    @Autowired
    CourseBaseRepository courseBaseRepository;

    /**
     * 添加课程计划
     *
     * @param teachplan
     * @return
     */
    @Transactional  //增删改操作都需要加spring事务
    public ResponseResult addTeachplan(Teachplan teachplan) {
        //校验课程id和课程计划名称
        if (teachplan == null || StringUtils.isEmpty(teachplan.getCourseid()) || StringUtils.isEmpty(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //取出课程id
        String courseId = teachplan.getCourseid();
        //取出父节点id
        String parentId = teachplan.getParentid();
        String newTeachplanGrade = "3";  //设置
        // 如果用户未在添加时候未选择根节点,表示需要添加的是一个二级菜单，并且默认添加到当前课程的根节点下
        if (StringUtils.isEmpty(parentId)) {
            newTeachplanGrade = "2";
            //根据课程id获取课程根节点id，如果根节点不存在 则创建一个根节点，并作为该课程的根节点d取根节点的id,
            parentId = this.getTeachplanRoot(courseId);
            if (StringUtils.isEmpty(parentId)) {
                ExceptionCast.cast(CommonCode.INVALID_PARAM);
            }
        }

        //创建新节点
        Teachplan teachplanNew = new Teachplan();
        //将传入的节点信息赋值到新节点内
        BeanUtils.copyProperties(teachplan, teachplanNew);
        teachplanNew.setParentid(parentId);
        teachplanNew.setCourseid(courseId);

        /* 设置新节点的级别
            方法1：根据父节点的级别进行设置，父节点级别为1，当前则为2，为2则当前为3
            方法2：在判断前端传入的父节点是否为空时进行设置，如果为空，表示需求为添加二级节点，设置2
                  如果不为空，则表示要添加的是三级节点，设置为3，与方法1相比可以减少一次查询。
         */
        teachplanNew.setGrade(newTeachplanGrade); //节点级别，根据父节点的级别进行设置
        teachplanRepository.save(teachplanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //根据课程id获取课程根节点id，如果根节点不存在 则创建一个根节点，并作为该课程的根节点
    private String getTeachplanRoot(String courseId) {
        //校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()) {
            return null;
        }
        CourseBase courseBase = optional.get();
        //取出一级根节点菜单
        List<Teachplan> teachplanList = teachplanRepository.findByCourseidAndParentid(courseId, "0");

        //如果根节点不存在，则新增一个节点，并且作为该课程的根节点
        if (teachplanList == null || teachplanList.size() == 0) {
            //新增一个节点
            Teachplan teachplanNewRoot = new Teachplan();
            teachplanNewRoot.setCourseid(courseId);
            teachplanNewRoot.setPname(courseBase.getName());
            teachplanNewRoot.setCourseid(courseId);
            teachplanNewRoot.setGrade("1"); //1级菜单
            teachplanNewRoot.setParentid("0"); //设置父节点id
            teachplanNewRoot.setStatus("0"); //未发布
            teachplanRepository.save(teachplanNewRoot);
            return teachplanNewRoot.getId();
        }
        Teachplan teachplan = teachplanList.get(0);
        return teachplan.getId();
    }

    /**
     * 查询课程计划
     *
     * @param courseId
     * @return
     */
    public TeachplanNode findTeachplanList(String courseId) {
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        return teachplanNode;
    }

    /**
     * 保存课程计划的媒体信息
     * @param teachplanMedia
     * @return
     */
    public ResponseResult saveTeachplanMedia(TeachplanMedia teachplanMedia) {
        if(teachplanMedia == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        //验证该课程计划是否存在
        String teachplanId = teachplanMedia.getTeachplanId();
        Optional<Teachplan> byId = teachplanRepository.findById(teachplanId);
        if(!byId.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_TEACHPLANISNULL);
        }

        //只允许为第三级的课程,也就是叶子节点课程选择视频
        Teachplan teachplan = byId.get();
        String grade = teachplan.getGrade();
        if(StringUtils.isEmpty(grade) || !grade.equals("3")){
            ExceptionCast.cast(CourseCode.COURSE_MEDIS_TEACHPLAN_GREAD_ERROR);
        }

        //查询该课程计划下的媒资信息
        TeachplanMedia mediaOne = null;
        Optional<TeachplanMedia> mediaById = teachplanMediaRepositoy.findById(teachplanId);
        if(!mediaById.isPresent()){  //查询不到则新建一个的对象,如果查询到了就直接获取
            mediaOne = new TeachplanMedia();
        }else{
            mediaOne = mediaById.get();
        }

        //保存媒体信息与课程计划信息
        //BeanUtils.copyProperties(teachplanMedia,mediaOne);
        mediaOne.setCourseId(teachplanMedia.getCourseId());
        mediaOne.setTeachplanId(teachplanId);
        mediaOne.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        mediaOne.setMediaId(teachplanMedia.getMediaId());
        mediaOne.setMediaId(teachplanMedia.getMediaId());
        mediaOne.setMediaUrl(teachplanMedia.getMediaUrl());
        teachplanMediaRepositoy.save(mediaOne);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
