package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import beans.User;

public class HibernateUtil {

	static Session sessionObj;

	static SessionFactory sessionFactoryObj;

	static SessionFactory buildSessionFactory() {
    Configuration configObj = new Configuration();
    configObj.configure();
    sessionFactoryObj = configObj.buildSessionFactory();
    return sessionFactoryObj;
    
	}
}
