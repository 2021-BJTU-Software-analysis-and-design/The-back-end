package com.xuecheng.manage_course;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRibbon {
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testRibbon(){
        String serviceId = "xc-service-manage-cms";
        for (int i = 0; i < 10; i++) {
            ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://" + serviceId + "/cms/page/get/5a795ac7dd573c04508f3a56", Map.class);
            Map body = forEntity.getBody();
            System.out.println(body);
        }
    }
}
