
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
                document.cookie = "destination=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
                window.opener.location.reload();
            }
        </script>
        <div class="section">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 col-md-offset-4">
                        <div class="form-group has-error">
                            <label class="control-label" >Device Name</label>
                            <%  BusinessLogicInterface bl = new BusinessLogic();
                                String username="";
                                String deviceName="";
                                String destID="";
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
                                    if(cookie.getName().equals("destination"))
                                        deviceName=cookie.getValue();
                                }
                                destID=bl.getDestinationID(username,deviceName);
                                
                                out.write("<input class=\"form-control\" id=\"inputDevice\"");
                                out.write("placeholder=\" "+bl.getDestination(username, destID).getDevice()+"\" type=\"text\" name=\"device\" disabled=\"true\">");
                                
                            %>
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" >OS</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"inputOS\"");
                                out.write("placeholder=\" "+bl.getDestination(username, destID).getOS()+"\" type=\"text\" name=\"os\" disabled=\"true\">");
                            %>
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" >Browser</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"inputBrowser\"");
                                out.write("placeholder=\" "+bl.getDestination(username, destID).getBrowser()+"\" type=\"text\" name=\"browser\" disabled=\"true\">");
                            %>
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" >Dropbox Path</label>
                            <%  
                                out.write("<input class=\"form-control\" id=\"inputDropbox\"");
                                out.write("placeholder=\" "+bl.getDestination(username, destID).getDropPath()+"\" type=\"text\" name=\"dropbox\" disabled=\"true\">");
                            %>
                       </div>

                        <input type="button" id="edit" value="Edit" class="active btn btn-success" onclick="editFunction()">
                        
                        <script src="js/getCookie.js"></script>
                        <script> 
                        function editFunction(){
                            if(document.getElementById('edit').value.toString()==="Edit"){
                                document.getElementById('inputDevice').disabled = false;
                                document.getElementById('inputOS').disabled = false;
                                document.getElementById('inputBrowser').disabled = false;
                                document.getElementById('inputDropbox').disabled = false;
                                document.getElementById('edit').value="Confirm";
                            }else{
                                var name = document.getElementById("inputDevice").value.toString();
                                var os = document.getElementById("inputOS").value.toString();
                                var browser = document.getElementById("inputBrowser").value.toString();
                                var dropbox = document.getElementById("inputDropbox").value.toString();
                                $.post( "EditDestination", 
                                        {user: getCookie("username"),ckDev:getCookie("destination"),device:name,os: os,browser:browser,
                                             dropbox: dropbox},
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