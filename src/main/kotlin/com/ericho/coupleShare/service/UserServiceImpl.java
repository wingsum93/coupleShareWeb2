package com.ericho.coupleShare.service;

import com.ericho.coupleShare.dao.UserDao;
import com.ericho.coupleShare.exception.JException;
import com.ericho.coupleShare.model.User;
import com.ericho.coupleShare.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    public UserDao userDao;

    @Override
    public void register(String login, String password) throws JException {
        User prevUser = userDao.findByUsername(login);
        if (prevUser != null) throw new JException("account already register");

        User user = new User();
        user.setName(login);
        user.setPassword(password);
        userDao.save(user);
    }

    @Override
    public void changePassword(Long id, String originPassword,
                               String newPassword) {

    }

    @Override
    public boolean checkLogin(String username, String password) throws JException{
        User user = this.userDao.findByUsername(username);
        
        if(user==null) throw new JException("account not exist");

        boolean s = StringUtil.equal(password, user.getPassword());

        if(!s) throw new JException("password not exist");
        
        return s;
    }

    @Override
    public void delete(User user) {

        userDao.delete(user);
    }

}
