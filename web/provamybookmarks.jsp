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
                if ( $(this).hasClass('selected') ) {
                    $(this).removeClass('selected');
                }
                else {
                    table.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                    var urlText =  escape(table.row('.selected').data()[1]) + ";";
                    document.cookie="url="+urlText;
                }
            } );

            $('#open').click( function () {
                window.open(table.row('.selected').data()[1]);
            } );
            
            $('#delete').click( function () {
               $.post( "DeleteBookmark", 
                    {username: getCookie("username"),url:table.row('.selected').data()[1]},
                    function() {
                                window.location.reload();
                                }
                );
            } );
            
            $('#details').click( function () {
                
                var x = screen.width/2 - 700/2;
                var y = screen.height/2 - 450/2;
                window.open(
                    'details.jsp','Bookmark','width=600,height=500,toolbar=0,\n\
                    menubar=0,location=no,addressbar=no,status=1,scrollbars=0,\n\
                    resizable=0,left='+x+',top='+y);
                    return false;
            } );
            
            $('#device').click( function () {
                 window.open ('mydevices.jsp','_self',false);
            } );
            
            $('#logout').click( function () {
                document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                document.cookie = "password=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                window.open('/BookSync/','_self');
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
                            <a id="open" >Open</a>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-expanded="false">New <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li>
                                    <a onclick="openPopupNewB()" target="_blank" > New Bookmark</a>
                                    <script>
                                        function openPopupNewB(){
                                            var x = screen.width/2 - 700/2;
                                            var y = screen.height/2 - 450/2;
                                            window.open(
                                                'newBookmark.jsp','Bookmark','width=600,height=450,toolbar=0,\n\
                                                menubar=0,location=no,addressbar=no,status=1,scrollbars=0,\n\
                                                resizable=1,left='+x+',top='+y);
                                                return false;
                                        }
                                    </script>
                                </li>
                                <li class="divider"></li>
                                <li>
                                    <a onclick="openPopupNewF()" target="_blank" > New Folder</a>
                                    <script>
                                        function openPopupNewF(){
                                            var x = screen.width/2 - 700/2;
                                            var y = screen.height/2 - 450/2;
                                            window.open(
                                                'newFolder.jsp','Bookmark','width=600,height=300,toolbar=0,\n\
                                                menubar=0,location=no,addressbar=no,status=1,scrollbars=0,\n\
                                                resizable=1,left='+x+',top='+y);
                                                return false;
                                        }
                                    </script>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a id="delete" >Delete</a>
                        </li>
                        <li>
                            <a id="details" >Details</a>
                        </li>
                        <li>
                            <a id="device" >My Devices</a>
                        </li>
                        <li>
                            <a id="logout" >Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <form action="provamybookmarks.jps" method="POST">
            <div class="form-group has-error" id="folder" width="200" style="float: Left;">
                <label class="control-label" >Father Folder</label>
                <select  name="folder" class="form-control" id="inputFatherFolder">
                    <option>All Folder</option>
                    <option>Booksync</option>
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
                            if(bms.get(i).getUrl().equals("")){
                                out.write("<option value=\""+bms.get(i).getTitle()+"\"+>"+bms.get(i).getTitle()+"</option>");
                            }
                        }
                    %>
                </select>
           </div>
            <input type="submit" value="click">
        </form>
        
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
                <%  bl = new BusinessLogic();
                    username="";
                    cookie = null;
                    cookies = null;
                    // Get an array of Cookies associated with this domain
                    cookies = request.getCookies();
                    for (int i = 0; i < cookies.length; i++){
                        cookie = cookies[i];
                        if(cookie.getName().equals("username"))
                            username=cookie.getValue();
                    }
                    bms = new LinkedList();
                    bms = bl.getBookmarks(username);
                    
                    for(int i=0;i<bms.size();i++){
                        if(!bms.get(i).getUrl().equals("") ){
                            out.write("<tr>");
                            out.write("<td>"+bms.get(i).getTitle()+" "+request.getParameter("folder")+"</td>");
                            out.write("<td>"+bms.get(i).getUrl()+"</td>");
                            out.write("<td>"+bms.get(i).getTag()+"</td>");
                            out.write("<td>"+bms.get(i).getLastEditDate()+"</td>");
                            out.write("<td>"+bms.get(i).getFatherFolder()+"</td>");
                            out.write("</tr>");
                        }
                        
                    }
                %>
            </tbody>
        </table>       
    </body>

</html>
