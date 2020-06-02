package com.xuecheng.ucenter.service.impl;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.XcUserRole;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import com.xuecheng.ucenter.dao.XcUserRepository;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
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
}
