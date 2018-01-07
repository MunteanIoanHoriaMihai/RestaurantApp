package dao;

import java.util.List;

import javax.transaction.SystemException;
import beans.User;
import security.PasswordEncrypter;

import org.hibernate.Session;

public class UserDAO {

	public void addUser(String username, String password) throws IllegalStateException, SystemException {

		Session session = HibernateUtil.buildSessionFactory().openSession();
		org.hibernate.Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			User user = new User();
			String dbPassword = PasswordEncrypter.encryptPassword(username, password);
			
			user.setUsername(username);
			user.setPassword(dbPassword);
			session.save(user);
			
			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public boolean hasUser(String username) throws IllegalStateException, SystemException {


		Session session = HibernateUtil.buildSessionFactory().openSession();
		List validationList= session.createQuery("from User where username='" + username + "'").list();
		if((validationList!=null) && (validationList.size()>0)) {
				return true;
		}
		System.out.println(validationList.size());
		return false;

	}


	public void deleteUser(String username) throws IllegalStateException, SystemException {

		Session session = HibernateUtil.buildSessionFactory().openSession();

		org.hibernate.Transaction tx = null;
		try {
			tx = session.beginTransaction();

			tx.commit();

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public boolean validate(String username, String password) {

		Session session = HibernateUtil.buildSessionFactory().openSession();
		String dbPassword = PasswordEncrypter.encryptPassword(username, password);
		List validationList= session.createQuery("from User where username='" + username + "' and password='" + dbPassword +"'").list();
		if((validationList!=null) && (validationList.size()>0)) {
				return true;
		}
		System.out.println(validationList.size());
		return false;

	}

}
