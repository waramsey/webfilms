package webfilmsProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TimeSelect
 */
@WebServlet("/TimeSelect")
public class TimeSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeSelect() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String theater = InformationManager.getTheater();
		String movie = InformationManager.getMovie();
		Connection connection = null;

		String getInfoSql = "SELECT * FROM " + theater + " WHERE name='" + movie + "'";
		//returns all the rows belonging to the given movie	

		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			PreparedStatement preparedStmt = connection.prepareStatement(getInfoSql);
			ResultSet rs = preparedStmt.executeQuery();
			String time = null;
			rs.next();
			do {
				time = request.getParameter(rs.getString("Time").trim());
			} while (rs.next() && time == null);
			InformationManager.setTime(time);
			
			String Seats = "";
			
			String[] seatArray = {"1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D", "3A", "3B", "3C", "3D", "4A", "4B", "4C", "4D"};

			preparedStmt = connection.prepareStatement("SELECT * FROM " + theater + " WHERE name='" + movie + "'");
			rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				if (rs.getString("Time").trim().equals(time)) {
					Seats = rs.getString("Seats").trim();
				}
			}
			
			preparedStmt.close();
			connection.close();
			
			//use newSeats to generate the table
			String table = "<table align='center'>";
			for (int i = 0; i < Seats.length(); i++) {
				if (i % 4 == 0) {
					table += "<tr>";
				}
				if (Seats.charAt(i) == '0') {
					table += "<th><form action='ReserveSeat' method='post'>" + //
							"<input type='submit' class='ReserveSeat' name='" +
							seatArray[i] + "' value='" + seatArray[i] + "'></form></th>";
				} else if (Seats.charAt(i) == '1') {
					table += "<th><button type='button' class='reserved'><h1>X</h1></button></th>";
				}
				if (i % 4 == 3) {
					table += "</tr>";
				}
			}
			table += "</table>";
			
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + //
		            "<html>\n" + //
		            "<head><title>" + movie + " Seats</title></head>\n" + //
		            "<body bgcolor=\"#d3d3d3\">\n" + //
		            "<style>section {" + //
		            "float: left; width: 350px;" + //
		            "padding: 10px;}" + //
		            ".ReserveSeat {padding: 40px 50px;" + //
		            "border-radius: 8px; background-color: #800000;" + //
		            "color: WHITE; font-size: 30px;}" + //
		            ".reserved {border-radius: 8px; width: 140px;" + //
		            "height: 115px; background-color: BLACK;" + //
		            "color: WHITE;}</style>" + //
		            "<div style='background-color:#a9a9a9' align='center'>" + //
		            "<h1>Seats for " + movie + "</h1></div>" + //
		            table + "<div align='center'><form id='SendEmail' action='SendEmail' method='POST'>" + //
		            "Email: <input type='text' name='email'>" + //
		            "<input type='submit' value='Reserve Seats' /></form></div>"
		            );

		      out.println("</body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
