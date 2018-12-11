package com.example.springboot.common;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonEx {

    /**
     *@ActionName:getResultJson
     *@Descript: //TODO 获取返回json字符串
     *@Author:lcc
     *@Date 2018/12/10  16:52
     *@Params
     *@Return JSONObject
     *@Version 1.0.0
     **/
    public static JSONObject getResultJson(int code,String message,Object jsonObject,String sessiontoken) throws JSONException {
        JSONObject m = new JSONObject();
        m.put("code", code);
        m.put("message",message);
        m.put("data", jsonObject);
        m.put("sessiontoken",sessiontoken);
        return m;
    }
    public static JSONObject getResultJson(int code,String message,Object jsonObject) throws JSONException {
        return getResultJson(code,message,jsonObject,null);
    }

    public static JSONObject getSuccessResultJson(String message,Object jsonObject) throws JSONException {
        JSONObject m = new JSONObject();
        m.put("code", 20000);
        m.put("message",message);
        m.put("data", jsonObject);
        return m;
    }

    public static JSONObject getFailResultJson(String message,Object jsonObject) throws JSONException {
        JSONObject m = new JSONObject();
        m.put("code", 50000);
        m.put("message",message);
        m.put("data", jsonObject);
        return m;
    }
}
