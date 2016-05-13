package com.github.dropwitch;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class DropwitchApplicationTest {
    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
    private final DataSourceFactory factory = mock(DataSourceFactory.class);
    private final DropwitchApplication application = new DropwitchApplication();
    private final DropwitchConfiguration configuration = new DropwitchConfiguration();

    @Before
    public void setUp() throws Exception {
        configuration.setDataSourceFactory(factory);
        when(environment.jersey()).thenReturn(jersey);
        // TODO hibernateのmockも作って読み込む
        // @see https://github.com/dropwizard/dropwizard/blob/master/dropwizard-hibernate/src/test/java/io/dropwizard/hibernate/HibernateBundleTest.java
    }

    @Test
    public void buildResource() throws Exception {
        application.run(configuration, environment);

        verify(jersey).register(DropwitchApplication.JacksonMessagePackProvider.class);
    }
}
