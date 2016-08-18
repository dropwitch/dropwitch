package com.github.dropwitch.entity.dao;

import com.github.dropwitch.entity.dao.base.MasterCommonBaseDao;
import org.hibernate.SessionFactory;

public class MasterCommonDao extends MasterCommonBaseDao {
    public MasterCommonDao(SessionFactory factory) {
        super(factory);
    }
}
