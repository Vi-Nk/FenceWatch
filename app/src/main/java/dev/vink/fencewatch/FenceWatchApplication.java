package dev.vink.fencewatch;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

public class FenceWatchApplication extends Application<FenceWatchConfiguration> {

    public static void main(String[] args) throws Exception {
        new FenceWatchApplication().run(args);
    }

    @Override
    public void run(FenceWatchConfiguration configuration, Environment environment) throws Exception {
        System.out.println("FenceWatch Application server is starting...");
    }

}
