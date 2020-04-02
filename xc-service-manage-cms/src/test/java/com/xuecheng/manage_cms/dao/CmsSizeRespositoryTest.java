package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.SiteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsSizeRespositoryTest {
    @Autowired
    SiteService siteService;

    /**
     * 测试查询所有站点数据
     */
    @Test
    public void testFindAll(){
        QueryResponseResult list = siteService.findList();
        System.out.println(list);
    }
}
