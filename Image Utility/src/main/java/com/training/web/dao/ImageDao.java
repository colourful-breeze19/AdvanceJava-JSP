package com.training.web.dao;


import java.sql.Blob;
import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.training.web.config.HibernateConfig;
import com.training.web.entity.ImageEntity;
import com.training.web.entity.UserEntity;

public class ImageDao {
	
	static SessionFactory sessionFactory = HibernateConfig.getSessionFactory();

	public static void saveImage(String name, String size, Blob image, UserEntity user) {
		Session session = sessionFactory.openSession();
		Transaction tx =  session.beginTransaction();
		ImageEntity ig = new ImageEntity();
		ig.setName(name);
		ig.setSize(size);
		ig.setImage(image);
		ig.setUser(user);

		session.save(ig);
		

		tx.commit();
		
		System.out.println("Image saved to database");
		session.close();
	}
	public static List <ImageEntity> fetchImage(UserEntity user) 
	{
		Session session = sessionFactory.openSession();
		Transaction tx = (Transaction) session.beginTransaction();
		System.out.println("Fetching images from DB");
		System.out.println(user);
		List<ImageEntity> images = session.createQuery("select i from Image i where i.user='" + user.getId() + "' ").list();
		tx.commit();
		System.out.println(images);
		System.out.println(images.size());
		if(images.size()>0)
		{
			return images;
		}
		else
		{
			return null;
		}
	}


}
