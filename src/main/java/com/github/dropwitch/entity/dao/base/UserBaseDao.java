package com.github.dropwitch.entity.dao.base;

import com.github.dropwitch.entity.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import java.util.List;

public abstract class UserBaseDao extends AbstractDAO<User> {
    public UserBaseDao(SessionFactory factory) {
        super(factory);
    }

    public User create(String name) {
        User user = User
                .builder()
                .name(name)
                .build();
        currentSession().save(user);
        return user;
    }

    public void update(User user) {
        currentSession().update(user);
    }

    public void updateByHql(String hql) {
        currentSession().createQuery(hql).executeUpdate();
    }

    public void delete(User user) {
        currentSession().delete(user);
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        return criteria().list();
    }

    public User findById(Long id) {
        return (User) criteria()
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    public User findByName(String name) {
        return (User) criteria()
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

    public User findByCreatedAt(DateTime createdAt) {
        return (User) criteria()
                .add(Restrictions.eq("created_at", createdAt))
                .uniqueResult();
    }

    public User findByUpdatedAt(DateTime updatedAt) {
        return (User) criteria()
                .add(Restrictions.eq("updated_at", updatedAt))
                .uniqueResult();
    }
}
