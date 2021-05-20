package model;

import java.sql.*;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notification {

	// Attributes
	private int notificationID;
	private String topic;
	private String content;
	private String date;
	private String time;

	// default constructor
	public Notification() {

	}

	// overloaded constructor
	public Notification(int notificationID, String topic, String content, String date, String time) {
		super();
		this.notificationID = notificationID;
		this.topic = topic;
		this.content = content;
		this.date = date;
		this.time = time;
	}

	// getters and setters
	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/admindb", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	// insert method
	public String publishNotification(String topic, String content) {

		String output = "";
		// connecting to the database
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}

			//insert into notification values(1,'important','jajajajajajaaaa')
			
			
			// insert query
			String query = "insert into notification (`notificationId`,`topic`,`content`,`date`,`time`)"
					+ " values(?, ?, ?, ?, ?)";
			
			
			// create a prepared statement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0); //auto increment 
			preparedStmt.setString(2, topic);
			preparedStmt.setString(3, content);

			// create a java calendar instance
			Calendar calendar = Calendar.getInstance();
			java.util.Date currentDate = calendar.getTime();

			// now, create a java.sql.Date from the java.util.Date
			java.sql.Date date = new java.sql.Date(currentDate.getTime());

			preparedStmt.setDate(4, date);

			// time

			Calendar cal = Calendar.getInstance();

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

			String ctime = sdf.format(cal.getTime()).toString();

			System.out.println(ctime);

			Time currentTime = Time.valueOf(ctime);

			preparedStmt.setTime(5, currentTime);

			preparedStmt.execute();
			con.close();
			
			output = "notification details inserted Successfully";

		} catch (Exception e) {

			output = "Error while inserting notification details";
			System.out.println(e.getMessage());
		}

		return output;
	}// end of insert method

// ******************************************************************************************
//method to read all the notifications
	public String readAllNotifications() {
		String output = "";

		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading";

			}
			String query = "select* from notification";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				String notificationid = Integer.toString(rs.getInt("notificationId"));
				String topic = rs.getString("topic");
				String content = rs.getString("content");
				String date = rs.getString("date");
				String time = rs.getString("time");

				output += "";
				output += "<div class=\"notification card bg-light m-2 \" style=\"max-width: 30rem; float: left;\">";

				output += "<div class=\"card-body\">";
				output += "<input id='hidItemIDUpdate' type='hidden' name='hidItemIDUpdate'  value='" + notificationid
						+ "'>";
				output += "<h4 class='ntopic'>" + topic + "</h4>";
				output += " <br> ";
				output += "<label class='ncontent'>" + content + "</label>";
				output += "<br> On:";
				output += "<label class='ndate'>" + date + "</label>";
				output += "  At:";
				output += "<label class='ntime'>" + time + "</label>";
				output += "</div>";
				
				 output += "<td><input name='btnUpdate'  type='button' value='Update' class='btnUpdate btn btn-warning btn-lg btn-block'></td>"
					 		+ "<td><form method='post' action='adminPanel.jsp'>"
					 		+ " <input name='btnRemove' type='submit' "
					 		+ "value='Remove' class=\"btn btn-danger btn-lg btn-block\"> <br>"
					 		+ "<input name='hidItemIDDelete' type='hidden' "
					 		+ "value='" + notificationid + "'>" + "</form></td></tr>"; 

				output += "</div>";
			}
			con.close();

		} catch (Exception e) {

			output = "Error while reading Notifications";
			System.out.println(e.getMessage());

		}
		return output;
	}// end of read method

	// ******************************************************************************************
	// method to read Notifications by topic
	public String readNotifications(int ntopic) {
		String output = "";
		// connect to the database
		try {
			Connection con = connect();
			if (con == null) {

				return "Error while connecting to the database for reading";

			}
			String query = "select* from notification where topic  = " + ntopic;
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {

				String notificationid = Integer.toString(rs.getInt("notificationId"));
				String topic = rs.getString("topic");
				String content = rs.getString("content");
				String date = rs.getString("date");
				String time = rs.getString("time");

				output += "";
				output += "<div class=\"student card bg-light m-2 \" style=\"max-width: 30rem; float: left;\">";

				output += "<div class=\"card-body\">";
				output += "<input id='hidItemIDUpdate' type='hidden' name='hidItemIDUpdate'  value='" + notificationid
						+ "'>";
				output += "<label class='ntopic'>" + topic + "</label>";
				output += "<br>";
				output += "<label class='ncontent'>" + content + "</label>";
				output += "<br>";
				output += "<label class='ndate'>" + date + "</label>";
				output += "<br>";
				output += "<label class='ntime'>" + time + "</label>";
				output += "</div>";

				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-itemid='" + notificationid + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-itemid='" + notificationid + "'></td></tr>";

				output += "</div>";

			}
			con.close();

		} catch (Exception e) {

			output = "Error while reading Notifications";
			System.out.println(e.getMessage());

		}
		return output;
	}

//******************************************************************************************
//method to remove a notification
	public String removeNotification(String notificationId) {

		String output = "";

		// connect to the database

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for removing";

			}
			String query = "delete from notification where notificationId = " + notificationId;
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.executeUpdate();
			output = " Notification details removed  successfully";
			con.close();

		} catch (Exception e) {

			output = "Error while removing Notification details";
			System.out.println(e.getMessage());
		}
		return output;
	}

	// ******************************************************************************************
	// method to update a notification
	public String updateNotification(String notificationId, String topic, String content) {
		String output = "";

		try {

			Connection con = connect();
			if (con == null) {

				output = "Error while connecting to the database for updating an notification";

			}

			String query = "update notification set topic = ?,	content = ? where notificationId = ?";

			PreparedStatement stmt = con.prepareStatement(query);
			int notificationID = Integer.parseInt(notificationId);
			stmt.setString(1, topic);
			stmt.setString(2, content);
			stmt.setInt(3, notificationID);

			stmt.execute();

			output = "notification details updated  successfully";
			con.close();

		} catch (Exception e) {

			output = "Error while updating notification details ";
			System.out.println(e.getMessage());
			e.printStackTrace();

		}

		return output;
	}

}
