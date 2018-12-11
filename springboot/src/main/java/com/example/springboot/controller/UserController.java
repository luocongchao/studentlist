package com.example.springboot.controller;

import com.example.springboot.common.JsonEx;
import com.example.springboot.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.domain.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//证明是controller层并且返回json
@RestController
public class UserController {

    private UserService userService = new UserService();


    @RequestMapping(value = "/user/noLogin")
    protected String noLogin() throws JSONException {
        return  JsonEx.getResultJson(50014,"身份验证失败","").toString();
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) throws JSONException {
        String token = userService.checkUser(user);
        return JsonEx.getResultJson(20000,token!=null?"登录成功":"用户名或密码错误",token!=null?true:false,token).toString();
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws JSONException, JsonProcessingException {
        boolean flag=false;
        if(request.getHeader("sessionToken")!=null){
            flag =userService.removeSessionToken(request.getHeader("sessionToken"));
        }
        return JsonEx.getResultJson(20000,flag?"退出成功":"退出失败",flag).toString();
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public String getUserInfo(HttpServletRequest request, HttpServletResponse response) throws JSONException, JsonProcessingException {
        User user=new User();
        if(request.getHeader("sessionToken")!=null){
            user =userService.getUserBySessionToken(request.getHeader("sessionToken"));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
        JSONObject jj=new  JSONObject(json);
        return JsonEx.getResultJson(user !=null?20000:50014,user!=null?"获取成功":"身份验证失败",jj).toString();
    }

}