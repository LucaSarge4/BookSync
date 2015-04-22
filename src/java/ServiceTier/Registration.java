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


public class Registration extends HttpServlet {

    BusinessLogicInterface bl;
    
    public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
          throws ServletException, IOException{
        bl = new BusinessLogic();
        
        PrintWriter out = response.getWriter();
        if(bl.checkUsername(request.getParameter("user")) && bl.checkEmail(request.getParameter("email"))){
            bl.addUser(request.getParameter("user"),request.getParameter("firstName"),
                request.getParameter("lastName"),request.getParameter("psw"),
                    request.getParameter("email"),request.getParameter("country"),
                    request.getParameter("regdate"));
            out.write("success");
            Cookie username=new Cookie("username",request.getParameter("user")); 
            username.setMaxAge(60*60);
            response.addCookie(username); 
            Cookie psw = new Cookie("password",request.getParameter("psw")); 
            psw.setMaxAge(60*60);
            response.addCookie(psw); 
        }
        else if(!bl.checkUsername(request.getParameter("user")))
            out.write("username");
        else if(!bl.checkEmail(request.getParameter("email")))
            out.write("email");
        
    }
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request,
                   HttpServletResponse response)
    throws ServletException, IOException {
    doGet(request, response);
    }


}