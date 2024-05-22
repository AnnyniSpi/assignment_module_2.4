package dev.annyni.dao.impl;

import dev.annyni.dao.FileDao;
import dev.annyni.entity.File;
import dev.annyni.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class FileDaoImpl implements FileDao {

    private static final FileDaoImpl INSTANCE = new FileDaoImpl();

    private FileDaoImpl(){}

    public static FileDaoImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public List<File> findAll() {
        try(Session session = HibernateUtil.openSession()){
            session.beginTransaction();

            List<File> files = session.createQuery("select f from File f", File.class)
                    .getResultList();

            System.out.println(files);

            session.getTransaction().commit();

            return files;
        } catch (Exception e){
            throw new RuntimeException("Error find all entities!");
        }
    }

    @Override
    public Optional<File> findByID(Integer id) {
        try(Session session = HibernateUtil.openSession()){
            session.beginTransaction();

            File file = session.get(File.class, id);

            session.getTransaction().commit();

            return Optional.ofNullable(file);

        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error find by id entity! " + id);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try(Session session = HibernateUtil.openSession()) {
            session.beginTransaction();

            boolean result = true;

            File file = session.get(File.class, id);

            if (file == null){
                result = false;
                throw new RuntimeException("File not found " +  id);
            }

            session.remove(file);
            session.flush();

            session.getTransaction().commit();

            return false;
        } catch (Exception e){
            throw new RuntimeException("Error delete entity!");
        }
    }

    @Override
    public File update(File entity) {
        try(Session session = HibernateUtil.openSession()){

            session.beginTransaction();

            if (entity == null){
                throw new RuntimeException("File not found!");
            }
            session.merge(entity);
//            session.saveOrUpdate(entity);

            session.getTransaction().commit();

            return entity;
        } catch (Exception e){
            throw new RuntimeException("Error save entity!");
        }
    }

    @Override
    public File save(File entity) {
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
