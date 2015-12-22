package com.github.dropwitch;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropwitchApplication extends Application<DropwitchConfiguration> {
    public static void main(String[] args) throws Exception {
        new DropwitchApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<DropwitchConfiguration> bootstrap) {
    }

    @Override
    public void run(DropwitchConfiguration configuration, Environment environment) throws Exception {

    }

    @Override
    public String getName() {
        return "dropwitch";
    }
}
