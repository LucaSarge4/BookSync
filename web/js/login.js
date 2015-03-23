function login(username,password){
    $.post( "Login", 
            {user: username,psw:password},
            function(responseText) {
                       if(responseText=="true"){
                           setCookie("username",username,1);
                           onLogin();
                        }
                        else 
                           window.alert("Invalid username or password")
                    }
        );   
}

function onLogin(){
    window.open ('mybookmarks.jsp','_self',false);
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
