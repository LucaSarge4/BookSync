package ViewTier;

import BusinessLogicTier.BusinessLogic;
import BusinessLogicTier.BusinessLogicInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditDestination extends HttpServlet {

    BusinessLogicInterface bl;
    
    public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
          throws ServletException, IOException{
        bl = new BusinessLogic();
        PrintWriter out = response.getWriter();
        String destID = bl.getDestinationID(request.getParameter("user"), request.getParameter("ckDev"));
        if(request.getParameter("device")!=""){
            bl.editDestinationName(destID, request.getParameter("device"));
        }
        if(request.getParameter("os")!=""){
            bl.editDestinationOS(destID, request.getParameter("os"));
        }
        if(request.getParameter("browser")!=""){
            bl.editDestinationBrowser(destID, request.getParameter("browser"));
        }
        if(request.getParameter("dropbox")!=""){
            bl.editDestinationDropbox(destID, request.getParameter("dropbox"));
        }
    }
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request,
                   HttpServletResponse response)
    throws ServletException, IOException {
    doGet(request, response);
    }


}