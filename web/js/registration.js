function registration(username,firstName,lastName,password,email,country){
    var d = new Date();
    var dataString = d.toLocaleDateString()+ " "+d.toLocaleTimeString();
    $.post( "Registration", 
            {user: username,firstName: firstName,lastName: lastName,psw:password,
                email: email,country: country,regdate:dataString},
            function(responseText) {
                       //if(responseText=="true"){
                            onReg();
                            setCookie("username",username,1);
                        //}
                        ///else 
                          // window.alert("Invalid data");
                    }
        ); 
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function onReg(){
    window.open ('mybookmarks.jsp','_self',false);
}