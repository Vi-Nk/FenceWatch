package dev.vink.fencewatch.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Path("/health")
public class HealthCheckResource {

    private final DataSource dataSource;
    private final String validationQuery;

    public HealthCheckResource(DataSource dataSource, String validationQuery) {
        this.dataSource = dataSource;
        this.validationQuery = validationQuery;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "healthy");

        // DB connectivity check
        boolean dbHealthy = false;
        String dbMessage = "OK";
        if (validationQuery != null && !validationQuery.isEmpty()) {
            try (Connection conn = dataSource.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(validationQuery)) {
                dbHealthy = rs.next();
            } catch (Exception e) {
                dbHealthy = false;
                dbMessage = e.getMessage();
            }
        } else {
            // If no validation query, just check connection
            try (Connection conn = dataSource.getConnection()) {
                dbHealthy = conn.isValid(2);
            } catch (Exception e) {
                dbHealthy = false;
                dbMessage = e.getMessage();
            }
        }
        health.put("database", dbHealthy ? "healthy" : "unhealthy");
        health.put("dbMessage", dbMessage);

        return Response.ok(health).build();
    }
}