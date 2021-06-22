package com.training.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import com.training.web.dao.ImageDao;
import com.training.web.entity.ImageEntity;
import com.training.web.entity.UserEntity;

@MultipartConfig(maxFileSize = 10240000)
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Blob imageBlob = null;
		PrintWriter out = response.getWriter();
		UserEntity u = (UserEntity) request.getSession().getAttribute("user");
		// int userid=((Integer) req.getAttribute("userid")).intValue();
		 System.out.println(u);
		ImageDao imagedao = new ImageDao();
		Part file = request.getPart("filename");
		String name = file.getSubmittedFileName();
		long size = file.getSize();
		String imagesize = String.valueOf(size);
		InputStream fileis = file.getInputStream();

		try {
			imageBlob = new SerialBlob(IOUtils.toByteArray(fileis));
		} catch (SerialException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("user:" + u + "file:" + file);
		try {

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			imagedao.saveImage(name, imagesize, imageBlob, u);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("WelcomePage.jsp");
		response.setContentType("image/gif");
		List<ImageEntity> images = u.getImages();
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
		request.setAttribute("images", images);
		rd.forward(request, response);
		
	}

}
