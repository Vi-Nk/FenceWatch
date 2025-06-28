package dev.vink.fencewatch.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import dev.vink.fencewatch.core.Device;
import io.dropwizard.hibernate.AbstractDAO;

public class DeviceDAO extends AbstractDAO<Device> {

    public DeviceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Device create(Device device) {
        return persist(device);
    }

    public Optional<Device> findByID(String deviceID) {
        return Optional.ofNullable(get(deviceID));
    }

    public List<Device> list() {
        Query<Device> q = currentSession().createQuery("FROM Device", Device.class);

        return q.getResultList();
    }

    public Device update(Device device) {
        return (Device) currentSession().merge(device);
    }

    public void remove(Device device) {
        currentSession().remove(device);
    }

}
