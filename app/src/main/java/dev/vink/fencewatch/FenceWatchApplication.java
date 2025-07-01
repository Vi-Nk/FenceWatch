package dev.vink.fencewatch;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.db.DataSourceFactory;

import dev.vink.fencewatch.core.*;
import dev.vink.fencewatch.db.*;
import dev.vink.fencewatch.api.*;

import java.util.Optional;

import javax.sql.DataSource;

public class FenceWatchApplication extends Application<FenceWatchConfiguration> {

    private final HibernateBundle<FenceWatchConfiguration> hibernateBundle = new HibernateBundle<FenceWatchConfiguration>(
            Device.class,
            Location.class,
            Zone.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(FenceWatchConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new FenceWatchApplication().run(args);
    }

    @Override
    public void initialize(io.dropwizard.core.setup.Bootstrap<FenceWatchConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(FenceWatchConfiguration configuration, Environment environment) throws Exception {
        System.out.println("FenceWatch Application server is starting...");

        final DeviceDAO deviceDAO = new DeviceDAO(hibernateBundle.getSessionFactory());
        final ZoneDAO zoneDAO = new ZoneDAO(hibernateBundle.getSessionFactory());
        final LocationDAO locationDAO = new LocationDAO(hibernateBundle.getSessionFactory());

        environment.jersey().register(new DeviceResource(deviceDAO));
        environment.jersey().register(new ZoneResource(zoneDAO));
        environment.jersey().register(new LocationUpdateResource(locationDAO, deviceDAO));

        // Create DataSource for health check
        DataSourceFactory dsFactory = configuration.getDataSourceFactory();
        DataSource dataSource = dsFactory.build(environment.metrics(), "health-check-ds");
        Optional<String> validationQuery = dsFactory.getValidationQuery();

        environment.jersey().register(
                new HealthCheckResource(
                        dataSource,
                        validationQuery.orElse(null) // Pass null if not present
                ));
    }
}
