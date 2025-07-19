package dev.vink.fencewatch.db;

import java.util.List;

import org.hibernate.SessionFactory;

import dev.vink.fencewatch.core.ZoneEvent;
import io.dropwizard.hibernate.AbstractDAO;

public class ZoneEventDAO extends AbstractDAO<ZoneEvent> {

    public ZoneEventDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public ZoneEvent insertEvent(ZoneEvent zoneEvent) {
        return persist(zoneEvent);
    }

    public List<ZoneEvent> findEventsbyDevice(String deviceID) {
        return list(
                currentSession().createQuery(
                        "FROM ZoneEvent WHERE deviceID = :deviceID", ZoneEvent.class)
                        .setParameter("deviceID", deviceID));
    }

}
