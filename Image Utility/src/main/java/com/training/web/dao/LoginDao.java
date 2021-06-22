package com.training.web.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.training.web.config.HibernateConfig;
import com.training.web.entity.UserEntity;

public class LoginDao {

	static SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

	public static UserEntity getUser(String username, String password) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<UserEntity> users = session.createQuery(
				"select u from UserEntity u where u.username='" + username + "' and u.password='" + password + "'")
				.list();
		tx.commit();
		
		if (users.size() == 1) {
			return users.get(0);
		} else {
			return null;
		}

	}
}
