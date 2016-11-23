var page = 0;var cid = '';
$(document).ready(function() {
	initData();
});
var updateUI = function() {
	var currWidth = $(document).width();
	var maginLR = 10;
	$(".focus-pic-wrapper").css("width", currWidth + "px")
	$(".wrap").css("width", currWidth + "px");
	$(".fucus_pic").css("width", currWidth + "px");
	$(".fucus_pic").css("height", (currWidth / 2.6 - maginLR) + "px");
	$(".adPic").css("width", (currWidth - maginLR) + "px");
	$(".adPic").css("height", (currWidth / 2.6 - maginLR) + "px");
	$(".adBox").css("width", (currWidth - maginLR) + "px");
	$(".adBox").css("height", (currWidth / 2.6 - maginLR) + "px");
	bindEvent();
};
var bindEvent = function() {
	$(".adPic").click(function() {
		var ad_id = $(this).attr("tag");
		window.location.href = "ad_detail.html?ad_id="+ad_id;
	});
};
var initData = function() {
	pullUpAction();
	loadFocusImage();
};
var loadFocusImage = function(){
	$.ajax( {
		type : "post",
		url : "/fmp/common_interface/query.do?actionid=10005",
		dataType : "json",
		async : false,
		success : function(dataJson) {
			if (dataJson.status == "ok" && dataJson.code == "0") {
				var dataArray = dataJson.data.datalist;
				var focusImageTemplate = "";
				for (var i = 0; i < dataArray.length; i++) {
					focusImageTemplate = focusImageTemplate + "<div class='swiper-slide'><a href=''><img class='fucus_pic' src='"+dataArray[i].ad_image+"' /></a></div>"
				}
				$(".swiper-wrapper").html(focusImageTemplate);
				var swiper = new Swiper('.swiper-container', {
					pagination: '.swiper-pagination'
				});
			}
		}
	});
};
var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

function pullDownAction() {
	setTimeout(function() {
		var el, li, i;
		page = 1;
		el = document.getElementById('data_list');
		$.ajax({
			type: "post",
			url: "/fmp/common_interface/query.do?actionid=10004&page="+page,
			dataType: "json",
			async: false,
			success: function(dataJson) {
				if (dataJson.status == "ok"
					&& dataJson.code == "0") {
					var dataArray = dataJson.data.datalist;
					for (i = 0; i < dataArray.length; i++) {
						var data = dataArray[i];
						li = document.createElement('li');
						var template = "<div class='adBox'><div class='adTitle'><h3>" + data.ad_title + "</h3></div><div class='adBottom'>&nbsp;&nbsp;<br /><br /></div><img class='adPic' tag='"+data.ad_id+"' src='" + data.ad_image + "' /></div>";
						li.innerHTML = template;
						el.insertBefore(li, el.childNodes[0]);
					}
					updateUI();
					myScroll.refresh();
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
	}, 1000); // <-- Simulate network congestion, remove setTimeout from production!
}

function pullUpAction() {
	setTimeout(function() { // <-- Simulate network congestion, remove setTimeout from production!
		var el, li, i;
		page = page+1;
		el = document.getElementById('data_list');
		$.ajax({
			type: "post",
			url: "/fmp/common_interface/query.do?actionid=10004&page="+page,
			dataType: "json",
			async: false,
			success: function(dataJson) {
				if (dataJson.status == "ok"
					&& dataJson.code == "0") {
					var dataArray = dataJson.data.datalist;
					for (i = 0; i < dataArray.length; i++) {
						var data = dataArray[i];
						li = document.createElement('li');
						var template = "<div class='adBox'><div class='adTitle'><h3>" + data.ad_title + "</h3></div><div class='adBottom'>&nbsp;&nbsp;<br /><br /></div><img class='adPic' tag='"+data.ad_id+"' src='" + data.ad_image + "' /></div>";
						li.innerHTML = template;
						el.appendChild(li, el.childNodes[0]);
					}
					updateUI();
					myScroll.refresh(); 
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

	}, 1000); 
}

function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');
	pullUpOffset = pullUpEl.offsetHeight;

	myScroll = new iScroll('wrapper', {
		useTransition: true,
		topOffset: pullDownOffset,
		onRefresh: function() {
			if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
			} else if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更过...';
			}
		},
		onScrollMove: function() {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手刷新...';
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手刷新...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更过...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function() {
			if (pullDownEl.className.match('flip')) {
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';
				pullDownAction(); // Execute custom function (ajax call?)
			} else if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';
				pullUpAction(); // Execute custom function (ajax call?)
			}
		}
	});

	setTimeout(function() {
		document.getElementById('wrapper').style.left = '0';
	}, 800);
}

document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);

document.addEventListener('DOMContentLoaded', function() {
	setTimeout(loaded, 200);
}, false);