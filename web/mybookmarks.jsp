<%-- 
    Document   : mybookmarks
    Created on : 20-mar-2015, 18.14.32
    Author     : Luca
--%>

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
        <script src="//code.jquery.com/jquery.js"></script>
        <script src="js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" class="init">
        $(document).ready(function() {
            var table = $('#bookmarksTable').DataTable();

            $('#bookmarksTable tbody').on( 'click', 'tr', function () {
                if ( $(this).hasClass('selected') ) {
                    $(this).removeClass('selected');
                }
                else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
            } );

            $('#open').click( function () {
                window.open('http://'+table.row('.selected').data()[1]);
            } );
        } );
        </script>
    </head>
    
    <body>      
        <!-- Include all compiled plugins (below), or include individual
        files as needed -->
        <script src="js/bootstrap.min.js"></script>
        <div class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" ><span>BookSync</span></a>
                </div>
                <div class="collapse navbar-collapse" id="#navbar-ex-collapse">
                    <ul class="nav navbar-left navbar-nav"></ul>
                    <p class="navbar-text navbar-right"></p>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="" >
                            <a id="open" >Open</a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-expanded="false">New <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li>
                                    <a onclick="openPopup()" target="_blank" >Bookmark</a>
                                    <script>
                                        function openPopup(){
                                            var x = screen.width/2 - 700/2;
                                            var y = screen.height/2 - 450/2;
                                            window.open(
                                                'newBookmark.html','Bookmark','width=600,height=400,toolbar=0,\n\
                                                menubar=0,location=no,addressbar=no,status=1,scrollbars=0,\n\
                                                resizable=1,left='+x+',top='+y);
                                                return false;
                                        }
                                    </script>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a onclick="" >Folder</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a onclick="" >Delete</a>
                        </li>
                        <li>
                            <a onclick="" >Details</a>
                        </li>
                        <li class="dropdown">
                            <ul class="dropdown-menu" role="menu">
                                <li>
                                    <a onclick="" >Devices</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a onclick="" >Add Dropbox Folder</a>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a onclick="" >Add Drive Folder</a>
                                </li>
                            </ul>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-expanded="false">Account <span class="caret"></span></a>
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
                        out.write("<td>"+bms.get(i).getLastEditDate()+"</td>");
                        out.write("<td>"+bms.get(i).getFatherFolder()+"</td>");
                        out.write("</tr>");
                    }
                %>
            </tbody>
        </table>       
    </body>

</html>
