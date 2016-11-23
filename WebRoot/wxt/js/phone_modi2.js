$(document).ready(function(){
	var access_token = request.getParameter("access_token");
	var telephone = window.localStorage.getItem("user.telephone");
	$("#save").click(function(){
		if($("#new_telephone").val() == ''){
			layer.open({
				content: '手机号不能为空！',
				shadeClose: false,
				anim: true,
				fixed: true,
				btn: ['确&nbsp;定']
			});
		}else{
			$.ajax( {
				type : "post",
				url : "/fmp/user/telephone_modify.do?telephone="+telephone+"&access_token="+access_token+"&new_telephone="+$("#new_telephone").val(),
				dataType : "json",
				async : false,
				timeout : 10000,
				beforeSend : function(){
				
				},
				success : function(dataJson) {
					if (dataJson.status == "ok") {
						if(dataJson.code == "0"){
							window.location.href="login.html";
						}else if(dataJson.code == "1"){
							layer.open({
								content: '该手机号已经被绑定，不能重复绑定！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定']
							});
						}else if(dataJson.code == "2"){
							layer.open({
								content: '操作超时，请重新验证手机号！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定'],
								yes: function(){
									layer.closeAll();
									window.location.href = "phone_modi.html";
							}
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