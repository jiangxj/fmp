var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function(){
	if (islogin == "1" && telephone != null && "" != telephone) {
		$.ajax( {
			type : "post",
			url : "/fmp/user/invitation_list.do?telephone="+telephone,
			dataType : "json",
			async : false,
			timeout : 10000,
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					var dataArray = dataJson.data.invitation_list;
					if(dataArray instanceof Array){
						var liHtml = "";
						for(var i=0; i< dataArray.length; i++){
							liHtml += "<li><span>"+dataArray[i].telephone+"</span><span>"+dataArray[i].createdate+"</span></li>"
						}
						$("#datalist").append(liHtml);
					}
				} else {
					layer.open( {
						content : '您还未登录，是否去登录！',
						shadeClose : false,
						anim : true,
						fixed : true,
						btn : [ '确&nbsp;定','取&nbsp;消'],
						yes : function() {
							layer.closeAll();
							btnFlag = true;
							window.location.href="login.html";
						},
						no : function() {
							layer.closeAll();
							btnFlag = true;
						}
					});
				}
			}
		});
	}else{
		layer.open( {
			content : '您还未登录，是否去登录！',
			shadeClose : false,
			anim : true,
			fixed : true,
			btn : [ '确&nbsp;定','取&nbsp;消'],
			yes : function() {
				layer.closeAll();
				btnFlag = true;
				window.location.href="login.html";
			},
			no : function() {
				layer.closeAll();
				btnFlag = true;
			}
		});
	}
	
});