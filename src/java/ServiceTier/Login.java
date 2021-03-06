package ServiceTier;

import BusinessLogicTier.BusinessLogic;
import BusinessLogicTier.BusinessLogicInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
        if(bl.login(request.getParameter("user"), request.getParameter("psw"))){
            Cookie username=new Cookie("username",request.getParameter("user")); 
            username.setMaxAge(60*60);
            response.addCookie(username); 
            Cookie psw = new Cookie("password",request.getParameter("psw")); 
            psw.setMaxAge(60*60);
            response.addCookie(psw); 
            out.print(true);
        }
        else 
            out.print(false);
    }
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request,
                   HttpServletResponse response)
    throws ServletException, IOException {
    doGet(request, response);
    }


}
