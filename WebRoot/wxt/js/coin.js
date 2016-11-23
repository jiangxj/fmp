var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function(){
	if(islogin == "1" && telephone != null && "" != telephone){
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
						var b = parseFloat(dataJson.data.balance);
						if(b>=8000){
							$("#next").attr("class","button_input");
							if(dataJson.data.alipay_account == '1'){
								$("#next").click(function(){
									window.location.href = "tx.html";
								});
							}else{
								$("#next").click(function(){
									layer.open( {
										content : '支付宝帐号未绑定！！',
										shadeClose : false,
										anim : true,
										fixed : true,
										btn : [ '确&nbsp;定']
									});
								});
							}
						}
						
					}
				}
			}
		});
		
		$.ajax( {
			type : "post",
			url : "/fmp/user/withdraw_list.do?telephone="+telephone,
			dataType : "json",
			async : false,
			timeout : 10000,
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if (dataJson.code == "0") {
						var arr = dataJson.data.list;
						var liHtml = "";
						for(var i=0;i<arr.length;i++){
							liHtml += "<li><span>"+arr[i].createdate+"</span><span>"+arr[i].coin+"</span><span>"+arr[i].status+"</span></li>";
						}
						$("#list").append(liHtml);
					}
				}
			}
		});
	}
});