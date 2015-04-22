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
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Giacomo
 */
public class ImportFromHTML extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    BusinessLogicInterface bl;
    String username, device, bookmarks, father;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException { 
        PrintWriter out = response.getWriter();
        this.bl = new BusinessLogic();
        this.username = request.getParameter("username");
        this.bookmarks = request.getParameter("bookmarks");
        this.father = "";
        //System.out.println("Loaded file: "+this.bookmarks);
        this.removeHeading();
        this.evaluateAndProcessNext();
        out.print("Loaded");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void evaluateAndProcessNext() {
        if(this.bookmarks.trim().length()!=0){
            // parsing is not complete
            // get tag 
            //System.out.println("Remaining file to parse: "+this.bookmarks);
            String s = this.bookmarks.trim().substring(0,4);
            switch(s){
                case "<DT>":
                    // check if is a folder or a bookmark
                    if(this.bookmarks.trim().substring(4,6).equals("<H")){
                        this.addFolder();
                        break;
                    }else{
                        this.addBookmark();
                        break;
                    }
                case "<DL>":
                    this.startDL();
                    break;
                case "</DL":
                    this.closeFolder();
                    break;
                case "</DT":
                    this.closeFolderDT();
                    break;
                default:
                    // do nothing
            }
        }
    }

    private void removeHeading() {
        int n = this.bookmarks.trim().indexOf("<DT><H3>Booksync</H3>");
        this.bookmarks = (this.bookmarks.trim().substring(n+21)).trim();
        this.father = "Booksync";
    }

    private void addFolder() {
        String title = (this.bookmarks.trim().substring(
            this.bookmarks.trim().indexOf("<H3>")+4,
            this.bookmarks.trim().indexOf("</H3>")
        )).trim();
        this.bookmarks = (this.bookmarks.trim().substring(
                this.bookmarks.trim().indexOf("</H3>")+5
        )).trim();
        String lasteditdate = (new Date()).toString();
        // add bookmark
        //System.out.println("Adding folder: "+title+" with father: "+this.father);
        this.bl.addBookmark(this.username, title, "",lasteditdate , this.father, "web", "", "");
        // don't need to localize, addLocalized allow to add folder when add bookmarks
        // change father after adding
        this.father = title;
        // call next step
        this.evaluateAndProcessNext();
    }

    private void addBookmark() {
        // cutting informations
        String cut = this.bookmarks.trim().substring(
            this.bookmarks.trim().indexOf("<DT><A")+6,
            this.bookmarks.trim().indexOf("</A>")
        ).trim();
        String title = cut.trim().substring(
            cut.trim().lastIndexOf(">")+1,
            cut.trim().length()
        ).trim();
        String url = cut.trim().substring(
            cut.trim().indexOf("HREF=")+6,
            cut.trim().length()
        ).trim();
        url = url.trim().substring(
            0,
            url.trim().indexOf("\"")
        ).trim();
        this.bookmarks = (this.bookmarks.trim().substring(
            this.bookmarks.trim().indexOf("</A>")+4,
            this.bookmarks.trim().length()
        )).trim();
        String lasteditdate = (new Date()).toString();
        // adding bookmark
        //System.out.println("Adding bookmark "+title+" with father: "+this.father+" and url: "+url);
        this.bl.addBookmark(this.username, title, url, lasteditdate, this.father, "web", "", "");
        // call next step
        this.evaluateAndProcessNext();
    }

    private void startDL() {
        // <DL><p> is 7 char
        this.bookmarks = (this.bookmarks.trim().substring(7, this.bookmarks.trim().length())).trim();
        // call next step
        this.evaluateAndProcessNext();
    }

    private void closeFolder() {
        // remove </DL> tag
	this.bookmarks = (this.bookmarks.trim().substring(5, this.bookmarks.trim().length())).trim();
        // get upper folder if current father isn't Booksync
        this.father = this.bl.getUpperFolder(this.username, this.father);
        // call next step
        this.evaluateAndProcessNext();
    }

    private void closeFolderDT() {
        // remove </DT> tag
	this.bookmarks = (this.bookmarks.trim().substring(5, this.bookmarks.trim().length())).trim();
	// folder id updated on closeFolder(). Ready for next step
	this.evaluateAndProcessNext();
    }

}
