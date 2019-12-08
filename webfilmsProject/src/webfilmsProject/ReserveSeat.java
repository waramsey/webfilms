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
 * Servlet implementation class ReserveSeat
 */
@WebServlet("/ReserveSeat")
public class ReserveSeat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReserveSeat() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String theater = InformationManager.getTheater();
		String movie = InformationManager.getMovie();
		String time = InformationManager.getTime(); //TODO need to set this in TimeSelect
		
		Connection connection = null;
		String getInfoSql = "SELECT * FROM theater WHERE movie in (SELECT movie FROM theater GROUP BY movie HAVING count(*) > 1)";
		int Seats = 0, id = 1;
		String Time = "";
	    
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			//find out which seat was clicked
			String[] seatArray = {"1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D", "3A", "3B", "3C", "3D", "4A", "4B", "4C", "4D"};
			String seat = null;
			int seatNum;
			for (seatNum = 0; seatNum < seatArray.length; seatNum++) {
				seat = request.getParameter(seatArray[seatNum]);
				if (seat != null) {
					break;
				}
			}
			
			PreparedStatement preparedStmt = connection.prepareStatement(getInfoSql);
			ResultSet rs = preparedStmt.executeQuery();
			
			int newSeats = 0;
			while (rs.next()) {
				Time = rs.getString("Time").trim();
				if (Time.equals(time)) {
					Seats = rs.getInt("Seats");
					newSeats = (int) (Seats + Math.pow(10, (seatArray.length - seatNum - 1))); //issue of what happens if the seat was already reserved
					preparedStmt.execute("UPDATE " + theater + " SET Movie = " + movie + ", Time = " + time + ", Seats = " + newSeats + " WHERE id = " + id);
					break;
				}
				id++;
			}
			
			preparedStmt.close();
			connection.close();
			
			//use newSeats to generate the table
			String tableGen = Integer.toString(newSeats);
			String table = "<table align='center'>";
			for (int i = 0; i < tableGen.length(); i++) {
				if (i % 4 == 0) {
					table += "<tr>";
				}
				if (tableGen.charAt(i) == '0') {
					table += "<th><form action='ReserveSeat' method='post'>" + //
							"<input type='submit' class='ReserveSeat' name='" +
							seatArray[i] + "' value='" + seatArray[i] + "'></form></th>";
				} else if (tableGen.charAt(i) == '1') {
					table += "<th><button type='button' class='reserved'><h1>X</h1></button></th>";
				}
				if (i % 4 == 3) {
					table += "</tr>";
				}
			}
			table += "</table>";
			
			
			//TODO set the response (should be nearly the same as Seats.html, except some the taken seat is taken)
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
		            table + "<section><form id='SendEmail' action='SendEmail' method='POST'>" + //
		            "Email: <input type='text' name='email'>" + //
		            "<input type='submit' value='Reserve Seats' /></form></section>"
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
