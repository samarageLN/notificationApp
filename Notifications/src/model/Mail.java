package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	private int mailId;
	private String sendFrom;
	private String sendTo;
	private String subject;
	private String message;
	private String date;
	private String time;

	// constructor
	public Mail() {
	}

	public Mail(int mailId, String sendFrom, String sendTo, String subject, String message, String date, String time) {
		super();
		this.mailId = mailId;
		this.sendFrom = sendFrom;
		this.sendTo = sendTo;
		this.subject = subject;
		this.message = message;
		this.date = date;
		this.time = time;
	}

	// getters and setters
	public int getMailId() {
		return mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public void setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	// database connection
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/admindb", "root", "");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return con;
	}

	public String sendCustomMail(String recepient, String subject, String messageBody) throws Exception {
		return sendMail(recepient, subject, messageBody);
	}

	public String sendMail(String recepient, String subject, String messageBody) throws Exception {

		System.out.println("Preparing email...");

		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		String myAccount = "lahirueduc@gmail.com";
		String password = "@password123";

		// login using the email address
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(myAccount, password);
			}
		});

		Message message = prepareMessage(session, myAccount, recepient, subject, messageBody);

		Transport.send(message);

		System.out.println("E Mail sent SuccessFully");

		return insertEmailDetails(myAccount, recepient, subject, messageBody);

	}

	private Message prepareMessage(Session session, String myAccount, String recepient, String subject,
			String messagee) {

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(myAccount));

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));

			message.setSubject(subject);

			message.setText(messagee);

			return message;

		} catch (AddressException e) {

			e.printStackTrace();
		} catch (MessagingException e) {

			e.printStackTrace();
		}

		return null;
	}

	// save mail to db
	public String insertEmailDetails(String from, String to, String subject, String message) {
		String output = "";

		try {
			Connection conn = connect();
			if (conn == null) {
				output = " Error while Connecting to the database";
			}

			String query = " insert into mails(`mailId`, `sendFrom`, `sendTo`, `subject`,`message`,`date`, `time`)"
					+ " values(?,?,?,?,?,?,?)";

			PreparedStatement preparedstatement = conn.prepareStatement(query);

			preparedstatement.setInt(1, 0);
			preparedstatement.setString(2, from);
			preparedstatement.setString(3, to);
			preparedstatement.setString(4, subject);
			preparedstatement.setString(5, message);

			// create a java calendar instance
			Calendar calendar = Calendar.getInstance();
			java.util.Date currentDate = calendar.getTime();

			// now, create a java.sql.Date from the java.util.Date
			java.sql.Date date = new java.sql.Date(currentDate.getTime());
			preparedstatement.setDate(6, date);

			// time
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String ctime = sdf.format(cal.getTime()).toString();
			System.out.println(ctime);
			Time currentTime = Time.valueOf(ctime);
			preparedstatement.setTime(7, currentTime);

			preparedstatement.execute();
			conn.close();

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the mail.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// method to view all mails
	public String readAllMailDetails() {
		String output = "";

		try {

			Connection conn = connect();
			if (conn == null) {
				output = " Error while Connecting to the database";
			}

			output = "<table class=\"table table-striped\" border='1'><tr><th>Receiptionist</th>"
					+ "<th>Subject</th><th>Message Body</th>" + "<th>Date</th><th>Time Body</th>"
					+ "<th>Remove</th></tr>";

			String query = " select * from mails";
			Statement stmt = conn.createStatement();

			// getting the result to the result set
			ResultSet resultSet = stmt.executeQuery(query);

			while (resultSet.next()) {

				// read a row and storing them on our variables
				String mailId = Integer.toString(resultSet.getInt("mailId"));
				String sendFrom = resultSet.getString("sendFrom");
				String sendTo = resultSet.getString("sendTo");
				String subject = resultSet.getString("subject");
				String message = resultSet.getString("message");
				;
				String date = resultSet.getString("date");
				String time = resultSet.getString("time");

				// Add into the html table

				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + mailId
						+ "'>" + sendTo + "</td>";
				output += "<td>" + subject + "</td>";
				output += "<td>" + message + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + time + "</td>";
				// buttons
				output += "<td><form method='post' action='mails.jsp'>" + " <input name='btnRemove' type='submit' "
						+ "value='Remove' class='btn btn-danger'> " + "<input name='hidItemIDDelete' type='hidden' "
						+ "value='" + mailId + "'>" + "</form></td></tr>";

			}

			// closing the connection
			conn.close();

		} catch (Exception e) {
			output = " Error while reading the mail details";
			e.printStackTrace();
		}
		return output;
	}

	// method to remove email
	public String removeEmail(String mailId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				output = "Error while Connecfting to the database ";

			}

			String query = "delete from mails where mailId=" + mailId + " ";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			con.close();

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while removing the mail.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateMail(String mailID, String recep, String subject, String message) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE mails SET sendTo=?,subject=?,message=? WHERE mailid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, recep);
			preparedStmt.setString(2, subject);
			preparedStmt.setString(3, message);
			preparedStmt.setString(4, mailID);

			// execute the statement
			preparedStmt.execute();
			con.close();

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the mail.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
