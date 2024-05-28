package dev.annyni.dao.impl;

import dev.annyni.dao.EventDao;
import dev.annyni.entity.Event;
import dev.annyni.util.HibernateUtil;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class EventDaoImpl implements EventDao {

    @Override
    public List<Event> findAll() {
        try (Session session = HibernateUtil.openSession();
             EntityManagerFactory entityManagerFactory = HibernateUtil.openSession().getEntityManagerFactory();
             EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            EntityGraph<?> entityGraph = entityManager.createEntityGraph("graph.eventUserAndFile");

            List<Event> events = session.createQuery("FROM Event", Event.class)
                    .setHint("jakarta.persistence.loadgraph", entityGraph)
                    .getResultList();

            return events;
        } catch (Exception e) {
            throw new RuntimeException("Error find all entities!");
        }
    }

    @Override
    public Optional<Event> findByID(Integer id) {
        try (Session session = HibernateUtil.openSession()) {
            Event event = session.createQuery("select e from Event e " +
                                              "left join fetch e.user " +
                                              "left join fetch e.file " +
                                              "where e.id = :id", Event.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(event);
        } catch (Exception e) {
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = HibernateUtil.openSession()) {
            boolean result = true;

            Event event = session.get(Event.class, id);

            if (event == null) {
                result = false;
                throw new RuntimeException("File not found " + id);
            }

            session.remove(event);
            session.flush();

            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public Event update(Event entity) {
        try (Session session = HibernateUtil.openSession()) {

            if (entity == null) {
                throw new RuntimeException("Event not found!");
            }
            session.merge(entity);

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error save entity!");
        }
    }

    @Override
    public Event save(Event entity) {
        try (Session session = HibernateUtil.openSession()) {

            session.persist(entity);

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Error save entity!");
        }
    }
}
