package dev.annyni;

import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.entity.User;
import dev.annyni.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.openSession().getSessionFactory();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            User user = User.builder()
                    .name("Riki")
                    .build();

            File file = File.builder()
                    .name("hoho")
                    .filePath("/hoho")
                    .build();

            Event event = Event.builder()
                    .user(user)
                    .file(file)
                    .build();

            session.merge(event);


            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }


}
