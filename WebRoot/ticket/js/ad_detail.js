var ad_id;
var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function() {
	initData();
	updateUI();
});

var updateUI = function() {
	var currWidth = $(document).width();
	$(".adPic").css("width", currWidth + "px");
	$(".adPic").css("height", currWidth / 2.6 + "px");
	$(".adTitle .title").css("width", currWidth*0.6 + "px");
	
	$(".adTitle .btn").click(function() {
		if(islogin != '1'){
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
		}else{
			window.location.href = "ad_ticket.html?ad_id="+ad_id;
		}
	});
};
var initData = function() {
	ad_id = request.getParameter("ad_id");
	$.ajax({
		type: "post",
		url: "/fmp/common_interface/query.do?actionid=10006&ad_id=" + ad_id,
		dataType: "json",
		success: function(dataJson) {
			if (dataJson.status == "ok" && dataJson.code == "0") {
				var data = dataJson.data.datalist[0];
				$(".adPic").attr("src", data.ad_image);
				$(".title").html(data.ad_title);
				$("#canyu").html(data.canyu);
				$("#shengyu").html(parseInt(data.total)-parseInt(data.canyu));
				$("#datelimit").html(data.startdate + "-" + data.enddate);
				$(".adContent").html("<pre>" + data.ad_content + "</pre>");
			}
		}
	});
};