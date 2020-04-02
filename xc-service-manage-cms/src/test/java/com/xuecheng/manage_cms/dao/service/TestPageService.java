package com.xuecheng.manage_cms.dao.service;

import com.xuecheng.manage_cms.service.PageService;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPageService {
    @Autowired
    PageService pageService;

    @Test
    public void testGetPageHtml() throws IOException, TemplateException {
        String pageHtml = pageService.getPageHtml("5e7a251a3304a252280804dc");
        System.out.println(pageHtml);
    }
}
