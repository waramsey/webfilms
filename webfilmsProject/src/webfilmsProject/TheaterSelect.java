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
 * Servlet implementation class TheaterSelect
 */
@WebServlet("/TheaterSelect")
public class TheaterSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TheaterSelect() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;

	    
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			//Part 1: make a loop that runs request.getParameter() for every theater in the database.  When it isn't null, we've found our theater.
//			PreparedStatement preparedStmt = connection.prepareStatement("SELECT theaterName FROM theaterNames");
//			ResultSet rs = preparedStmt.executeQuery();
//			String theater = null;
//			rs.next();
//			do {
//				theater = request.getParameter(rs.getString("theaterName").trim());
//			} while (rs.next() && theater == null);
			
			
			//Part 2: We've found our theater. Now we can get the theater by name.
//			String theaterName = request.getParameter(theater);
			String getInfoSql = "SELECT * FROM movieList";
			String title = "", poster = "";
			
			
			
			PreparedStatement preparedStmt = connection.prepareStatement(getInfoSql);
			ResultSet rs = preparedStmt.executeQuery();
			
			
			String table = "";
			int movieNumber = 0;

			while (rs.next()) {
				title = rs.getString("title").trim();
				poster = rs.getString("poster").trim();
				
				if (movieNumber % 3 == 0) {
					table += "<tr>";
				}
				
				table += "<th><form id='MovieSelect' action='MovieSelect' method='post'>" + //
						"<div><img src='" + poster + "' class='moviePoster'></div>" + //
						"<div><input type='submit' name='" + title +"' value='" + title + "'></div>" + //
						"</form></th>";
				
				if (movieNumber % 3 == 2) {
					table += "</tr>";
				}
				
				movieNumber++;
			}
			

			
			
			preparedStmt.close();
			connection.close();
			
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + //
		            "<html>\n" + //
		            "<head><title>" + "PLACEHOLDER" + "</title></head>\n" + //
		            "<body bgcolor=\"#d3d3d3\">\n" + //
		            "<style>.moviePoster {" + //
		            "height: 300px; width: 200px;" + //
		            "margin-right: 20px; margin-bottom: 20px;" + //
		            "}</style>" + //
		            
		            "<div><h1>" + "PLACEHOLDER" + "</h1>" + //
		            "<table>" + table + "</table>" + //
		            "</div>"
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
