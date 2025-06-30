package dev.vink.fencewatch.db;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import dev.vink.fencewatch.core.Zone;
import io.dropwizard.hibernate.AbstractDAO;

public class ZoneDAO extends AbstractDAO<Zone> {

    public ZoneDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Zone add(Zone zone) {
        return persist(zone);
    }

    public List<Zone> getAll() {
        Query<Zone> q = currentSession().createQuery("FROM Zone", Zone.class);
        return q.getResultList();
    }

    public Optional<Zone> getByName(String zoneName) {
        return Optional.ofNullable(get(zoneName));
    }

}
