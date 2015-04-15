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
                            <label class="control-label" >Folder Name</label>
                            <input class="form-control" id="inputFolderName"
                                   placeholder="Enter Folder Name" type="text" name="name">
                        </div>
                        <div class="form-group has-error">
                            <label class="control-label" >Father Folder</label>
                            <input class="form-control" id="inputFatherFolder"
                                   placeholder="FatherFolder" type="text" name="fatherFolder">
                        </div>

                        <input type="button" value="Confirm" class="active btn btn-success" onclick="addFunction()">
                        
                        <script src="js/getCookie.js"></script>
                        <script> 
                        function addFunction(){
                            var folderName = document.getElementById("inputFolderName").value.toString();
                            var url = "";
                            var desc = "";
                            var tag = "";
                            var type = "Web";
                            var fatherfolder = document.getElementById("inputFatherFolder").value.toString();
                            var d = new Date();
                            var dataString = d.toLocaleDateString()+ " "+d.toLocaleTimeString();
                            var username = getCookie("username");
                            $.post( "NewBookmark", 
                                    {userid: username,title: folderName,url: url,lasteditdate:dataString,
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