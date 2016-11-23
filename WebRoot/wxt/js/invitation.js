var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function(){
	if (islogin == "1" && telephone != null && "" != telephone) {
		$.ajax( {
			type : "post",
			url : "/fmp/user/user_info.do?telephone="+telephone,
			dataType : "json",
			async : false,
			timeout : 10000,
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if (dataJson.code == "0") {
						$("#recommend_code").html(dataJson.data.user.recommend_code)
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
	}
	
	$("#go").click(function(){
		if (islogin == "1" && telephone != null && "" != telephone) {
			window.location.href = "invitation_list.html";
		}else {
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
	
});