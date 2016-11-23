var page = 0;
$(document).ready(function() {
	initData();
	bindEvent();
	loadJqPlugins();
});
var bindEvent = function() {
	
};
var goPojectDetail = function(pid){
	window.location.href = "detail.html?p_id="+pid;
}
var initData = function() {
	loadFocusImage();
	loadboard();
	pullUpAction();
};
var loadboard = function(){
	$.ajax( {
		type : "post",
		url : "/fmp/common_interface/query.do?actionid=10003",
		dataType : "json",
		async : false,
		success : function(dataJson) {
			if (dataJson.status == "ok" && dataJson.code == "0") {
				var dataArray = dataJson.data.datalist;
				$("#mcount").html(format(dataArray[0].mcount,3,","));
				$("#pcount").html(dataArray[0].pcount);
			}
		}
	});
}
var loadFocusImage = function(){
	$.ajax( {
		type : "post",
		url : "/fmp/common_interface/query.do?actionid=10001",
		dataType : "json",
		async : false,
		success : function(dataJson) {
			if (dataJson.status == "ok" && dataJson.code == "0") {
				var dataArray = dataJson.data.datalist;
				var focusImageTemplate = "";
				for (var i = 0; i < dataArray.length; i++) {
					focusImageTemplate = focusImageTemplate + "<div class='swiper-slide'><a href='"+dataArray[i].url+"'><img class='fucus_pic' src='"+dataArray[i].image+"' /></a></div>"
				}
				$(".swiper-wrapper").html(focusImageTemplate);
				var swiper = new Swiper('.swiper-container', {
					pagination: '.swiper-pagination'
				});
			}
		}
	});
};
var myScroll, pullDownEl, pullDownOffset, pullUpEl, pullUpOffset, generatedCount = 0;

function pullDownAction() {
		var el, li, i;
		page = 1;
		$(".data_list ul").html("");
		el = $(".data_list ul").get(0);
		$.ajax( {
				type : "post",
				url : "/fmp/common_interface/query.do?actionid=10000&rand="+Date.parse(new Date())+"&page="
						+ page,
				dataType : "json",
				async : false,
				success : function(dataJson) {
					if (dataJson.status == "ok"
							&& dataJson.code == "0") {
						var dataArray = dataJson.data.datalist;
						for (var i = 0; i < dataArray.length; i++) {
							var data = dataArray[i];
							li = document.createElement('li');
							var labelStr = data.p_labels == 'null' ? ""
									: data.p_labels;
							var labelsHtml = "";
							if (labelStr.split(",").length > 0) {
								var array = labelStr.split(",");
								labelsHtml = "<div class='tips'>";
								for ( var j = 0; j < array.length; j++) {
									labelsHtml = labelsHtml
											+ "<span class='text_tip'>"
											+ array[j] + "</span>"
								}
								labelsHtml = labelsHtml + "</div>"
							}
							var template = "";
							if(data.surplus_amount == 0 || data.is_outdate == '1'){
								template += "<div class='list_item clearfix' tag='"+data.p_id+"' onclick='goPojectDetail(\""+data.p_id+"\")'>"
								+ "<div class='item_left'>"
								+ "<img src='" + data.p_image+ "' /><div class='shadow'><h1>已过期</h1></div></div>"
								+ "<div class='item_right'>"
								+ "<h3>"
								+ data.p_title
								+ "</h3>"
								+ labelsHtml
								+ "<div class='item_right_bottom'>"
								+ "<span>"
								+ "<i class='word_icon'>商</i><label>"
								+ data.platform
								+ "</label>"
								+ "</span>"
								+ "<span>"
								+ "<label>获得金币：</label>"
								+ data.coin
								+ "</span>"
								+ "<div class='syme'>剩余名额："+0+"</div>"
								+ "</div>"
								+ "</div></div>";
							}else{
								template += "<div class='list_item clearfix' tag='"+data.p_id+"' onclick='goPojectDetail(\""+data.p_id+"\")'>"
								+ "<div class='item_left'>"
								+ "<img src='" + data.p_image+ "' /><div class='no_shadow'><h1>" + data.schedule+ "</h1></div></div>"
								+ "<div class='item_right'>"
								+ "<h3>"
								+ data.p_title
								+ "</h3>"
								+ labelsHtml
								+ "<div class='item_right_bottom'>"
								+ "<span>"
								+ "<i class='word_icon'>商</i><label>"
								+ data.platform
								+ "</label>"
								+ "</span>"
								+ "<span>"
								+ "<label>获得金币：</label>"
								+ data.coin
								+ "</span>"
								+ "<div class='syme'>剩余名额："+data.surplus_amount+"</div>"
								+ "</div>"
								+ "</div></div>";
							}
							
							li.innerHTML = template;
							el.appendChild(li);
						}
						bindEvent();
						myScroll.refresh();
					} else {
						layer.open( {
							content : '服务器异常',
							shadeClose : false,
							anim : true,
							fixed : true,
							btn : [ '确&nbsp;定' ]
						});
					}
				}
			});
}

function pullUpAction() {
		var el, li, i;
		page = page + 1;
		el = $(".data_list ul").get(0);
		$.ajax( {
				type : "post",
				url : "/fmp/common_interface/query.do?actionid=10000&page="
						+ page,
				dataType : "json",
				async : false,
				success : function(dataJson) {
					if (dataJson.status == "ok"
							&& dataJson.code == "0") {
						var dataArray = dataJson.data.datalist;
						if(dataArray.length<=0){
							pullUpEl.className = '';
							pullUpEl.querySelector('.pullUpLabel').innerHTML = '没有更多数据啦';
							return;
						}
						for (var i = 0; i < dataArray.length; i++) {
							var data = dataArray[i];
							li = document.createElement('li');
							var labelStr = data.p_labels == 'null' ? ""
									: data.p_labels;
							var labelsHtml = "";
							if (labelStr.split(",").length > 0) {
								var array = labelStr.split(",");
								labelsHtml = "<div class='tips'>";
								for ( var j = 0; j < array.length; j++) {
									labelsHtml = labelsHtml
											+ "<span class='text_tip'>"
											+ array[j] + "</span>"
								}
								labelsHtml = labelsHtml + "</div>"
							}
							var template = "";
							if(data.surplus_amount == 0  || data.is_outdate == '1'){
								template += "<div class='list_item clearfix' tag='"+data.p_id+"' onclick='goPojectDetail(\""+data.p_id+"\")'>"
								+ "<div class='item_left'>"
								+ "<img src='" + data.p_image+ "' /><div class='shadow'><h1>已过期</h1></div></div>"
								+ "<div class='item_right'>"
								+ "<h3>"
								+ data.p_title
								+ "</h3>"
								+ labelsHtml
								+ "<div class='item_right_bottom'>"
								+ "<span>"
								+ "<i class='word_icon'>商</i><label>"
								+ data.platform
								+ "</label>"
								+ "</span>"
								+ "<span>"
								+ "<label>获得金币：</label>"
								+ data.coin
								+ "</span>"
								+ "<div class='syme'>剩余名额："+0+"</div>"
								+ "</div>"
								+ "</div></div>";
							}else{
								template += "<div class='list_item clearfix' tag='"+data.p_id+"' onclick='goPojectDetail(\""+data.p_id+"\")'>"
								+ "<div class='item_left'>"
								+ "<img src='" + data.p_image+ "' /><div class='no_shadow'><h1>" + data.schedule+ "</h1></div></div>"
								+ "<div class='item_right'>"
								+ "<h3>"
								+ data.p_title
								+ "</h3>"
								+ labelsHtml
								+ "<div class='item_right_bottom'>"
								+ "<span>"
								+ "<i class='word_icon'>商</i><label>"
								+ data.platform
								+ "</label>"
								+ "</span>"
								+ "<span>"
								+ "<label>获得金币：</label>"
								+ data.coin
								+ "</span>"
								+ "<div class='syme'>剩余名额："+data.surplus_amount+"</div>"
								+ "</div>"
								+ "</div></div>";
							}
							li.innerHTML = template;
							el.appendChild(li);
						}
						bindEvent();
						myScroll.refresh();
					} else {
						layer.open( {
							content : '服务器异常',
							shadeClose : false,
							anim : true,
							fixed : true,
							btn : [ '确&nbsp;定' ]
						});
					}
				}
			});
				

}

function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');
	pullUpOffset = pullUpEl.offsetHeight;

	myScroll = new iScroll(
			'wrapper',
			{
				useTransition : true,
				topOffset : pullDownOffset,
				onRefresh : function() {
					if (pullDownEl.className.match('loading')) {
						pullDownEl.className = '';
						pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
					} else if (pullUpEl.className.match('loading')) {
						pullUpEl.className = '';
						pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
					}
				},
				onScrollMove : function() {
					if (this.y > 5 && !pullDownEl.className.match('flip')) {
						pullDownEl.className = 'flip';
						pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手刷新...';
						this.minScrollY = 0;
					} else if (this.y < 5 && pullDownEl.className.match('flip')) {
						pullDownEl.className = '';
						pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
						this.minScrollY = -pullDownOffset;
					} else if (this.y < (this.maxScrollY - 5)
							&& !pullUpEl.className.match('flip')) {
						pullUpEl.className = 'flip';
						pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手刷新...';
						this.maxScrollY = this.maxScrollY;
					} else if (this.y > (this.maxScrollY + 5)
							&& pullUpEl.className.match('flip')) {
						pullUpEl.className = '';
						pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
						this.maxScrollY = pullUpOffset;
					}
				},
				onScrollEnd : function() {
					if (pullDownEl.className.match('flip')) {
						pullDownEl.className = 'loading';
						pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
						pullDownAction(); // Execute custom function (ajax
											// call?)
					} else if (pullUpEl.className.match('flip')) {
						pullUpEl.className = 'loading';
						pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
						pullUpAction(); // Execute custom function (ajax call?)
					}
				}
			});

	
}
var format = function(str, step, splitor) {
    str = str.toString();
    var tail = str.substring(str.indexOf("."));
    str = str.substring(0, str.indexOf("."));
    var len = str.length;
    
    if(len > step) {
         var l1 = len%step, 
             l2 = parseInt(len/step),
             arr = [],
             first = str.substr(0, l1);
         if(first != '') {
             arr.push(first);
         };
         for(var i=0; i<l2 ; i++) {
             arr.push(str.substr(l1 + i*step, step));                                    
         };
         str = arr.join(splitor);
     };
     return str+tail;
}
document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);

document.addEventListener('DOMContentLoaded', function() {
	setTimeout(loaded, 200);
}, false);
