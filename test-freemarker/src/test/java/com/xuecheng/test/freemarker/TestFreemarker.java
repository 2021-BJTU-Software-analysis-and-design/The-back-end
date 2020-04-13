package com.xuecheng.test.freemarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFreemarker {

    @Autowired
    RestTemplate restTemplate;

    //基于模板生成静态文件
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板路径
        String classPath = this.getClass().getResource("/").getPath();
        classPath = java.net.URLDecoder.decode(classPath,"utf-8"); //路径包含中文需要进行转码
        configuration.setDirectoryForTemplateLoading(new File(classPath +"/templates"));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //加载模板
        Template template = configuration.getTemplate("test1.ftl");
        //数据模型
        Map map = getMap();
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //静态化内容
        InputStream inputStream = IOUtils.toInputStream(content);
        //输出到文件
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/test1.html"));
        int copy = IOUtils.copy(inputStream, fileOutputStream);
    }

    public Map getMap(){
        Map<String,Object> map = new HashMap<>();
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
        return map;
    }

}
