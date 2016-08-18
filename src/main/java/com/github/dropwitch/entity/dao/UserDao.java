package com.github.dropwitch.entity.dao;

import com.github.dropwitch.entity.dao.base.UserBaseDao;
import org.hibernate.SessionFactory;

public class UserDao extends UserBaseDao {
    public UserDao(SessionFactory factory) {
        super(factory);
    }
}
