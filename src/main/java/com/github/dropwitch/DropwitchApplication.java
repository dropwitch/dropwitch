package com.github.dropwitch;

import com.github.dropwitch.entity.MasterCommon;
import com.github.dropwitch.entity.User;
import com.github.dropwitch.entity.dao.MasterCommonDao;
import com.github.dropwitch.entity.dao.UserDao;
import com.github.dropwitch.resources.MasterResource;
import com.github.dropwitch.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropwitchApplication extends Application<DropwitchConfiguration> {
    private final HibernateBundle<DropwitchConfiguration> hibernate = new HibernateBundle<DropwitchConfiguration>(
            MasterCommon.class,
            User.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(DropwitchConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final MigrationsBundle<DropwitchConfiguration> migrations = new MigrationsBundle<DropwitchConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(DropwitchConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new DropwitchApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwitch";
    }

    @Override
    public void initialize(Bootstrap<DropwitchConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(migrations);
    }

    @Override
    public void run(DropwitchConfiguration configuration, Environment environment) throws Exception {
        final MasterCommonDao masterCommonDao = new MasterCommonDao(hibernate.getSessionFactory());
        final UserDao userDao = new UserDao(hibernate.getSessionFactory());

        environment.jersey().register(new MasterResource(masterCommonDao));
        environment.jersey().register(new UserResource(userDao));
    }
}
