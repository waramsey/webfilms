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
		//String movieName = request.getParameter("movieName");
	    String movieName = "Bee Movie";
		Connection connection = null;
		String getInfoSql = "SELECT * FROM movieList WHERE title LIKE ?";
		String title = "", synopsis = "", rating = "", poster = "", duration = "";
	    
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			PreparedStatement preparedStmt = connection.prepareStatement(getInfoSql);
			preparedStmt.setString(1, movieName);
			ResultSet rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				title = rs.getString("title").trim();
				synopsis = rs.getString("synopsis").trim();
				rating = rs.getString("rating").trim();
				poster = rs.getString("poster").trim();
				duration = rs.getString("duration").trim();
			}
			
			preparedStmt.close();
			connection.close();
			
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + //
		            "<html>\n" + //
		            "<head><title>" + title + "</title></head>\n" + //
		            "<body bgcolor=\"#d3d3d3\">\n" + //
		            "<style>.moviePoster {" + //
		            "float: left; margin-right: 20px;" + //
		            "margin-bottom: 10px; height: 600px;" + //
		            "width: 400px; clear: left;" + //
		            "}</style>" + //
		            
		            "<img class='moviePoster' src='" + poster + "' alt='Webfilms'>" + //
		            
		            "<h2 align=\"center\">" + title + "</h2>\n" + //
		            "<ul>\n" + //

		            "  <li><b>Rating</b>: " + rating + "\n" + //
		            "  <li><b>Duration</b>: " + duration + "\n" + //
		            "  <li><b>Synopsis</b>: " + synopsis + "\n" + //
		            "</ul>\n" + //
		            
					"<h2 align=\"center\">Show Times</h2>\n" + //
					"<ul>\n" + //
					
					//TODO Update with a link to reserve tickets
					"  <li><b>Time 1</b>\n" + //
					"  <li><b>Time 2</b>\n" + //
					"  <li><b>Time 3</b>\n" + //
					"</ul>\n");

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
