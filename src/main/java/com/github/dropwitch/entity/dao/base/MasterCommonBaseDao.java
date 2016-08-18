package com.github.dropwitch.entity.dao.base;

import com.github.dropwitch.entity.MasterCommon;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class MasterCommonBaseDao extends AbstractDAO<MasterCommon> {
    public MasterCommonBaseDao(SessionFactory factory) {
        super(factory);
    }

    @SuppressWarnings("unchecked")
    public List<MasterCommon> findAll() {
        return criteria().list();
    }
}
