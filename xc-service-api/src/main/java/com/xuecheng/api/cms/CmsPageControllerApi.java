package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="cms页面管理接口",description = "cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    /**
     * 分页查询接口
     */
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
        @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     * 添加页面数据
     */
    @ApiOperation("添加页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cmsPage",value = "请提交json形式的页面数据",required=true,paramType="CmsPage",dataType="CmsPage"),
    })
    public CmsPageResult addCmsPage(CmsPage cmsPage);

    /**
     * 保存页面数据
     */
    @ApiOperation("保存页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cmsPage",value = "请提交json形式的页面数据",required=true,paramType="CmsPage",dataType="CmsPage"),
    })
    public CmsPageResult saveCmsPage(CmsPage cmsPage);


    /**
     * 通过ID查询页面
     */
    @ApiOperation("通过ID查询页面")
    public CmsPageResult findById(String id);


    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    @ApiOperation("修改页面")
    public CmsPageResult update(String id, CmsPage cmsPage);

    /**
     * 删除接口
     * @param id 页面id
     * @return
     */
    @ApiOperation("删除页面")
    public ResponseResult delete(String id);

    /**
     * 页面发布接口
     */
    @ApiOperation("发布页面")
    public ResponseResult post(String pageId);

    /**
     * 一键发布页面
     */
    @ApiOperation("一键发布页面")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
