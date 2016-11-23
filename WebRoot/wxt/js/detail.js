var p_id = request.getParameter("p_id");
var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
var is_outdate = "";
$(document).ready(function(){
	loadData();
	$("#go").click(function(){
		if(is_outdate == '1'){
			return;
		}
		if(islogin == "1" && telephone != null && "" != telephone){
			$.ajax( {
				type : "post",
				url : "/fmp/project/project_join.do?p_id="+p_id+"&telephone="+telephone,
				dataType : "json",
				async : false,
				success : function(dataJson) {
					if (dataJson.status == "ok") {
						if(dataJson.code == "0"){
							layer.open( {
								content : '请务必使用此账户去注册/登录其他平台，否则将不能享受本系统的返利！',
								shadeClose : false,
								anim : true,
								fixed : true,
								btn : [ '确&nbsp;定' ],
								yes: function(){
									window.location.href = dataJson.data;
								}
							});
							
						}else if(dataJson.code == "1"){
							layer.open( {
								content : '项目已过期！',
								shadeClose : false,
								anim : true,
								fixed : true,
								btn : [ '确&nbsp;定' ]
							});
						}
					}
				}
			});
		}else{
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
		}
	});
});
var loadData = function(){
	$.ajax( {
		type : "post",
		url : "/fmp/project/project_detail.do?p_id="+p_id+"&telephone="+telephone,
		dataType : "json",
		async : false,
		success : function(dataJson) {
			if (dataJson.status == "ok" && dataJson.code == "0") {
				var project = dataJson.data.project;
				$("#rule01").html(project.p_rule.replace(/\n/g,"<br>"));
				$("#rule02").html(project.p_notice.replace(/\n/g,"<br>"));
				$("#platform").html(project.platform);
				$("#funds_trust").html(project.funds_trust);
				$("#guarantee_agency").html(project.guarantee_agency);
				$("#security_level").html(project.security_level);
				$("#registered_capital").html(project.registered_capital);
				$("#district").html(project.district);
				$("#onlinedate").html(project.onlinedate);
				$("#icp_record").html(project.icp_record);
				$(".ad_image_wrap").html("<img src='"+project.p_detail_image+"' />");
//				var guide_list = dataJson.data.guide_list;
//				var guideHtml = "";
//				for(var i=0; i<guide_list.length; i++){
//					guideHtml = guideHtml + "<li><h5>" + guide_list[i].g_title + "</h5>";
//					if(guide_list[i].g_content != ""){
//						guideHtml = guideHtml + "<p>"+guide_list[i].g_content+"</p>";
//					}
//					if(guide_list[i].g_image != ""){
//						guideHtml = guideHtml+ "<p><img src='" + guide_list[i].g_image + "' /></p>";
//					}
//					guideHtml = guideHtml + "</li>";
//				}
				$("#rule03").html(project.p_guide);
				is_outdate = project.is_outdate;
				if(project.is_outdate == '1'){
					$("#go").css("background-color","gray");
				}
			}
		}
	});
};