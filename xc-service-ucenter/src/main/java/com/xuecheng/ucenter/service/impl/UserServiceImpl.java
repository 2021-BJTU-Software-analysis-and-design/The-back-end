package com.xuecheng.ucenter.service.impl;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.XcUserRole;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.framework.domain.ucenter.request.RegisterRequest;
import com.xuecheng.framework.domain.ucenter.response.UcenterCode;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import com.xuecheng.ucenter.dao.XcUserRepository;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    //对xc_user表的相关操作
    @Autowired
    XcUserRepository xcUserRepository;
    //对用户权限表操作的mapper
    @Autowired
    XcMenuMapper xcMenuMapper;

    //对xc_company_user表的相关操作
    @Autowired
    XcCompanyUserRepository xcCompanyUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 根据用户名查询用户信息的实现
     * @param username
     * @return
     */
    @Override
    public XcUser findXcUserByUsername(String username) {
        return xcUserRepository.findXcUserByUsername(username);
    }

    /**
     * 根据用户名获取用户权限的实现
     * @param username 用户名
     * @return
     */
    @Override
    public XcUserExt getUserExt(String username) {
        //查询用户信息
        XcUser xcUser = this.findXcUserByUsername(username);
        if(xcUser ==null) return null;

        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(xcUser,xcUserExt);

        //根据用户id查询用所属公司
        String xcUserId = xcUser.getId();
        XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findByUserId(xcUserId);
        if(xcCompanyUser!=null){
            String companyId = xcCompanyUser.getCompanyId();
            xcUserExt.setCompanyId(companyId);
        }

        //获取用户的所有权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(xcUserId);
        xcUserExt.setPermissions(xcMenus);

        //返回XcUserExt对象
        return xcUserExt;
    }

    public UcenterCode save(RegisterRequest registerRequest){
        XcUser user = new XcUser();
        System.out.println(registerRequest.toString());
        if(findXcUserByUsername(registerRequest.getUsername()) != null )
            return UcenterCode.UCENTER_REGISTER_USERNAME_EXIST;
        user.setUsername(registerRequest.getUsername());
        if(!registerRequest.getPassword().equals(registerRequest.getPasswordConfirm()))
            return UcenterCode.UCENTER_REGISTER_PASSWORD_INCONFORMITY;
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getUsername());
        user.setSex(registerRequest.getSex());
        user.setPhone(registerRequest.getPhone());

        user.setUtype("101001"); // 学生
        user.setStatus("1");

        Date d = new Date();
        user.setCreateTime(d);

        xcUserRepository.save(user);

        user = xcUserRepository.findXcUserByUsername(registerRequest.getUsername());

        XcUserRole xcUserRole = new XcUserRole();
        xcUserRole.setRoleId("17");
        xcUserRole.setUserId(user.getId());
        xcUserRole.setCreateTime(d);
        xcUserRepository.save(user);
        return UcenterCode.UCENTER_SUCCESS;
    }
}
