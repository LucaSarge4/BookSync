<%@page import="DataAccessTier.Destination"%>
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
        <title>My Devices</title>
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

            $('#destinationTable tbody').on( 'click', 'tr', function () {
                if ( $(this).hasClass('selected') ) {
                    $(this).removeClass('selected');
                }
                else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
            } );

            $('#mybookmarks').click( function () {
                window.open ('mybookmarks.jsp','_self',false);
            } );
            
            
        } );
        </script>
    </head>
    
    <body>
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
                            <a id="dropbox" >Add DropBox Path</a>
                        </li>
                        <li>
                            <a id="drive" >Add Drive Path</a>
                        </li>
                        <li>
                            <a id="edit" >Edit</a>
                        </li>
                        <li>
                            <a id="mybookmarks" >My Bookmarks</a>
                        </li>
                        <li>
                            <a id="logout" >Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        
        <table class="table table-hover display" id="destinationTable">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Os</th>
                    <th>Broswer</th>
                    <th>Dropbox</th>
                    <th>Drive</th>
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
                    LinkedList <Destination> bms = new LinkedList();
                    //bms = bl.getDestinations(username);
                    
                    for(int i=0;i<bms.size();i++){
                        out.write("<tr>");
                        out.write("<td>"+bms.get(i).getDevice()+"</td>");
                        out.write("<td>"+bms.get(i).getOS()+"</td>");
                        out.write("<td>"+bms.get(i).getBrowser()+"</td>");
                        out.write("<td>"+bms.get(i).getDropPath()+"</td>");
                        out.write("</tr>");
                    }
                %>
            </tbody>
        </table>       
    </body>

</html>

