package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;


    //查询所有
    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    //自定义条件查询
    @Test
    public void testDiyFindAll(){
        //精确匹配条件值
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setTemplateId("5a925be7b00ffc4b3c1578b5");
        cmsPage.setPageAliase("预览");

        //条件匹配器,用于模糊匹配
        ExampleMatcher matching = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        //条件查询实例
        Example<CmsPage> example = Example.of(cmsPage, matching);

        //分页参数
        int page = 0;
        int size = 24;
        Pageable pageable = PageRequest.of(page,size);

        //调用dao
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    //分页查询
    @Test
    public void testFindPage(){
        //分页参数
        int page = 0; //从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    //添加
    @Test
    public void testInsert(){
        //定义实体类
        CmsPage cmsPage = new CmsPage();
        //设置属性
        cmsPage.setSiteId("s01");
        cmsPage.setTemplateId("t01");
        cmsPage.setPageName("测试页面");
        cmsPage.setPageCreateTime(new Date());
        //参数集合
        List<CmsPageParam> cmsPageParams = new ArrayList<>();
        CmsPageParam cmsPageParam = new CmsPageParam();
        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");
        //将参数添加至集合内
        cmsPageParams.add(cmsPageParam);
        //实体添加参数集
        cmsPage.setPageParams(cmsPageParams);
        cmsPageRepository.save(cmsPage);
    }

    //修改
    @Test
    public void testUpdate(){
        Optional<CmsPage> optional = cmsPageRepository.findById("5e705470cc53e4266c135ee7");
        if(optional.isPresent()){  //判断是否为空，jdk1.8新特性optional
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面02");
            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println("修改成功 " + save);
        }
    }

    //删除
    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("5e705470cc53e4266c135ee7");
        System.out.println("删除成功!");
    }
}
