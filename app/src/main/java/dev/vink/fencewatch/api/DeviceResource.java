package dev.vink.fencewatch.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.vink.fencewatch.core.Device;
import dev.vink.fencewatch.db.DeviceDAO;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/devices")
public class DeviceResource {

    private final DeviceDAO deviceDAO;

    public DeviceResource(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<Device> getAllDevices() {
        return deviceDAO.list();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Optional<Device> getDevicebyID(@PathParam("id") String id) {
        return deviceDAO.findByID(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response registerDevice(Map<String, Object> input) {
        String deviceId = (String) input.get("deviceId");
        String vehicleType = (String) input.get("vehicleType");
        String driverName = (String) input.get("driverName");

        Device device = new Device(deviceId, vehicleType, driverName);
        deviceDAO.create(device);

        return Response.ok(Map.of(
                "status", "registered",
                "deviceId", deviceId)).build();
    }

}
