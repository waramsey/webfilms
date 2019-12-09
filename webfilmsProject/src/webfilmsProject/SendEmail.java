
package webfilmsProject;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendEmail
 */
@WebServlet("/SendEmail")
public class SendEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SendEmail() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String username = "webfilms.csci4830@gmail.com";
        final String password = "WinterBreak2019!!";
        
        final String toEmail = request.getParameter("email");

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("webfilms.csci4830@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject("WebFilms Seat Reservation");
            message.setText("Your seats have been reserved!\n");

            Transport.send(message);

            System.out.println("Done");

            //Print out Webpage
            response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			out.println(docType +
		            "<html>\n" +
		            "<head><title>" + "Reserved Seats" + "</title></head>\n" +
		            "<body bgcolor=\"#d3d3d3\">\n" +
					"<h2 align=\"center\">Thank you!<br>An email confirmation has been sent to you.</h2>\n" +
		            "<div align=\"center\">" +
		            "<form action='Index.html'>" +
					"<input type='submit' value='Home Page'>" +
					"</form>" +
		            "</div>" +
					"<ul>\n");

		      out.println("</body></html>");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
