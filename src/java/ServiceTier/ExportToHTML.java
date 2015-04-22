package ServiceTier;

import BusinessLogicTier.BusinessLogic;
import BusinessLogicTier.BusinessLogicInterface;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExportToHTML extends HttpServlet {

    BusinessLogicInterface bl;
    static String header = "<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
        "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
        "<TITLE>Bookmarks</TITLE>\n" +
        "<H1>Bookmarks</H1>\n" +
        "<DT><H3>Booksync</H3>\n"+
        "<DL><p>\n";
    static String end = "</DL>";
    
    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        this.bl = new BusinessLogic();
        PrintWriter out = response.getWriter();
        if(request.getParameter("username")==null){
            // cannot load bookmarks 
            out.print("");
        }else{
            // write header of description list
            out.print(BookmarkList.header);
            // write content of root folder(Booksync)
            out.print(this.bl.getBookmarksDescriptionList(request.getParameter("username")));
            // write end
            out.write(BookmarkList.end);
        }
    }
    // Method to handle POST method request.
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }


}
