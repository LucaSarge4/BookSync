function login(username,password){
    console.log("Name: "+username);
    console.log("Password: "+password);
    var response="response";
    $.post( "Login", 
            {user: username,psw:password},
            function(responseText) {
                       response = responseText;
                       if(response=="true"){
                            onLogin();
                        }
                        else 
                           console.log("login non effettuato"); 
                    }
        );   
}

function onLogin(){
    window.open ('mybookmarks.jsp','_self',false);
}
