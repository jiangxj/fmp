$(document).ready(function(){
	$("#submit").click(function(){
		if($("#suggest").val() == ""){
			window.location.href = "me.html";
		}else{
			$.ajax( {
				type : "post",
				url : "/fmp/suggest/suggest_save.do?content="+$("#suggest").val(),
				dataType : "json",
				async : false,
				timeout : 10000,
				success : function(dataJson) {
					if (dataJson.status == "ok") {
						if (dataJson.code == "0") {
							window.location.href = "me.html";
						}
					}
				}
			});
		}
	});
});