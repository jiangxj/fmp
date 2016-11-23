var request = {
	urlStr : window.location.href+"",
	getParameterDict : function(){
		if(this.urlStr.indexOf('?')<0){
			return {};
		}
		var params = this.urlStr.split('?')[1].split('&');
		var paramDict = {};
		for (var i=0; i<params.length; i++) {
			if(params[i].indexOf('=')>0){
				var vals = params[i].split('=');
				paramDict[vals[0]] = vals[1];
			}else{
				paramDict[params[i]] = '';
			}
		}
		return paramDict;
	},
	getParameter : function(parameter){
		return this.getParameterDict()[parameter];
	}
}
