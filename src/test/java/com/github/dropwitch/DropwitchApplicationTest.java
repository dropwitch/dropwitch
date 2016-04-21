package com.github.dropwitch;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DropwitchApplicationTest {
    private final Environment environment = Mockito.mock(Environment.class);
    private final JerseyEnvironment jersey = Mockito.mock(JerseyEnvironment.class);
    private final DataSourceFactory factory = Mockito.mock(DataSourceFactory.class);
    private final DropwitchApplication application = Mockito.mock(DropwitchApplication.class);
    private final DropwitchConfiguration configuration = Mockito.mock(DropwitchConfiguration.class);

    @Before
    public void setUp() throws Exception {
        configuration.setDataSourceFactory(factory);
        Mockito.when(environment.jersey()).thenReturn(jersey);
    }

    @Test
    public void buildResource() throws Exception {
        application.run(configuration, environment);

        Mockito.verify(jersey).register(DropwitchApplication.JacksonMessagePackProvider.class);
    }
}
