function login(username,password){
    $.post( "Login", 
            {user: username,psw:password},
            function(responseText) {
                       if(responseText=="true"){
                           onLogin();
                        }
                        else 
                           window.alert("Invalid username or password")
                    }
        );   
}

function onLogin(){
    window.open ('provamybookmarks.jsp','_self',false);
}

