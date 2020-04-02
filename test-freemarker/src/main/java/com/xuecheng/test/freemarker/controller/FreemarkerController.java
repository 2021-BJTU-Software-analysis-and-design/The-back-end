package com.xuecheng.test.freemarker.controller;
import com.xuecheng.test.freemarker.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/freemarker")
@Controller  //这里主要不要使用RestController,RestController默认返回json数据,使模板引擎失效
public class FreemarkerController {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 用于调试banner模板
     */
    @RequestMapping("/banner")
    public String indexBanner(Map<String,Object> map){
        String dataUrl = "http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        map.putAll(body); //将获取到的数据赋值给map，并渲染至模板内
        return "index-banner";
    }

    /**
     * 测试1
     * @param map freemarker引擎会自动从Map形参中读取变量进行渲染
     * @return
     */
    @RequestMapping("/test1")
    public String freemarker(Map<String,Object> map){
        map.put("name", "黑马程序员");
        //学生对象1
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1888.123f);
        stu1.setBirthday(new Date());
        //学生对象2
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setAge(22);
        stu2.setMoney(1888.123f);
        stu2.setBirthday(new Date());
        //创建一个List对象，用于储存上面这两个学生对象
        ArrayList<Object> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        //向数据模型中放入List
        map.put("stus",stus);

        //准备map数据
        HashMap<Object, Object> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        //像数据模型内放数据
        map.put("stu1",stu1);
        //向数据模型放入stuMap
        map.put("stuMap", stuMap);

        //返回模板文件名称
        return "test1";
    }
}
