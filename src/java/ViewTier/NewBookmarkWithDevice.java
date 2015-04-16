/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewTier;

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
 * @author Giacomo
 */
public class NewBookmarkWithDevice extends HttpServlet {

    BusinessLogicInterface bl;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        bl = new BusinessLogic();
        
        PrintWriter out = response.getWriter();
        bl.addBookmark(request.getParameter("userid"), request.getParameter("title"),request.getParameter("url"),
                request.getParameter("lasteditdate"), request.getParameter("fatherfolder"), request.getParameter("type"),
                request.getParameter("description"),request.getParameter("tag"));
        bl.addLocalized(request.getParameter("userid"), request.getParameter("title"), request.getParameter("device"));
        out.write(request.getParameter("userid")+" "+request.getParameter("title")+" "+request.getParameter("url")+" "+
                request.getParameter("lasteditdate")+" "+request.getParameter("fatherfolder")+" "+ request.getParameter("type")+" "+
                request.getParameter("description")+" "+request.getParameter("tag"));
        
    }
    // Method to handle POST method request.
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
    doGet(request, response);
    }


}