function login(username,password){
    $.post( "Login", 
            {user: username,psw:password},
            function(responseText) {
                       if(responseText=="true"){
                           setCookie("username",username,1);
                           console.log(find_os_version());
                           onLogin();
                        }
                        else 
                           window.alert("Invalid username or password")
                    }
        );   
}

function find_os_version() {
  var ua = navigator.userAgent.toLowerCase();
  if (ua.indexOf("windows nt 5.0") != -1) {
    return 'win2k';
  }
  if (ua.indexOf("windows nt 5.1") != -1) {
    return 'winXP';
  }
  if (ua.indexOf("windows nt 6.0") != -1) {
    return 'winVista';
  }
  if (ua.indexOf("windows nt 6.1") != -1) {
    return 'win7';
  }
  if (ua.indexOf("windows nt 6.2") != -1) {
    return 'win8';
  }
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
