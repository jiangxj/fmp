$(document).ready(function(){
	var access_token = request.getParameter("access_token");
	var telephone = window.localStorage.getItem("user.telephone");
	$("#save").click(function(){
		if($("#aplipay_account").val() == ''){
			layer.open({
				content: '账号不能为空！',
				shadeClose: false,
				anim: true,
				fixed: true,
				btn: ['确&nbsp;定']
			});
		}else{
			$.ajax( {
				type : "post",
				url : "/fmp/user/alipay_modify.do?telephone="+telephone+"&access_token="+access_token+"&alipay_account="+$("#alipay_account").val(),
				dataType : "json",
				async : false,
				timeout : 10000,
				beforeSend : function(){
				
				},
				success : function(dataJson) {
					if (dataJson.status == "ok") {
						if(dataJson.code == "0"){
							window.location.href="me.html";
						}else{
							layer.open({
								content: '操作超时！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定']
							});
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
		}
	});
});