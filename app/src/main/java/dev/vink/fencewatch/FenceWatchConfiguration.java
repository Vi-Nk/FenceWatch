package dev.vink.fencewatch;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class FenceWatchConfiguration extends Configuration {
    @NotNull
    private String environment;

    private String apiVersion = "v1";

    // Add DataSourceFactory for Hibernate
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    public String getEnvironment() {
        return environment;
    }

    @JsonProperty
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @JsonProperty
    public String getApiVersion() {
        return apiVersion;
    }

    @JsonProperty
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    // Getter and setter for DataSourceFactory
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }
}
