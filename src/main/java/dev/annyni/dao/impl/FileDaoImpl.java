package dev.annyni.dao.impl;

import dev.annyni.dao.FileDao;
import dev.annyni.entity.File;
import dev.annyni.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class FileDaoImpl implements FileDao {

    @Override
    public List<File> findAll() {
        try(Session session = HibernateUtil.openSession()){
            List<File> files = session.createQuery("From File", File.class)
                    .getResultList();

            return files;
        } catch (Exception e){
            throw new RuntimeException("Error find all entities!");
        }
    }

    @Override
    public Optional<File> findByID(Integer id) {
        try(Session session = HibernateUtil.openSession()){

            File file = session.get(File.class, id);

            return Optional.ofNullable(file);

        } catch (Exception e){
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(Session session = HibernateUtil.openSession()) {
            boolean result = true;

            File file = session.get(File.class, id);

            if (file == null){
                result = false;
                throw new RuntimeException("File not found " +  id);
            }

            session.remove(file);
            session.flush();

            return false;
        } catch (Exception e){
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public File update(File entity) {
        try(Session session = HibernateUtil.openSession()){

            if (entity == null){
                throw new RuntimeException("File not found!");
            }

            session.merge(entity);

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }

    @Override
    public File save(File entity) {
        try(Session session = HibernateUtil.openSession()){

            session.persist(entity);

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }
}
