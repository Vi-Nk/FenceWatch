package dev.vink.fencewatch.db;

import java.util.List;

import org.hibernate.SessionFactory;

import dev.vink.fencewatch.core.ZoneEvent;
import io.dropwizard.hibernate.AbstractDAO;

public class ZoneEventDAO extends AbstractDAO<ZoneEvent> {

    private SessionFactory sessionFactory;

    public ZoneEventDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
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

    // Use when UnitOfWork cannot call the insert
    public ZoneEvent insertEventWithTransaction(ZoneEvent zoneEvent) {
        var session = sessionFactory.openSession();
        var tx = session.beginTransaction();
        try {
            session.persist(zoneEvent);
            tx.commit();
            return zoneEvent;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
