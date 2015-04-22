/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceTier;

import BusinessLogicTier.BusinessLogic;
import BusinessLogicTier.BusinessLogicInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Luca
 */
public class DeleteLocalized extends HttpServlet {

    BusinessLogicInterface bl;
    
    public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
          throws ServletException, IOException{
        bl = new BusinessLogic();
        
        PrintWriter out = response.getWriter();
        bl.deleteLocalized(request.getParameter("user"),request.getParameter("url"), request.getParameter("deviceName"));
        
    }
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request,
                   HttpServletResponse response)
    throws ServletException, IOException {
    doGet(request, response);
    }


}
