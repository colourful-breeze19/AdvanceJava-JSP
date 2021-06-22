package com.training.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.SessionFactory;

import com.training.web.config.HibernateConfig;
import com.training.web.dao.ImageDao;
import com.training.web.entity.UserEntity;

public class TableServlet extends HttpServlet {
	
	static SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
			
		UserEntity user = (UserEntity) req.getSession().getAttribute("user");
		ImageDao imagedao = new ImageDao();
		try {
			imagedao.fetchImage(user);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
			
		
	}

	}
	
