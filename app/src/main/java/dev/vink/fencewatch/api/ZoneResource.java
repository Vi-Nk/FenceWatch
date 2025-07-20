package dev.vink.fencewatch.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.vink.fencewatch.core.Zone;
import dev.vink.fencewatch.db.ZoneDAO;
import dev.vink.fencewatch.service.SpatialIndex;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/zones")
public class ZoneResource {
    private ZoneDAO zoneDAO;
    private SpatialIndex spatialIndex;

    public ZoneResource(ZoneDAO zoneDAO, SpatialIndex spatialIndex) {
        this.zoneDAO = zoneDAO;
        this.spatialIndex = spatialIndex;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<Zone> getAllZones() {
        return zoneDAO.getAll();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Optional<Zone> getByName(@PathParam("name") String name) {
        return zoneDAO.getByName(name);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response addZone(Zone zone) {
        Zone created = zoneDAO.add(zone);
        spatialIndex.addZone(zone);
        return Response.accepted(Map.of("status", "created",
                "zoneName", created.getZoneName())).build();
    }

}
