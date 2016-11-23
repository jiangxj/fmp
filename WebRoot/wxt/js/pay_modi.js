var timer, s = 60;
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function(){
	$("#next").click(function(){
		if($("#checkcode").val() == ''){
			layer.open({
				content: '验证码未输入！',
				shadeClose: false,
				anim: true,
				fixed: true,
				btn: ['确&nbsp;定']
			});
		}else{
			//校验验证码
			$.ajax( {
				type : "post",
				url : "/fmp/user/verify_authcode.do?telephone="+telephone+"&checkcode="+$("#checkcode").val(),
				dataType : "json",
				async : false,
				timeout : 10000,
				beforeSend : function(){
				
				},
				success : function(dataJson) {
					if (dataJson.status == "ok") {
						if(dataJson.code == "0"){
							s = 60;
							clearInterval(timer);
							window.location.href="pay_modi2.html?telephone="+telephone+"&access_token="+dataJson.data.access_token;
						}else if(dataJson.code == "1"){
							layer.open({
								content: '验证码输入错误！',
								shadeClose: false,
								anim: true,
								fixed: true,
								btn: ['确&nbsp;定'],
								yes: function(){
									$("#checkcode").val("");
									$("#checkcode").attr("readonly","readonly");
									layer.closeAll();
								}
							});
						}else if(dataJson.code == "2"){
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
	$("#codebtn").click(function(){

		$("#checkcode").removeAttr("readonly");
		$("#codebtn").css("background-color", "#999")
		if(s < 60){
			return;
		}
		timer = setInterval(function(){
			$("#codebtn").text(s--);
			if(s < 0){
				clearInterval(timer)
				$("#codebtn").text("重新获取");
				$("#codebtn").css("background-color", "#FF4070")
				s = 60;
			}
		},1000);
		
		//发送验证码到手机
		$.ajax( {
			type : "post",
			url : "/fmp/user/send_authcode.do?telephone="+telephone,
			dataType : "json",
			async : false,
			timeout : 10000,
			beforeSend : function(){
			
			},
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if(dataJson.code == "0"){
						
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
	
	});
});