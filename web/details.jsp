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
        <link href="css/booksync.css" rel="stylesheet">
        <link rel="icon" href="images\logo.png" type="image/png" />
        <style>
            body{
                background-color: #04718E;
                }
        </style>
        <script src="js/jquery-2.1.3.js"></script>
        <script src="js/getCookie.js"></script>
    </head>
    
    <body onunload="resetCookie()">
        <script src="js/bootstrap.min.js"></script>
        <script>
            function resetCookie(){
                document.cookie = "url=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                window.opener.location.reload();
            }
        </script>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="form-group has-error">
                            <label class="control-label" >Title</label>
                            <%  BusinessLogicInterface bl = new BusinessLogic();
                                String username="";
                                String url="";
                                String bookID="";
                                Cookie cookie = null;
                                Cookie[] cookies = null;
                                // Get an array of Cookies associated with this domain
                                cookies = request.getCookies();
                                for (int i = 0; i < cookies.length; i++){
                                    cookie = cookies[i];
                                    if(cookie.getName().equals("username"))
                                        username=cookie.getValue();
                                }
                                for (int i = 0; i < cookies.length; i++){
                                    cookie = cookies[i];
                                    if(cookie.getName().equals("url"))
                                        url = cookie.getValue();
                                }
                                bookID=bl.getBookID(username,url);
                                out.write("<input class=\"form-control\" id=\"inputTitle\"");
                                out.write("placeholder=\" "+bl.getBookmark(username, bookID).getTitle()+"\" type=\"text\" name=\"title\" disabled=\"true\">");
                                
                            %>
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" >Url</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"inputUrl\"");
                                out.write("placeholder=\" "+bl.getBookmark(username, bookID).getUrl()+"\" type=\"text\" name=\"url\" disabled=\"true\">");
                            %>
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" >Description</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"inputDescription\"");
                                out.write("placeholder=\" "+bl.getBookmark(username, bookID).getDescription()+"\" type=\"text\" name=\"description\" disabled=\"true\">");
                            %>
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" >Tag</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"inputTag\"");
                                out.write("placeholder=\" "+bl.getBookmark(username, bookID).getTag()+"\" type=\"text\" name=\"tag\" disabled=\"true\">");
                            %>
                       </div>
                        <div class="form-group has-error" id="folderDet">
                            <label class="control-label" >Father Folder</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"fatherFolder\"");
                                out.write("placeholder=\" "+bl.getBookmark(username, bookID).getFatherFolder()+"\" type=\"text\" name=\"fatherFolder\" disabled=\"true\">");
                            %>
                        </div>
                        
                        <div class="form-group has-error" id="folder" hidden="true">
                            <label class="control-label" >Father Folder</label>
                            <select  class="form-control" id="inputFatherFolder">
                                <option>Booksync</option>
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
                                    LinkedList <Bookmark> bms = new LinkedList();
                                    bms = bl.getBookmarks(username);

                                    for(int i=0;i<bms.size();i++){
                                        if(bms.get(i).getUrl().equals("")){
                                            out.write("<option>"+bms.get(i).getTitle()+"</option>");
                                        }
                                    }
                                %>
                            </select>
                       </div>

                        <input type="button" id="edit" value="Edit" class="active btn btn-success" onclick="editFunction()">
                        
                        <script src="js/getCookie.js"></script>
                        <script> 
                        function editFunction(){
                            if(document.getElementById('edit').value.toString()==="Edit"){
                                document.getElementById('inputTitle').disabled = false;
                                document.getElementById('inputUrl').disabled = false;
                                document.getElementById('inputDescription').disabled = false;
                                document.getElementById('inputTag').disabled = false;
                                document.getElementById('folderDet').hidden = true;
                                document.getElementById('folder').hidden = false;
                                document.getElementById('edit').value="Confirm";
                            }else{
                                var title = document.getElementById("inputTitle").value.toString();
                                var url = document.getElementById("inputUrl").value.toString();
                                var desc = document.getElementById("inputDescription").value.toString();
                                var tag = document.getElementById("inputTag").value.toString();
                                var fatherfolder = document.getElementById("inputFatherFolder").value.toString();
                                var d = new Date();
                                var dataString = d.toLocaleDateString()+ " "+d.toLocaleTimeString();
                                $.post( "EditBookmark", 
                                        {user: getCookie("username"),ckUrl:getCookie("url"),title: title,url: url,lasteditdate:dataString,
                                            fatherfolder: fatherfolder, description: desc, tag: tag},
                                        function(responseText) {
                                            console.log(responseText);
                                                    window.close();
                                                    window.opener.location.reload();
                                                }
                                    );
                                }
                            }
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>