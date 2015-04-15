<%@page import="DataAccessTier.Bookmark"%>
<%@page import="java.util.LinkedList"%>
<%@page import="BusinessLogicTier.BusinessLogic"%>
<%@page import="BusinessLogicTier.BusinessLogicInterface"%>
<!DOCTYPE html>
<html>
    
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>BookSync new bookmark</title>
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/booksync.css" rel="stylesheet">
        
        <link rel="icon" href="images\logo.png" type="image/png" />
        <style>
            body{
                background-size: 100% auto;
                background-color: #04718E;
                }
        </style>
        <script src="js/jquery-2.1.3.js"></script>
    </head>
    
    <body>
        <script src="js/bootstrap.min.js"></script>
        
        
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="form-group has-error">
                            <label class="control-label" >Type</label>
                            <select  class="form-control" id="inputBookType" onclick="getType()">
                                <option></option>
                                <option>Web</option>
                                <option>Cloud</option>
                            </select>
                       </div>
                        
                        <div class="form-group has-error" id="title" hidden="true">
                            <label class="control-label" >Title</label>
                            <input class="form-control" id="inputTitle"
                                   placeholder="Enter Title" type="text" name="title">
                        </div>
                        <div class="form-group has-error" id="webUrl" hidden="true">
                            <label class="control-label" >Url</label>
                            <input class="form-control" id="inputUrl"
                                   placeholder="Enter Url" type="text" name="url">
                        </div>
                        <div class="form-group has-error" id="cloudUrl" hidden="true">
                            <label class="control-label" >Url</label>
                            <input type="file" id="fileURL"/>
                        </div>
                        <div class="form-group has-error" id="desc" hidden="true">
                            <label class="control-label" >Description</label>
                            <input class="form-control" id="inputDescription"
                                   placeholder="Enter Description" type="text" name="description">
                        </div>
                        <div class="form-group has-error" id="tag" hidden="true">
                            <label class="control-label" >Tag</label>
                            <select  class="form-control" id="inputBookTag">
                                <option></option>
                                <option>Sport</option>
                                <option>News</option>
                                <option>Life</option>
                            </select>
                       </div>
                        <div class="form-group has-error" id="folder" hidden="true">
                            <label class="control-label" >Father Folder</label>
                            <select  class="form-control" id="inputFatherFolder">
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
                                            out.write("<option>"+bms.get(i).getTitle()+"</option>");
                                        }
                                    }
                                %>
                            </select>
                       </div>

                            <input type="button" value="Confirm" id="conf" class="active btn btn-success" disabled="true" onclick="addFunction()">
                        
                        <script>
                            function getType(){
                                if(document.getElementById("inputBookType").value==="Web"){
                                    document.getElementById("title").hidden = false;
                                    document.getElementById("webUrl").hidden = false;
                                    document.getElementById("cloudUrl").hidden = true;
                                    document.getElementById("desc").hidden = false;
                                    document.getElementById("tag").hidden = false;
                                    document.getElementById("folder").hidden = false;
                                    document.getElementById("conf").disabled = false;
                                }
                                if(document.getElementById("inputBookType").value==="Cloud"){
                                    document.getElementById("title").hidden = false;
                                    document.getElementById("cloudUrl").hidden = false;
                                    document.getElementById("webUrl").hidden = true;
                                    document.getElementById("desc").hidden = false;
                                    document.getElementById("tag").hidden = false;
                                    document.getElementById("folder").hidden = false;
                                    document.getElementById("conf").disabled = false;
                                }   
                                if(document.getElementById("inputBookType").value===""){
                                    document.getElementById("title").hidden = true;
                                    document.getElementById("cloudUrl").hidden = true;
                                    document.getElementById("webUrl").hidden = true;
                                    document.getElementById("desc").hidden = true;
                                    document.getElementById("tag").hidden = true;
                                    document.getElementById("folder").hidden = true;
                                    document.getElementById("conf").disabled = true;
                                }  
                            }
                        </script>
                        
                        
                        <script src="js/getCookie.js"></script>
                        <script> 
                        function addFunction(){
                            var title = document.getElementById("inputTitle").value.toString();
                            var url = document.getElementById("inputUrl").value.toString();
                            var desc = document.getElementById("inputDescription").value.toString();
                            var tag = document.getElementById("inputBookTag").value.toString();
                            var type = document.getElementById("inputBookType").value.toString();
                            var fatherfolder = document.getElementById("inputFatherFolder").value.toString();
                            var d = new Date();
                            var dataString = d.toLocaleDateString()+ " "+d.toLocaleTimeString();
                            var username = getCookie("username");
                            $.post( "NewBookmark", 
                                    {userid: username,title: title,url: url,lasteditdate:dataString,
                                        fatherfolder: fatherfolder,type: type, description: desc, tag: tag},
                                    function(responseText) {
                                            console.log(responseText);
                                            window.close();
                                            window.opener.location.reload();
                                            }
                                ); 
                        }
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
