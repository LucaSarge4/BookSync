package ViewTier;

import BusinessLogicTier.BusinessLogic;
import BusinessLogicTier.BusinessLogicInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditBookmark extends HttpServlet {

    BusinessLogicInterface bl;
    
    public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
          throws ServletException, IOException{
        bl = new BusinessLogic();
        PrintWriter out = response.getWriter();
        String bookID = bl.getBookID(request.getParameter("user"), request.getParameter("ckUrl"));
        if(request.getParameter("title")!=""){
            out.write("title");
            bl.editBookmarkTitle(bookID, request.getParameter("title"));
            bl.editBookmarkLastEditDate(bookID, request.getParameter("lasteditdate"));
        }
        if(request.getParameter("url")!=""){
            bl.editBookmarkUrl(bookID, request.getParameter("url"));
            bl.editBookmarkLastEditDate(bookID, request.getParameter("lasteditdate"));
        }
        if(request.getParameter("fatherfolder")!=""){
            bl.editBookmarkFatherFolder(bookID, request.getParameter("fatherfolder"));
            bl.editBookmarkLastEditDate(bookID, request.getParameter("lasteditdate"));
        }
        if(request.getParameter("description")!=""){
            bl.editBookmarkDescription(bookID, request.getParameter("description"));
            bl.editBookmarkLastEditDate(bookID, request.getParameter("lasteditdate"));
        }
        if(request.getParameter("tag")!=""){
            out.write("tag");
            bl.editBookmarkTag(bookID, request.getParameter("tag"));
            bl.editBookmarkLastEditDate(bookID, request.getParameter("lasteditdate"));
        }
    }
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request,
                   HttpServletResponse response)
    throws ServletException, IOException {
    doGet(request, response);
    }


}