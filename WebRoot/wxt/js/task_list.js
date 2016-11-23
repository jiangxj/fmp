var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
var mySwiper;
$(document).ready(function(){
	loadData();
	$("#pagenavi").find("a").click(function() {
		var currIndex = $("#pagenavi").find("a").index($(this));
		mySwiper.slideTo(currIndex)
		sliderBlock(currIndex);
	});
});
var loadData = function(){
	if(islogin == "1" && telephone != null && "" != telephone){
		$.ajax( {
			type : "post",
			url : "/fmp/user/user_project.do?telephone="+telephone,
			dataType : "json",
			async : false,
			timeout : 10000,
			success : function(dataJson) {
				if (dataJson.status == "ok") {
					if (dataJson.code == "0") {
						var unpassArray = dataJson.data.unpass;
						var tempHtml1 = "";
						if(unpassArray.length == 0){
							tempHtml1 = tempHtml1 + "<div class='empty'>没有未完成的任务哦！</div>";
						}else{
							tempHtml1 = tempHtml1 + "<ul>";
							for(var i=0; i<unpassArray.length; i++){
								var data = unpassArray[i];
								tempHtml1 = tempHtml1 + "<li><div class='task_item clearfix'><div class='logo'><img src='"+data.p_image+"' /></div><div class='item_right'><div class='item_right_top'><p>"+data.p_title+"</p></div><div class='item_right_bottom'><p>任务状态：<strong>"+data.statusmsg+"</strong></p><p>更新时间：<strong>"+data.modifydate+"</strong></p></div></div></div></li>";
							}
							tempHtml1 = tempHtml1 + "</ul>";
						}
						
						$("#unpass").html(tempHtml1);
						
						var passArray = dataJson.data.pass;
						var tempHtml2 = "";
						if(passArray.length == 0){
							tempHtml2 = tempHtml2 + "<div class='empty'>没有已完成的任务哦！</div>";
						}else{
							tempHtml2 = tempHtml2 + "<ul>";
							for(var i=0; i<passArray.length; i++){
								var data = passArray[i];
								tempHtml2 = tempHtml2 + "<li><div class='task_item clearfix'><div class='logo'><img src='"+data.p_image+"' /></div><div class='item_right'><div class='item_right_top'><p>"+data.p_title+"</p></div><div class='item_right_bottom'><p>任务状态：<strong>"+data.statusmsg+"</strong></p><p>更新时间：<strong>"+data.modifydate+"</strong></p></div></div></div></li>";
							}
							tempHtml2 = tempHtml2 + "</ul>";
						}
						$("#pass").html(tempHtml2);
					}
					mySwiper = new Swiper('.swiper-container', {
						onInit: function(swiper) {
							swiper.container[0].style.height = $(swiper.slides[swiper.activeIndex]).find(".task_list").outerHeight(true) + 'px';
						},
						onSlideChangeEnd: function(swiper) {
							swiper.container[0].style.height = $(swiper.slides[swiper.activeIndex]).find(".task_list").outerHeight(true) + 'px';
							sliderBlock(swiper.activeIndex);
						}
					});
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
};
var sliderBlock = function(index) {
	$("#pagenavi").find("a").each(function(tempIndex) {
		if (tempIndex == index) {
			if (!$("#pagenavi").find("a").eq(tempIndex).hasClass("active")) {
				$("#pagenavi").find("a").eq(tempIndex).addClass("active");
			}
		} else {
			if ($("#pagenavi").find("a").eq(tempIndex).hasClass("active")) {
				$("#pagenavi").find("a").eq(tempIndex).removeClass("active");
			}
		}
	});
};