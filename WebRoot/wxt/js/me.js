var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function() {
	if(islogin == "1" && telephone != null && "" != telephone){
		$("#login").html("<a href='#' ><img id='loingPic' src='img/icon_me_selected.png' /><p style='color:#fff;'>"+telephone+"</p></a>");
		$.ajax( {
			type : "post",
			url : "/fmp/user/account_info.do?telephone="+telephone,
			dataType : "json",
			async : false,
			timeout : 10000,
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if (dataJson.code == "0") {
						$("#balance").text(dataJson.data.balance);
						$("#today_income").text(dataJson.data.today_income);
					}
				}
			}
		});
	}
	
	$("#exit").click(function(event){
		layer.open( {
			content : '是否确定退出！！',
			shadeClose : false,
			anim : true,
			fixed : true,
			btn : [ '确&nbsp;定','取&nbsp;消'],
			yes : function() {
				layer.closeAll();
				window.localStorage.removeItem("user.islogin");
				window.localStorage.removeItem("user.telephone");
				window.location.reload();
			},
			no : function() {
				layer.closeAll();
			}
		});
	});
	
	$("#account").click(function() {
		window.location.href = "account.html";
	});
});
