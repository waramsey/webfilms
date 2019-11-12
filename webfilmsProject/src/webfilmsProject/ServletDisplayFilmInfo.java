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
 * Servlet implementation class MyServletFileRead1023Ramsey
 */
@WebServlet("/ServletDisplayFilmInfo")
public class ServletDisplayFilmInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDisplayFilmInfo() {
        super();
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	  response.setContentType("text/html");
	  PrintWriter out = response.getWriter();
	  String title = "Movie Information";
	  String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	  out.println(docType + //
		"<html>\n" + //
		"<head><title>" + title + "</title></head>\n" + //
		"<body bgcolor=\"#f0f0f0\">\n" + //
		"<h2 align=\"center\">" + title + "</h2>\n"  //
		/*
		 * Whatever information we'd like to display to the screen.
		 */);
	
	  out.println("</body></html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
