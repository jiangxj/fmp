$(document).ready(function(){
	var islogin = window.localStorage.getItem("user.islogin");
	var telephone = window.localStorage.getItem("user.telephone");
	if(islogin != null && islogin == "1"){
		$.ajax( {
			type : "post",
			url : "/fmp/user/user_info.do?telephone="+telephone,
			dataType : "json",
			async : false,
			timeout : 10000,
			beforeSend : function(){
			
			},
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if(dataJson.code == "0"){
						$("#telephone").text(dataJson.data.user.telephone);
						if(dataJson.data.user.alipay_account == ""){
							$("#alipay").html("<strong style='color:red;'>未绑定</strong>");
						}else{
							$("#alipay").html(dataJson.data.user.alipay_account);
						}
					}
				}else{
					layer.open({
						content: '服务器异常！',
						shadeClose: false,
						anim: true,
						fixed: true,
						btn: ['确&nbsp;定']
					});
				}
			},
			error : function(){
				layer.open({
					content: '网络异常！',
					shadeClose: false,
					anim: true,
					fixed: true,
					btn: ['确&nbsp;定']
				});
			}
			
		});
	}else{
		layer.open({
			content: '请先登录',
			shadeClose: false,
			anim: true,
			fixed: true,
			btn: ['确&nbsp;定'],
			yes: function(){
				window.location.href = "login.html";
			}
		});
	}
	
});