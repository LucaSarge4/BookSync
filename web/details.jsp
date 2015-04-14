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
    
    <body>
        <script src="js/bootstrap.min.js"></script>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="form-group has-error">
                            <label class="control-label" >Title</label>
                            <%  BusinessLogicInterface bl = new BusinessLogic();
                                String username="";
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
                                    if(cookie.getName().equals("url")){
                                        bookID=bl.getBookID(username,cookie.getValue());
                                    }
                                }
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
                        <div class="form-group has-error">
                            <label class="control-label" >FatherPath</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"inputFatherFolder\"");
                                out.write("placeholder=\" "+bl.getBookmark(username, bookID).getFatherFolder()+"\" type=\"text\" name=\"fatherFolder\" disabled=\"true\">");
                            %>
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
                                document.getElementById('inputFatherFolder').disabled = false;
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