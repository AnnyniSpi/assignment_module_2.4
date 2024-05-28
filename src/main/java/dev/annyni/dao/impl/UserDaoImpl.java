package dev.annyni.dao.impl;

import dev.annyni.dao.UserDao;
import dev.annyni.entity.User;
import dev.annyni.util.HibernateUtil;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public List<User> findAll() {
        try(Session session = HibernateUtil.openSession();
            EntityManagerFactory entityManagerFactory = HibernateUtil.openSession().getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager()){

            EntityGraph<?> entityGraph = entityManager.createEntityGraph("graph.userEvents");

            List<User> events = session.createQuery("FROM User", User.class)
                    .setHint("jakarta.persistence.loadgraph", entityGraph)
                    .getResultList();

            return events;
        } catch (Exception e){
            throw new RuntimeException("Error find all entities!");
        }
    }

    @Override
    public Optional<User> findByID(Integer id) {
        try(Session session = HibernateUtil.openSession()){
            User user = session.createQuery("select u from User u " +
                                            "left join fetch u.events " +
                                            "where u.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResult();

            return Optional.ofNullable(user);

        } catch (Exception e){
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(Session session = HibernateUtil.openSession()) {
            boolean result = true;

            User user = session.get(User.class, id);

            if (user == null){
                result = false;
                throw new RuntimeException("File not found " +  id);
            }

            session.remove(user);
            session.flush();

            return false;
        } catch (Exception e){
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public User update(User entity) {
        try(Session session = HibernateUtil.openSession()){
            if (entity == null){
                throw new RuntimeException("User not found!");
            }
            session.merge(entity);

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }

    @Override
    public User save(User entity) {
        try(Session session = HibernateUtil.openSession()){

            session.persist(entity);

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }
}
