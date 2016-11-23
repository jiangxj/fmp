var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function() {
	updateUI();
});
var updateUI = function() {
	$(".successInfo").css("width", $(document).width() + "px");
	$(".successInfo").css("height", ($(document).height() * 0.4) + "px");
	loadData();
};
var loadData = function() {
	var ad_id = request.getParameter("ad_id");
	var cid = window.sessionStorage.getItem("cid");
	if (window.localStorage.getItem("adflag") == ad_id) {
		$(".ticketInfo div h3").html(window.localStorage.getItem("adticket"));
	} else {
		$.ajax({
			type: "post",
			url: "/fmp/adertising/ticket.do?telephone="+telephone+"&ad_id=" + ad_id,
			dataType: "json",
			success: function(result) {
				if (result.status == "ok") {
					if (result.code == "0") {
						layer.open({
							content: '对不起，您来晚了...',
							shadeClose: false,
							anim: true,
							fixed: true,
							btn: ['确&nbsp;定']
						});
					} else if (result.code == "1") {
						$(".ticketInfo div h3").html(result.data.ticket);
						window.localStorage.setItem("adflag", ad_id);
						window.localStorage.setItem("adticket", result.data.ticket);
					} else if (result.code == "2") {
						layer.open({
							content: '非法访问',
							shadeClose: false,
							anim: true,
							fixed: true,
							btn: ['确&nbsp;定']
						});
					} else if (result.code == "3") {
						layer.open({
							content: '您已经领取过了，不能重复领取！',
							shadeClose: false,
							anim: true,
							fixed: true,
							btn: ['确&nbsp;定']
						});
					}
				} else {
					layer.open({
						content: '服务器异常',
						shadeClose: false,
						anim: true,
						fixed: true,
						btn: ['确&nbsp;定']
					});
				}
			}
		});
	}

};