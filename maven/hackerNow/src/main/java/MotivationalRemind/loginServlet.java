package MotivationalRemind;
 
import java.io.IOException;
import java.io.PrintWriter;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
         
        // read form fields
        String phoneNumber = request.getParameter("login");
        String submit = request.getParameter("submit");
        receiveSMS sms = new receiveSMS();
        sms.sendText("", phoneNumber);
        System.out.println("username: " + phoneNumber);
 
        // do some processing here...
         
        // get response writer
        PrintWriter writer = response.getWriter();
        
        // build HTML code
        String htmlRespone = "<html>";
        htmlRespone += "<h2>Check your messages!<br/>";      
        htmlRespone += "</html>";
         
        // return response
        writer.println(htmlRespone);

    }
 
}