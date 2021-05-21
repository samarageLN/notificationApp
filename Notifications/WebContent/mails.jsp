<%@page import="model.Mail"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	session.setAttribute("statusMsg1", "");
//Save---------------------------------
if (request.getParameter("to") != null) {

	Mail mail = new Mail();
	String stsMsg = "";

	System.out.println(request.getParameter("hidItemIDSave"));
	//Insert--------------------------
	if (request.getParameter("hidItemIDSave") == "") {

		System.out.println(request.getParameter("hidItemIDSave"));
		stsMsg = mail.sendCustomMail(request.getParameter("to"), request.getParameter("subject"),
				request.getParameter("message"));

	}
	session.setAttribute("statusMsg1", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidItemIDDelete") != null) {
	Mail mail = new Mail();
	String stsMsg = "";
	 stsMsg = mail.removeEmail(request.getParameter("hidItemIDDelete"));
	session.setAttribute("statusMsg1", stsMsg);
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mails</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/mails.js"></script>

<style type="text/css">
body {
	margin: 60px;
}
</style>

</head>
<body>

	<div class="container bootdey">
		<div class="email-app">

			<p class="text-center">New Message</p>
			<form id="formItem" name="formItem" method="post" action="mails.jsp">
				<div class="form-row mb-3">
					<label for="to" class="col-2 col-sm-1 col-form-label">To:</label>
					<div class="col-10 col-sm-11">
						<input type="email" class="form-control" id="to" name="to"
							placeholder="Type email">
					</div>
				</div>

				<div class="form-row mb-3">
					<label for="bcc" class="col-2 col-sm-1 col-form-label">Subject:</label>
					<div class="col-10 col-sm-11">
						<input type="email" class="form-control" id="subject"
							name="subject" placeholder="Type subject">
					</div>
				</div>


				<div class="form-group mt-4">
					<textarea class="form-control" id="message" name="message"
						rows="12" placeholder="Click here to reply"></textarea>
				</div>
				<div class="form-group">

					<input id="hidItemIDSave" type='hidden' name="hidItemIDSave">

					<div id="alertSuccess" class="alert alert-success"></div>
					<div id="alertError" class="alert alert-danger"></div>

					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-success">

					<button type="submit" class="btn btn-danger">Discard</button>
				</div>

			</form>




			<div id="divItemsGrid">
				<%
					// call the readItems method
				Mail mail = new Mail();
				out.print(mail.readAllMailDetails());
				%>

			</div>

		</div>
	</div>


</body>
</html>