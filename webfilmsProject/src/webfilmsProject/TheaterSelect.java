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
			
			String theater = null;
			String theaterID = null;
			String[] theaters = {"Village Pointe", "Oakview", "Aksarben"};
			for (int i = 0; i < theaters.length; i++) {
				theater = request.getParameter(theaters[i]);
				if (theater != null) {
					break;
				}
			}
			
			switch (theater) {
				case "Village Pointe" : 
					theaterID = "VP";
					break;
				case "Oakview" :
					theaterID = "OakView";
					break;
				case "Aksarben" :
					theaterID = "AkSarBen";
					break;
			}

			//We've found our theater. Now we can get the theater by name from the Database.
			String theaterName = request.getParameter(theater);
			InformationManager.setTheater(theaterID);
			String getInfoSql = "SELECT * FROM movieList";
			String title = "", poster = "";
			PreparedStatement preparedStmt = connection.prepareStatement(getInfoSql);
			ResultSet rs = preparedStmt.executeQuery();
			String table = "";

			while (rs.next()) {
				title = rs.getString("title").trim();
				poster = rs.getString("poster").trim();

				table += "<th><form id='MovieSelect' action='MovieSelect' method='post'>" + //
						"<div><img src='" + poster + "' class='moviePoster' border='4'></div>" + //
						"<div><input type='submit' name='" + title + "' class='MovieSelect' value='" + title + "'></div>" + //
						"</form></th>";
			}

			preparedStmt.close();
			connection.close();

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType + //
		            "<html>\n" + //
		            "<head><title>" + theaterName + "</title></head>\n" + //
		            "<body bgcolor=\"#d3d3d3\">\n" + //
		            "<style>.MovieSelect {padding: 8px 10px;" + //
		            "border-radius: 8px; background-color: #800000;" + //
		            "color: WHITE; font-size: 22px;}.moviePoster {" + //
		            "height: 300px; width: 200px; margin-left: 20px;" + //
		            "margin-right: 20px; margin-bottom: 15px;" + //
		            "}</style>" + //
		            "<div style='background-color:#a9a9a9' align='center'><h1>" + theaterName + "</h1></div>" + //
		            "<table align='center'><tr>" + table + "</tr></table>");

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
