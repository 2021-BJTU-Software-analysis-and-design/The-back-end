package com.xuecheng.ucenter.controller;

import com.xuecheng.api.ucenter.UcenterControllerApi;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.framework.domain.ucenter.request.RegisterRequest;
import com.xuecheng.framework.domain.ucenter.response.UcenterCode;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi {
    @Autowired
    UserService userService;

    @GetMapping("/getuserext")
    @Override
    public XcUserExt getUserext(String username) {
        XcUserExt xcUserExt = userService.getUserExt(username);
        return xcUserExt;
    }

    @PostMapping("/register")
    public UcenterCode save(RegisterRequest registerRequest){
        UcenterCode code = userService.save(registerRequest);
        return code;
    }
}
