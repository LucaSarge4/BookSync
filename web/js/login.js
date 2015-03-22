function login(username,password){
    $.post( "Login", 
            {user: username,psw:password},
            function(responseText) {
                       if(responseText=="true"){
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
