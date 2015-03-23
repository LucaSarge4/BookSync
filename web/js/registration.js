function registration(username,password,firstName,lastName,email,country){
    var d = new Date();
    var dataString = d.toLocaleDateString()+ " "+d.toLocaleTimeString();
    $.post( "Registration", 
            {user: username,psw:password,firstName: firstName,lastName: lastName,
                email: email,country: country,data:dataString},
            function(responseText) {
                       if(responseText=="true"){
                            onReg();
                        }
                        else 
                           window.alert("Invalid data");
                    }
        ); 
}

function onReg(){
    window.open ('mybookmarks.jsp','_self',false);
}