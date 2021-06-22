package com.training.web.config;
import com.training.web.entity.ImageEntity;
import com.training.web.entity.UserEntity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateConfig {
	public static SessionFactory getSessionFactory() {
		SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(UserEntity.class).addAnnotatedClass(ImageEntity.class).buildSessionFactory();
		return sessionFactory;
	}

}
