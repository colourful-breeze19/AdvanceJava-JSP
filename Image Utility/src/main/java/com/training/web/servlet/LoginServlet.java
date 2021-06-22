package com.training.web.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import com.training.web.dao.LoginDao;
import com.training.web.entity.ImageEntity;
import com.training.web.entity.UserEntity;  
  

public class LoginServlet extends HttpServlet{
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws ServletException, IOException {  
		
		LoginDao loginDao= new LoginDao();
	  
	    response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	          
	    String n=request.getParameter("username");  
	    String p = request.getParameter("userpass");  
	    
	    System.out.println("Username: "+ n);
	    System.out.println("Password: "+ p);
	    
	    
	    UserEntity user = LoginDao.getUser(n, p);
		HttpSession session = request.getSession();
		if (user != null) {
			session.setAttribute("user", user);
			List<ImageEntity> images = user.getImages();
			byte[] imageByte=null;
			for(ImageEntity image: images) {
				try {
					imageByte = image.getImage().getBytes(1, (int)image.getImage().length());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				byte[] encodeBase64 = Base64.encodeBase64(imageByte);
	            String base64Encoded = new String(encodeBase64, "UTF-8");
	            image.setBase64Image("data:image/jpeg;base64,"+base64Encoded);
			}
			System.out.println(images);
			
			
			
			
			RequestDispatcher rd = request.getRequestDispatcher("WelcomePage.jsp");
			session.setAttribute("user", user);
			request.setAttribute("images", images);
			response.setContentType("image/gif");
			rd.forward(request, response);
		} else {
			out.print("Sorry username or password error");
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			rd.include(request, response);
		}

		out.close();
	}

}
