package com.github.dropwitch.entity.dao;

import com.github.dropwitch.entity.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserDao extends AbstractDAO<User> {
    public UserDao(SessionFactory factory) {
        super(factory);
    }

    public User create(String name) {
        User user = User.create();
        user.setName(name);
        Long id = (Long) currentSession().save(user);
        user.setId(id);
        return user;
    }

    public User findById(Long id) {
        return (User) criteria()
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
