window.onload = function() {
	if (document.getElementsByClassName("backarrow").length != 0) {
		document.getElementsByClassName("backarrow")[0].onclick = function() {
			history.back();
		}
	}

};
var loadJavaScript = function(path){
	var oHead = document.getElementsByTagName('head').item(0); 
    var oScript= document.createElement("script"); 
    oScript.type = "text/javascript"; 
    oScript.src=path; 
    oHead.appendChild( oScript);
};
