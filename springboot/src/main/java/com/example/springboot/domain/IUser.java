package com.example.springboot.domain;

import com.example.springboot.model.User;

public interface IUser {

    String checkUser(User user);

    User selectByUserId(String userId);

    String getSession(User user);

    User getUserBySessionToken(String sessionToken);

    boolean removeSessionToken(String sessionToken);
}
