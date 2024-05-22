package dev.annyni.dao.impl;

import dev.annyni.dao.UserDao;
import dev.annyni.entity.User;
import dev.annyni.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    public static UserDaoImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        try(Session session = HibernateUtil.openSession()){
            session.beginTransaction();

            List<User> events = session.createQuery("select u from User u", User.class)
                    .getResultList();

            session.getTransaction().commit();

            return events;
        } catch (Exception e){
            throw new RuntimeException("Error find all entities!");
        }
    }

    @Override
    public Optional<User> findByID(Integer id) {
        try(Session session = HibernateUtil.openSession()){
            session.beginTransaction();

            User user = session.createQuery("select u from User u " +
                                            "left join fetch u.events " +
                                            "where u.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResult();

            session.getTransaction().commit();

            return Optional.ofNullable(user);

        } catch (Exception e){
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(Session session = HibernateUtil.openSession()) {
            session.beginTransaction();

            boolean result = true;

            User user = session.get(User.class, id);

            if (user == null){
                result = false;
                throw new RuntimeException("File not found " +  id);
            }

            session.remove(user);
            session.flush();

            session.getTransaction().commit();

            return false;
        } catch (Exception e){
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public User update(User entity) {
        try(Session session = HibernateUtil.openSession()){

            session.beginTransaction();

            if (entity == null){
                throw new RuntimeException("User not found!");
            }
            session.merge(entity);

            session.getTransaction().commit();

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }

    @Override
    public User save(User entity) {
        try(Session session = HibernateUtil.openSession()){

            session.beginTransaction();

            session.persist(entity);

            session.getTransaction().commit();

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }
}
