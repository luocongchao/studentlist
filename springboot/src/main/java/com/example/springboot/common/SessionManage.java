package com.example.springboot.common;


import com.example.springboot.model.SessionDTO;
import com.example.springboot.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SessionManage {
    private static List<SessionDTO> sessionList = new ArrayList<>();
    private int timeOut = 20 * 60;  //session过期时间20分钟


    //获取一个Session
    public String getSession(User user) {
        String uid = UUID.randomUUID().toString();
        SessionDTO dto = _findUser(user);
        if (dto != null) {
            return dto.getSessionString();
        } else {
            while (_findSession(uid) != null) {
                uid = UUID.randomUUID().toString();
            }
            setSession(uid, user);
        }
        return uid;
    }

    //设置Session
    private boolean setSession(String session, User user) {
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionString(session);
        sessionDTO.setDate(new Date());
        sessionDTO.setUserName(user.getUserName());
        sessionDTO.setUserId(user.getUserId());
        sessionDTO.setAvatar(user.getAvatar());
        sessionList.add(sessionDTO);
        return true;
    }

    public User getUserBySessionToken(String session) {
        for (SessionDTO item : sessionList) {
            if (item.getSessionString().equals(session)) {
                //判断两个时间相差秒数
                int tOut = (int) ((new Date().getTime() - item.getDate().getTime()) / 1000);
                //判断是否超过设定的超时时间
                if (tOut > timeOut) {
                    sessionList.remove(item);
                    return null;
                } else {
                    //每访问一次 重新设定session的开始时间
                    item.setDate(new Date());
                    return item;
                }
            }
        }
        return  null;
    }

    //查找列表中是否存在UUID
    private SessionDTO _findUser(User user) {
        for (SessionDTO item : sessionList) {
            if (item.getUserId().equals(user.getUserId())) {
                item.setDate(new Date());
                return item;
            }
        }
        return null;
    }

    //查找列表中是否存在UUID
    private SessionDTO _findSession(String uid) {
        for (SessionDTO item : sessionList) {
            if (item.getSessionString().equals(uid)) {
                return item;
            }
        }
        return null;
    }

    public boolean removeSessionToken(String sessionToken){
        for (SessionDTO item : sessionList) {
            if (item.getSessionString().equals(sessionToken)) {
                sessionList.remove(item);
                return true;
            }
        }
        return  false;
    }
}
