function exportDeviceBookmarks(){
    var user = getCookie("username");
    var dev = getCookie("destination");
    $.post( "BookmarkList",
        {username: user,device:dev},
        function(responseText){
            var textFileAsBlob = new Blob([responseText], {type:'text/html'});
            var d = new Date();                                        
            var dataString = d.toLocaleDateString()+ " "+d.toLocaleTimeString();
            var fileNameToSaveAs = "Booksync "+dataString;
            var downloadLink = document.createElement("a");
            downloadLink.download = fileNameToSaveAs;
            downloadLink.innerHTML = "Download File";
            if (window.webkitURL != null)
            {
                    // Chrome allows the link to be clicked
                    // without actually adding it to the DOM.
                    downloadLink.href = window.webkitURL.createObjectURL(textFileAsBlob);
            }
            else
            {
                    // Firefox requires the link to be added to the DOM
                    // before it can be clicked.
                    downloadLink.href = window.URL.createObjectURL(textFileAsBlob);
                    downloadLink.onclick = destroyClickedElement;
                    downloadLink.style.display = "none";
                    document.body.appendChild(downloadLink);
            }

            downloadLink.click();

    });
}

