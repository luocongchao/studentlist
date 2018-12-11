package com.example.springboot.domain.impl;

import com.example.springboot.common.MD5;
import com.example.springboot.common.SessionManage;
import com.example.springboot.dao.UserMapper;
import com.example.springboot.domain.IUser;
import com.example.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserService implements IUser {
    //依赖注入
    @Autowired
    UserMapper userMapper;

    private static UserService userService;
    private SessionManage sessionManage = new SessionManage();

    @PostConstruct
    public void init() {
        userService = this;
    }

    @Override
    /**
     *@ActionName:checkUser
     *@Descript: //TODO 用户登录验证
     *@Author:lcc
     *@Date 2018/12/10  13:38
     *@Params [user]
     *@Return java.lang.String
     *@Version 1.0.0
     **/
    public String checkUser(User user) {
        User us = userService.userMapper.selectUserByName(user.getUserName());
        String pwd = new MD5().MD5(user.getUserPwd());
        System.out.println(pwd);
        if (us != null && pwd.equals(us.getUserPwd())) {
            return getSession(us);
        } else return null;
    }

    @Override
    /**
     *@ActionName:selectByUserId
     *@Descript: //TODO 根据用户ID获取用户信息
     *@Author:lcc
     *@Date 2018/12/10  13:38
     *@Params [userId]
     *@Return com.example.springboot.model.User
     *@Version 1.0.0
     **/
    public User selectByUserId(String userId) {
        return null;
    }

    @Override
    /**
     *@ActionName:getSession
     *@Descript: //TODO 获取用户SessionToken
     *@Author:lcc
     *@Date 2018/12/10  13:39
     *@Params [user]
     *@Return java.lang.String
     *@Version 1.0.0
     **/
    public String getSession(User user) {
        return sessionManage.getSession(user);
    }

    @Override
    /**
     *@ActionName:getUserBySessionToken
     *@Descript: //TODO 根据SessionToken获取用户信息
     *@Author:lcc
     *@Date 2018/12/10  13:39
     *@Params [sessionToken]
     *@Return com.example.springboot.model.User
     *@Version 1.0.0
     **/
    public User getUserBySessionToken(String sessionToken) {
        User user = sessionManage.getUserBySessionToken(sessionToken);
        User us = new User();
        us.setUserName(user.getUserName());
        us.setAvatar(user.getAvatar());
        us.setId(user.getId());
        return us;
    }

    @Override
    /**
     *@ActionName:removeSessionToken
     *@Descript: //TODO 移除SessionToken
     *@Author:lcc
     *@Date 2018/12/10  16:50
     *@Params [sessionToken]
     *@Return boolean
     *@Version 1.0.0
     **/
    public boolean removeSessionToken(String sessionToken) {
        return sessionManage.removeSessionToken(sessionToken);
    }
}
