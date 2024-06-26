package dev.annyni.util;

import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.entity.User;
import jakarta.persistence.EntityManager;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionImpl;

@UtilityClass
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration =
                    new Configuration()
                            .addAnnotatedClass(User.class)
                            .addAnnotatedClass(File.class)
                            .addAnnotatedClass(Event.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Session openSession() {
        return sessionFactory.openSession();
    }

}
