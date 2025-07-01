package dev.vink.fencewatch.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import dev.vink.fencewatch.core.Device;
import dev.vink.fencewatch.core.Location;
import io.dropwizard.hibernate.AbstractDAO;

public class LocationDAO extends AbstractDAO<Location> {

    public LocationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Location createOrUpdate(Location location) {

        return (Location) currentSession().merge(location);
    }

    public Optional<Location> getByDeviceID(String deviceID) {
        return Optional.ofNullable(get(deviceID));
    }

    public List<Location> getAll() {
        Query<Location> q = currentSession().createQuery("FROM Location", Location.class);
        return q.getResultList();
    }

    public void remove(Location location) {
        currentSession().remove(location);
    }
}
