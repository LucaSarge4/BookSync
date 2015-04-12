<%@page import="DataAccessTier.Bookmark"%>
<%@page import="java.util.LinkedList"%>
<%@page import="BusinessLogicTier.BusinessLogic"%>
<%@page import="BusinessLogicTier.BusinessLogicInterface"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>My Bookmarks</title>
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/jquery.dataTables.css" rel="stylesheet">
        <link href="css/booksync.css" rel="stylesheet">
        <link rel="icon" href="images\logo.png" type="image/png" />
        <style>
            body{
                background-color: #EDEEF0;
                }
        </style>
        <script src="js/jquery-2.1.3.js"></script>
        <script src="js/jquery.dataTables.min.js"></script>
        <script src="js/getCookie.js"></script>
        <script type="text/javascript" class="init">
            $(document).ready(function() {
                var table = $('#bookmarksTable').DataTable();

                $('#bookmarksTable tbody').on( 'click', 'tr', function () {
                    $(this).toggleClass('selected');
                } );

                $('#confirm').click( function () {
                    alert( table.rows('.selected').data().length +' row(s) selected' );
                } );
            } );
        </script>
    </head>
    
    <body>
        <script src="js/bootstrap.min.js"></script>
        <div class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" ><span>BookSync</span></a>
                </div>
                <div class="collapse navbar-collapse" id="#navbar-ex-collapse">
                    <ul class="nav navbar-left navbar-nav"></ul>
                    <p class="navbar-text navbar-right"></p>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="" >
                            <a id="confirm" >Confirm</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
                        
                        
        
        <table class="table table-hover display" id="bookmarksTable">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Url</th>
                    <th>Tag</th>
                    <th>Last Edit Date</th>
                    <th>Folder</th>
                </tr>
            </thead>
            <tbody>
                <%  BusinessLogicInterface bl = new BusinessLogic();
                    String username="";
                    Cookie cookie = null;
                    Cookie[] cookies = null;
                    // Get an array of Cookies associated with this domain
                    cookies = request.getCookies();
                    for (int i = 0; i < cookies.length; i++){
                        cookie = cookies[i];
                        if(cookie.getName().equals("username"))
                            username=cookie.getValue();
                    }
                    LinkedList <Bookmark> bms = new LinkedList();
                    bms = bl.getBookmarks(username);
                    
                    for(int i=0;i<bms.size();i++){
                        out.write("<tr>");
                        out.write("<td>"+bms.get(i).getTitle()+"</td>");
                        out.write("<td>"+bms.get(i).getUrl()+"</td>");
                        out.write("<td>"+bms.get(i).getTag()+"</td>");
                        out.write("<td>"+bms.get(i).getLastEditDate()+"</td>");
                        out.write("<td>"+bms.get(i).getFatherFolder()+"</td>");
                        out.write("</tr>");
                    }
                %>
            </tbody>
        </table>       
    </body>

</html>