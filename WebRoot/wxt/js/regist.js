var btnFlag = true, s = 60, access_token = "";
$(document).ready(function() {

	$("#regist_btn").click(function() {
		if(!$("#agree").attr("checked") == "checked"){
			layer.open( {
				content : '请认真阅读并同意条款！',
				shadeClose : false,
				anim : true,
				fixed : true,
				btn : [ '确&nbsp;定' ]
			});
			return ;
		}
		if (!btnFlag) {
			return;
		}
		userDataVerify();

	});

	$("#codebtn").click(function() {
		if ($("#telephone").val() == '') {
			layer.open( {
				content : '手机号未输入！',
				shadeClose : false,
				anim : true,
				fixed : true,
				btn : [ '确&nbsp;定' ]
			});
		} else {
			$("#codebtn").css("background-color", "#999")
			if (s < 60) {
				return;
			}
			sendCheckcodeRequest();
		}
	});
});
var sendRegistRequest = function(){
	$.ajax( {
		type : "post",
		url : "/fmp/user/regist.do",
		dataType : "json",
		async : false,
		timeout : 10000,
		data : {
			"telephone" : $("#telephone").val(),
			"password" : $("#password").val(),
			"code" : $("#code").val(),
			"access_token" : access_token
		},
		success : function(dataJson) {
			if (dataJson.status == "ok") {
				if (dataJson.code == "0") {
					layer.open( {
						content : '注册成功！',
						shadeClose : false,
						anim : true,
						fixed : true,
						btn : [ '确&nbsp;定' ],
						yes : function() {
							window.location.href = "login.html";
						}
					});
				} else if (dataJson.code == "1") {
					layer.open( {
						content : '用户已注册，不能重复注册！',
						shadeClose : false,
						anim : true,
						fixed : true,
						btn : [ '确&nbsp;定' ],
						yes : function() {
							layer.closeAll();
							btnFlag = true;
						}
					});
				} else if (dataJson.code == "2") {
					layer.open( {
						content : '不正确的手机号！',
						shadeClose : false,
						anim : true,
						fixed : true,
						btn : [ '确&nbsp;定' ],
						yes : function() {
							layer.closeAll();
							btnFlag = true;
						}
					});
				}else if (dataJson.code == "3") {
					layer.open( {
						content : '非法注册！',
						shadeClose : false,
						anim : true,
						fixed : true,
						btn : [ '确&nbsp;定' ],
						yes : function() {
							layer.closeAll();
							btnFlag = true;
						}
					});
				}
			} else {
				layer.open( {
					content : '服务器异常！',
					shadeClose : false,
					anim : true,
					fixed : true,
					btn : [ '确&nbsp;定' ],
					yes : function() {
						layer.closeAll();
						btnFlag = true;
					}
				});
			}
		},
		error : function() {
			layer.open( {
				content : '网络异常！',
				shadeClose : false,
				anim : true,
				fixed : true,
				btn : [ '确&nbsp;定' ],
				yes : function() {
					layer.closeAll();
					btnFlag = true;
				}
			});
		}

	});
};

var sendCheckcodeRequest = function(){
	$("#checkcode").removeAttr("readonly");
	timer = setInterval(function() {
		$("#codebtn").text(s--);
		if (s < 0) {
			clearInterval(timer)
			$("#codebtn").text("重新获取");
			$("#codebtn").css("background-color", "#FF4070")
			s = 60;
		}
	}, 1000);

	// 发送验证码到手机
	$.ajax( {
		type : "post",
		url : "/fmp/user/send_authcode.do?telephone=" + $("#telephone").val() +"&rand="+Date.parse(new Date()),
		dataType : "json",
		async : false,
		timeout : 10000,
		success : function(dataJson) {
			if (dataJson.status == "ok") {
				if (dataJson.code == "0") {

				} else {
					layer.open( {
						content : '操作超时！',
						shadeClose : false,
						anim : true,
						fixed : true,
						btn : [ '确&nbsp;定' ],
						yes : function() {
							layer.closeAll();
							btnFlag = true;
						}
					});
				}
			} else {
				layer.open( {
					content : '服务器异常！',
					shadeClose : false,
					anim : true,
					fixed : true,
					btn : [ '确&nbsp;定' ],
					yes : function() {
						layer.closeAll();
						btnFlag = true;
					}
				});
			}
		},
		error : function() {
			layer.open( {
				content : '网络异常！',
				shadeClose : false,
				anim : true,
				fixed : true,
				btn : [ '确&nbsp;定' ],
				yes : function() {
					layer.closeAll();
					btnFlag = true;
				}
			});
		}
	});
};
var userDataVerify = function() {
	var flag = false;
	btnFlag = false;
	if ($("#telephone").val() == "") {
		btnFlag = false;
		layer.open( {
			content : '手机号不能为空！',
			shadeClose : false,
			anim : true,
			fixed : true,
			btn : [ '确&nbsp;定' ],
			yes : function() {
				btnFlag = true;
				layer.closeAll();
			}
		});
	} else if ($("#checkcode").val() == "") {
		layer.open( {
			content : '验证码不能为空！',
			shadeClose : false,
			anim : true,
			fixed : true,
			btn : [ '确&nbsp;定' ],
			yes : function() {
				btnFlag = true;
				layer.closeAll();
			}
		});
	} else if ($("#password").val() == "") {
		layer.open( {
			content : '密码不能为空！',
			shadeClose : false,
			anim : true,
			fixed : true,
			btn : [ '确&nbsp;定' ],
			yes : function() {
				btnFlag = true;
				layer.closeAll();
			}
		});
	} else if ($("#password").val() != $("#new_password").val()) {
		layer.open( {
			content : '两次输入密码不一致！',
			shadeClose : false,
			anim : true,
			fixed : true,
			btn : [ '确&nbsp;定' ],
			yes : function() {
				btnFlag = true;
				layer.closeAll();
			}
		});
	} else {
		$.ajax( {
			type : "post",
			url : "/fmp/user/single_verify_authcode.do?telephone="
					+ $("#telephone").val() + "&checkcode=" + $("#checkcode").val(),
			dataType : "json",
			async : false,
			timeout : 10000,
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if (dataJson.code == "0") {
						access_token = dataJson.data.access_token;
						sendRegistRequest();
					} else {
						layer.open( {
							content : '验证码错误！',
							shadeClose : false,
							anim : true,
							fixed : true,
							btn : [ '确&nbsp;定' ],
							yes : function() {
								layer.closeAll();
								flag = false;
								btnFlag = true;
							}
						});
					}
				}
			},
			error : function() {
				layer.open( {
					content : '验证码校验失败，网络异常！',
					shadeClose : false,
					anim : true,
					fixed : true,
					btn : [ '确&nbsp;定' ],
					yes : function() {
						layer.closeAll();
						flag = false;
						btnFlag = true;
					}
				});
			}
		});
		flag = true;
	}
	return flag;
};