package dev.annyni.dao.impl;

import dev.annyni.dao.EventDao;
import dev.annyni.entity.Event;
import dev.annyni.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class EventDaoImpl implements EventDao {

    private static final EventDaoImpl INSTANCE = new EventDaoImpl();

    public static EventDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Event> findAll() {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();

            List<Event> events = session.createQuery("select e from Event e", Event.class)
                    .getResultList();

            session.getTransaction().commit();

            return events;
        } catch (Exception e) {
            throw new RuntimeException("Error find all entities!");
        }
    }

    @Override
    public Optional<Event> findByID(Integer id) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();

            Event event = session.createQuery("select e from Event e " +
                                              "left join fetch e.user " +
                                              "left join fetch e.file " +
                                              "where e.id = :id", Event.class)
                    .setParameter("id", id)
                    .uniqueResult();

            session.getTransaction().commit();

            return Optional.ofNullable(event);

        } catch (Exception e) {
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();

            boolean result = true;

            Event event = session.get(Event.class, id);

            if (event == null) {
                result = false;
                throw new RuntimeException("File not found " + id);
            }

            session.remove(event);
            session.flush();

            session.getTransaction().commit();

            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public Event update(Event entity) {
        try (Session session = HibernateUtil.openSession()) {

            session.beginTransaction();

            if (entity == null) {
                throw new RuntimeException("Event not found!");
            }
            session.merge(entity);

            session.getTransaction().commit();

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error save entity!");
        }
    }

    @Override
    public Event save(Event entity) {
        try (Session session = HibernateUtil.openSession()) {

            session.beginTransaction();

            session.persist(entity);

            session.getTransaction().commit();

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error save entity!");
        }
    }
}
