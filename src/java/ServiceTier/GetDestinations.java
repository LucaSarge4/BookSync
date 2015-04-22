/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServiceTier;

import BusinessLogicTier.BusinessLogic;
import BusinessLogicTier.BusinessLogicInterface;
import DataAccessTier.Destination;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Giacomo
 */
public class GetDestinations extends HttpServlet {

    BusinessLogicInterface bl;
    
    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        this.bl = new BusinessLogic();
        LinkedList<Destination> destinationList;
        String separator = ";";
        PrintWriter out = response.getWriter();
        destinationList = this.bl.getDestinations(request.getParameter("user"));
        for(int i=0;i<destinationList.size();i++){
            out.write(destinationList.get(i).getDevice()+separator);
        };
    }
    // Method to handle POST method request.
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        doGet(request, response);
    }


}