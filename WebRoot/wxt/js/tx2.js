$(document).ready(function(){
	var access_token = request.getParameter("access_token");
	var telephone = window.localStorage.getItem("user.telephone");
	$("#save").click(function(){
		if($("#coin").val() == ''){
			layer.open({
				content: '金币数不能为空！',
				shadeClose: false,
				anim: true,
				fixed: true,
				btn: ['确&nbsp;定']
			});
		}else if($("#coin").val() < 8000){
			layer.open({
				content: '提现金币数不能低于8000金币！',
				shadeClose: false,
				anim: true,
				fixed: true,
				btn: ['确&nbsp;定']
			});
		}else{
			$.ajax( {
				type : "post",
				url : "/fmp/user/user_withdraw.do?telephone="+telephone+"&coin="+$("#coin").val()+"&access_token="+access_token,
				dataType : "json",
				async : false,
				timeout : 10000,
				beforeSend : function(){
				
				},
				success : function(dataJson) {
					if (dataJson.status == "ok") {
						if(dataJson.code == "0"){
							window.location.href="me.html";
						}else if(dataJson.code == "2"){
							layer.open({
								content: '金币必须为数字，请重新验证手机提交！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定'],
								yes: function(){
									window.location.href="tx.html";
								}
							});
						}if(dataJson.code == "3"){
							layer.open({
								content: '余额不足！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定'],
								yes: function(){
									window.location.href="me.html";
								}
							});
						}if(dataJson.code == "4"){
							layer.open({
								content: '提现金币数不能低于8000金币！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定'],
								yes: function(){
									window.location.href="me.html";
								}
							});
						}else{
							layer.open({
								content: '操作超时！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定'],
								yes: function(){
									window.location.href="tx.html";
								}
							});
						}
					}else{
						layer.open({
							content: '服务器异常！',
							shadeClose: false,
							anim: true,
							fixed: true,
							btn: ['确&nbsp;定'],
								yes: function(){
								window.location.href="tx.html";
							}
						});
					}
				},
				error : function(){
					layer.open({
						content: '网络异常！',
						shadeClose: false,
						anim: true,
						fixed: true,
						btn: ['确&nbsp;定'],
							yes: function(){
							window.location.href="tx.html";
						}
					});
				}
				
			});
		}
	});
});