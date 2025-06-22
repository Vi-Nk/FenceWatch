package dev.vink.fencewatch;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;
import jakarta.validation.constraints.NotNull;

public class FenceWatchConfiguration extends Configuration {
    @NotNull
    private String environment;

    private String apiVersion = "v1";

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
}
