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
 * Servlet implementation class MovieSelect
 */
@WebServlet("/MovieSelect")
public class MovieSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieSelect() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;
		String theater = InformationManager.getTheater();
	    
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			//Part 1: make a loop that runs request.getParameter() for every title in the database.  When it isn't null, we've found our movie title.
			PreparedStatement preparedStmt = connection.prepareStatement("SELECT title FROM movieList");
			ResultSet rs = preparedStmt.executeQuery();
			String movie = null;
			rs.next();
			do {
				movie = request.getParameter(rs.getString("title").trim());
			} while (rs.next() && movie == null);
			
			
			//Part 2: We've found our movie title. Now we can get the movie by name.
			String movieName = request.getParameter(movie);
			InformationManager.setMovie(movieName);
			String getInfoSql = "SELECT * FROM movieList WHERE title LIKE ?";
			String title = "", synopsis = "", rating = "", poster = "", duration = "";
			
			

			preparedStmt = connection.prepareStatement(getInfoSql);
			preparedStmt.setString(1, movieName);
			rs = preparedStmt.executeQuery();
			
			while (rs.next()) {
				title = rs.getString("title").trim();
				synopsis = rs.getString("synopsis").trim();
				rating = rs.getString("rating").trim();
				poster = rs.getString("poster").trim();
				duration = rs.getString("duration").trim();
			}
			preparedStmt = connection.prepareStatement("SELECT * FROM " + theater + " WHERE name='" + movie + "'");
			rs = preparedStmt.executeQuery();
			
			String table = "<table><tr>";
			while (rs.next()) {
				table += "<th><form action='TimeSelect' method='post'><input type='submit' class='TimeSelect' "
						+ "name='" + rs.getString("Time") + "' value='" + rs.getString("Time") + "'></form></th>";
			}
			table += "</tr></table>";
			
			preparedStmt.close();
			connection.close();
			
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + //
		            "<html>\n" + //
		            "<head><title>" + title + "</title></head>\n" + //
		            "<body bgcolor=\"#d3d3d3\">\n" + //
		            "<style>.TimeSelect {padding: 15px 20px;" + //
		            "border-radius: 8px; background-color: #800000;" + //
		            "color: WHITE; font-size: 20px;" + //
		            "}.moviePoster {" + //
		            "float: left; margin-left:30px; margin-right: 80px;" + //
		            "margin-bottom: 10px; height: 300px;" + //
		            "width: 200px; clear: left;" + //
		            "}</style>" + //
		            
		            "<div><div><h1 style='background-color:#a9a9a9' align='center'>" + movie + "</h1></div>" + //
		            "<img class='moviePoster' src='" + poster + "' alt='" + movie + "'>" + //
		            
		            "<h3>Rated " + rating + "</h3>" + //
		            "<h3>Duration: " + duration + "</h3>" + //
		            "<h3>Synopsis</h3><p>" + synopsis + "</p>" + //

		            "<h2>Show Times</h2>" + table);

		      out.println("</div></body></html>");
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
