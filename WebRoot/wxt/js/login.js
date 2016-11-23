$(document).ready(function(){
	$("#login_btn").click(function(){
		$.ajax( {
			type : "post",
			url : "/fmp/user/login.do?telephone="+$("#telephone").val()+"&password="+$("#password").val(),
			dataType : "json",
			async : false,
			timeout : 10000,
			beforeSend : function(){
			layer.open({
				content: '正在登陆...',
				shadeClose: false,
				anim: true,
				fixed: true
			});
			},
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if(dataJson.code == "0"){
						window.localStorage.setItem("user.islogin","1");
						window.localStorage.setItem("user.telephone",$("#telephone").val());
						window.location.href = "index.html";
					}else if(dataJson.code == "1"){
						layer.open({
							content: '用户名密码不正确！',
							shadeClose: false,
							anim: true,
							fixed: true,
							btn: ['确&nbsp;定']
						});
					}
					else if(dataJson.code == "2"){
						layer.open({
							content: '用户没有注册！',
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
		
	});
});