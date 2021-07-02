package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.TemplateService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/cms/template")
public class CmsTemplateController implements CmsTemplateControllerApi {
    @Autowired
    TemplateService templateService;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @GetMapping("/list")
    @Override
    public QueryResponseResult findList() {
        QueryResponseResult queryResponseResult = templateService.findList();
        return queryResponseResult;
    }

    @GetMapping("/save")
    public String save() throws FileNotFoundException {
        System.out.println("ENTER");
        File file = new File("D:\\java_program\\xuecheng-project-services\\course.ftl");
        //定义输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //向GridFS存储文件
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "courseTemplate");
        //得到文件ID
        String fileId = objectId.toString();
        System.out.println(fileId);

        return fileId;
    }
}
