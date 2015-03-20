package ViewTier;

import BusinessLogicTier.BusinessLogic;
import BusinessLogicTier.BusinessLogicInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {

    BusinessLogicInterface bl;
    
    public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
          throws ServletException, IOException{
        bl = new BusinessLogic();
        
        PrintWriter out = response.getWriter();
        String title = "Login Result";
        String docType =
        "<!doctype html public \"-//w3c//dtd html 4.0 " +
        "transitional//en\">\n";
        if(bl.login(request.getParameter("user"), request.getParameter("psw")))
            request.getRequestDispatcher("mybookmarks.jsp").forward(request, response);
        else 
            out.println(docType +
                  "<html>\n" +
                  "<head><title>" + title + "</title></head>\n" +
                  "<body bgcolor=\"#f0f0f0\">\n" +
                  "<h1 align=\"center\">" + title + "</h1>\n" +
                  "<ul>\n login non effettuato</ul>\n" +
                  "<a href=\"index.html\">Back</a></body></html>");
    }
// Method to handle POST method request.
public void doPost(HttpServletRequest request,
               HttpServletResponse response)
throws ServletException, IOException {
doGet(request, response);
}


}
