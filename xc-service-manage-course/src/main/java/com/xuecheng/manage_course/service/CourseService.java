package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CourseMarket;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseView;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.CourseCode;
import com.xuecheng.framework.domain.course.response.CoursePublishResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.client.CmsPageClient;
import com.xuecheng.manage_course.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CourseService {

    //从配置文件获取课程发布的基本配置
    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    CourseMarketService courseMarketService;
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CmsPageClient cmsPageClient;

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
    @Transactional  //Mysql操作需要添加到Spring事务
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

    /**
     * 根据课程id获得课程的图片信息
     * @param courseId
     * @return
     */
    @Transactional //Mysql操作需要添加到Spring事务
    public CoursePic getCoursePic(String courseId) {
        Optional<CoursePic> byId = coursePicRepository.findById(courseId);
        if(byId.isPresent()){
            CoursePic coursePic = byId.get();
            return coursePic;
        }
        return null;
    }

    /**
     * 删除课程图片信息
     * @param courseId
     * @return
     */
    @Transactional //Mysql操作需要添加到Spring事务
    public ResponseResult deleteCoursePic(String courseId) {
        long byCourseid = coursePicRepository.deleteByCourseid(courseId);
        if(byCourseid > 0){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 获取课程视图数据模型
     * @param courseId
     * @return
     */
    public CourseView getCourseView(String courseId) {
        CourseView courseView = new CourseView();
        //获取课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if(courseBaseOptional.isPresent()){
            CourseBase courseBase = courseBaseOptional.get();
            courseView.setCourseBase(courseBase);
        }

        //获取课程营销信息
        CourseMarket courseMarketById = courseMarketService.findCourseMarketById(courseId);
        courseView.setCourseMarket(courseMarketById);

        //获取课程图片
        Optional<CoursePic> coursePicOptional = coursePicRepository.findById(courseId);
        if(coursePicOptional.isPresent()){
            CoursePic coursePic = coursePicOptional.get();
            courseView.setCoursePic(coursePic);
        }

        //获取课程计划
        TeachplanNode teachplanNode = teachplanMapper.selectList(courseId);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    //根据id查询课程基本信息
    public CourseBase findCourseBaseById(String courseId){
        //获取课程信息
        Optional<CourseBase> optionalCourseBase = courseBaseRepository.findById(courseId);
        if(!optionalCourseBase.isPresent()){
            //课程不存在抛出异常
            ExceptionCast.cast(CourseCode.COURSE_NOTEXIST);
            return null;
        }
        return optionalCourseBase.get();
    }
    /**
     * 课程详细页面发布前预览
     * @param courseId
     * @return
     */
    @Transactional
    public CoursePublishResult coursePublishPreview(String courseId) {
        //拼装页面信息
        CmsPage cmsPage = setPageInfo(courseId);

        //远程调用，保存页面信息
        CmsPageResult cmsPageResult = cmsPageClient.saveCmsPage(cmsPage);
        if(!cmsPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        //页面id
        String cmsPageId = cmsPageResult.getCmsPage().getPageId();
        //返回预览url
        String url = previewUrl + cmsPageId;
        return new CoursePublishResult(CommonCode.SUCCESS,url);
    }

    /**
     * 课程详细页面发布
     */
    @Transactional
    public CoursePublishResult coursePublish(String courseId){
        //拼装页面信息
        CmsPage cmsPage = setPageInfo(courseId);

        //发布课程详细页面
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if(!cmsPostPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }

        //更新课程状态
        CourseBase courseBaseById = this.findCourseBaseById(courseId);
        courseBaseById.setStatus("202002");
        courseBaseRepository.save(courseBaseById);

        //课程索引...

        //课程缓存...

        //页面url
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }


    //拼装页面信息
    private CmsPage setPageInfo(String courseId){
        //获取课程信息
        CourseBase courseBaseById = this.findCourseBaseById(courseId);

        //拼装页面基本信息
        CmsPage cmsPage = new CmsPage();
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        cmsPage.setPageWebPath(publish_page_webpath);
        cmsPage.setSiteId(publish_siteId);
        cmsPage.setTemplateId(publish_templateId);
        cmsPage.setPageName(courseId + ".html");

        //页面别名
        cmsPage.setPageAliase(courseBaseById.getName());
        return cmsPage;
    }
}
