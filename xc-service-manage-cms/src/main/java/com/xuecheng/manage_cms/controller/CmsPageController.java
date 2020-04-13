package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    PageService pageService;

    /**
     * 分页查询数据
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page,size,queryPageRequest);
    }

    /**
     * 添加页面
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/add")
    public CmsPageResult addCmsPage(@RequestBody CmsPage cmsPage) {
        return pageService.addCmsPage(cmsPage);
    }

    /**
     * 保存页面
     * @param cmsPage
     * @return
     */
    @Override
    @PostMapping("/save")
    public CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage) {
        return pageService.saveCmsPage(cmsPage);
    }

    /**
     * 根据id查询页面数据
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @Override
    public CmsPageResult findById(@PathVariable("id") String id) {
        return pageService.cmsPageQueryById(id);
    }

    /**
     * 更新数据
     * @param id
     * @param cmsPage
     * @return
     */
    @PutMapping("/update/{id}")
    @Override
    public CmsPageResult update(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        CmsPageResult cmsPageResult = pageService.updateCmsPage(id, cmsPage);
        return cmsPageResult;
    }

    /**
     * 删除页面
     * @param id 页面id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseResult delete(@PathVariable("id") String id) {
        return pageService.deleteCmsPage(id);
    }

    /**
     * 发布页面
     * @param pageId
     * @return
     */
    @Override
    @PostMapping("/postPage/{pageId}")
    public ResponseResult post(@PathVariable("pageId") String pageId) {
        return pageService.postPage(pageId);
    }
}
