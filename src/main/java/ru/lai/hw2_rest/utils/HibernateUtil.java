package ru.lai.hw2_rest.utils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import ru.lai.hw2_rest.models.Appointment;
import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.models.Patient;

public class HibernateUtil {
    private static final Logger logger = Logger.getLogger(HibernateUtil.class.getName());
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties hibernateProperties = new Properties();
            hibernateProperties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties"));

            Configuration configuration = new Configuration();
            configuration.setProperties(hibernateProperties);

            configuration.addAnnotatedClass(Appointment.class);
            configuration.addAnnotatedClass(Doctor.class);
            configuration.addAnnotatedClass(Office.class);
            configuration.addAnnotatedClass(Patient.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize Hibernate SessionFactory", e);
            throw new ExceptionInInitializerError("Failed to initialize Hibernate SessionFactory");
        }
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
