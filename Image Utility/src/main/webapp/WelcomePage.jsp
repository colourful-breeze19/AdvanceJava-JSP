<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.training.web.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% 
	int i=1;
	List<ImageEntity> imageList = (List)request.getAttribute("images");
%>
<html>
<head>
<title>Image management utility</title>
</head>
<body>

	<h1 align="center">
	<font size="7">Image Management Utility</font>
	</h1>
	<hr>
	
	<p>
		<font size=4>Please select images to upload (Max. Size 1 MB)</font>
	</p>
	
		

	<form action="image" method="post" enctype="multipart/form-data">
		<input type="file" name="filename" size="50" /> <br /> 
		<input type="submit" value="Upload File" />		
	</form>

	<br>
	<br>
	<h4 align = 'center'> Uploaded Images </h4>
	<br>
	<table width="800px" align="center" border="2">
		<tr>
			<td>S.No</td>
			<td>Name</td>
			<td>Size</td>
			<td>preview</td>
			<td>Actions</td>
		</tr>
		
		<%
				for (ImageEntity image : imageList) {
		%>
		<tr>
			<td><%=i++%></td>
			<td><%=image.getName()%></td>
			<td><%=image.getSize()%></td>
			<td><img alt="img" src="<%=image.getBase64Image()%>" height="80" width="80"/></td>
		</tr>
		<%
				}
			%>

	</table>
	



</body>
</html>