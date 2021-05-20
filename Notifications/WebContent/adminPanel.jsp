<%@page import="model.Notification"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	session.setAttribute("statusMsg", "");
//Save---------------------------------
if (request.getParameter("ntopic") != null) {

	Notification notification = new Notification();
	String stsMsg = "";

	System.out.println(request.getParameter("hidItemIDSave"));
	//Insert--------------------------
	if (request.getParameter("hidItemIDSave") == "") {

		stsMsg = notification.publishNotification(request.getParameter("ntopic"), request.getParameter("ncontent"));

	} else//Update----------------------
	{
		stsMsg = notification.updateNotification(request.getParameter("hidItemIDSave"), request.getParameter("ntopic"),
		request.getParameter("ncontent"));
	}
	session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidItemIDDelete") != null) {
	Notification notification = new Notification();
	String stsMsg = notification.removeNotification(request.getParameter("hidItemIDDelete"));
	session.setAttribute("statusMsg", stsMsg);
}
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Panel</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/notifications.js"></script>

<style type="text/css">
body {
	margin: 25px;
}
</style>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col">


				<h3>
					Admin Panel <small class="text-muted">Notification
						Management</small>
				</h3>


				<div class="col-md-6">
					<div class="form-group">

						<form id="formItem" name="formItem" method="post"
							action="adminPanel.jsp">


							<label for="topic"> TOPIC</label> <input id="ntopic"
								name="ntopic" type="text" class="form-control""> <br>
							<label for="content"> Notification Content:</label> <input
								id="ncontent" name="ncontent" type="text" class="form-control"><br>

							<input id="hidItemIDSave" type='hidden' name="hidItemIDSave">

							<div id="alertSuccess" class="alert alert-success"></div>
							<div id="alertError" class="alert alert-danger"></div>

							<input id="btnSave" name="btnSave" type="button" value="Save"
								" class='btn btn-success btn-lg btn-block'>
						</form>
					</div>
				</div>

				<div class="alert-success">
					<%
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>


				<br>
				<div id="divItemsGrid">
					<%
						// call the readItems method

					Notification notification = new Notification();
					out.print(notification.readAllNotifications());
					%>
				</div>

				<br>

			</div>
		</div>
	</div>

</body>
</html>