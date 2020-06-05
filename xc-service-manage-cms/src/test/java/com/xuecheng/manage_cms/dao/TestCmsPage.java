package com.xuecheng.manage_cms.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCmsPage {

    @Test
    public void cmsNameToCourseId(){
        String cmsName = "asdasdasdas.html";
        String[] split = cmsName.split("\\.");
        for(int i=0,len=split.length;i<len;i++){
            System.out.println(split[i].toString());
        }
    }
}
