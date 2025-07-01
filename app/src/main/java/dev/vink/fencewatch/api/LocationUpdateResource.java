package dev.vink.fencewatch.api;

import dev.vink.fencewatch.core.Location;
import dev.vink.fencewatch.db.LocationDAO;
import dev.vink.fencewatch.db.DeviceDAO;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationUpdateResource {

    private final LocationDAO locationDAO;
    private final DeviceDAO deviceDAO;

    public LocationUpdateResource(LocationDAO locationDAO, DeviceDAO deviceDAO) {
        this.locationDAO = locationDAO;
        this.deviceDAO = deviceDAO;
    }

    @POST
    @UnitOfWork
    public Response submitLocation(Location location) {
        if (location.getDeviceID() == null || location.getDeviceID().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("deviceID must be provided").build();
        }
        if (deviceDAO.getByID(location.getDeviceID()).isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Device not found for ID: " + location.getDeviceID()).build();
        }
        locationDAO.createOrUpdate(location);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getLastKnownLocation(@PathParam("id") String deviceId) {
        Optional<Location> location = locationDAO.getByDeviceID(deviceId);
        if (location.isPresent()) {
            return Response.ok(location.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
