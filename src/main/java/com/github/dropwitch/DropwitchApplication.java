package com.github.dropwitch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.dropwitch.core.DropwitchMediaType;
import com.github.dropwitch.entity.EntityList;
import com.github.dropwitch.entity.dao.MasterCommonDao;
import com.github.dropwitch.entity.dao.UserDao;
import com.github.dropwitch.resources.MasterResource;
import com.github.dropwitch.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

public class DropwitchApplication extends Application<DropwitchConfiguration> {
    @Provider
    @Consumes(DropwitchMediaType.APPLICATION_MSGPACK)
    @Produces(DropwitchMediaType.APPLICATION_MSGPACK)
    public static class JacksonMessagePackProvider extends JacksonJsonProvider {
        public JacksonMessagePackProvider() {
            super(new ObjectMapper(new MessagePackFactory()));
        }

        @Override
        protected boolean hasMatchingMediaType(MediaType mediaType) {
            if (mediaType != null) {
                String subtype = mediaType.getSubtype();
                return "x-msgpack".equals(subtype);
            }
            return false;
        }
    }

    private final HibernateBundle<DropwitchConfiguration> hibernate = new HibernateBundle<DropwitchConfiguration>(
            EntityList.get(), new SessionFactoryFactory()
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

        environment.jersey().register(JacksonMessagePackProvider.class);
        environment.jersey().register(new MasterResource(masterCommonDao));
        environment.jersey().register(new UserResource(userDao));
    }
}
