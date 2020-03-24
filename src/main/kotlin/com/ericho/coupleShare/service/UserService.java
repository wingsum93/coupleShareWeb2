package com.ericho.coupleShare.service;

import com.ericho.coupleShare.exception.JException;
import com.ericho.coupleShare.model.User;


public interface UserService {
    void register(String login, String password) throws JException;

    void changePassword(Long id, String originPassword, String newPassword);

    boolean checkLogin(String username, String password) throws JException;

    void delete(User user);
}
