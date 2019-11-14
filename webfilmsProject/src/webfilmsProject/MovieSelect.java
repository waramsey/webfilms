package webfilmsProject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

		//String movieName = request.getParameter("movieName");
	    Connection connection = null;
		
		try {
			DBConnection.getDBConnection();
			connection = DBConnection.connection;
			
			//Do whatever Database stuff we need
				//Search for movie title within database
				//Get relevant movie information
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "replace with movie name";
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
	            
	            "<img class='moviePoster' src='images/webfilms.png' alt='Webfilms'>" + //
	            
	            "<h2 align=\"center\">" + title + "</h2>\n" + //
	            "<ul>\n" + //

	            //TODO Probably cut back on the amount of info we give here.
	            "  <li><b>Release Date</b>: _____\n" + //
	            "  <li><b>Genre</b>: _____\n" + //
	            "  <li><b>Rating</b>: _____\n" + //
	            "  <li><b>Runtime</b>: _____\n" + //
	            "  <li><b>Director</b>: _____\n" + //
	            "  <li><b>Starring</b>: _____\n" + //
	            "  <li><b>Synopsis</b>: _____\n" + //
	            "</ul>\n" + //
	            
				"<h2 align=\"center\">Show Times</h2>\n" + //
				"<ul>\n" + //
				
				//TODO Update with a link to reserve tickets
				"  <li><b>Time 1</b>\n" + //
				"  <li><b>Time 2</b>\n" + //
				"  <li><b>Time 3</b>\n" + //
				"</ul>\n");

	      out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
