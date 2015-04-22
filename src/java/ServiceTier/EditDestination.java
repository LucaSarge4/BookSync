package ServiceTier;

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
    
    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        this.bl = new BusinessLogic();
        PrintWriter out = response.getWriter();
        String destID = this.bl.getDestinationID(request.getParameter("user"), request.getParameter("ckDev"));
        if(request.getParameter("device")!=""){
            this.bl.editDestinationName(destID, request.getParameter("device"));
        }
        if(request.getParameter("os")!=""){
            this.bl.editDestinationOS(destID, request.getParameter("os"));
        }
        if(request.getParameter("browser")!=""){
            this.bl.editDestinationBrowser(destID, request.getParameter("browser"));
        }
        if(request.getParameter("dropbox")!=""){
            this.bl.editDestinationDropbox(destID, request.getParameter("dropbox"));
        }
    }
    // Method to handle POST method request.
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        doGet(request, response);
    }


}