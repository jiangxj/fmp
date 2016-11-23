var page = 0;
var islogin = window.localStorage.getItem("user.islogin");
var telephone = window.localStorage.getItem("user.telephone");
$(document).ready(function() {
	if (islogin == "1" && telephone != null && "" != telephone) {
		initData();
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
var initData = function() {
	pullUpAction();
};
var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

function pullDownAction() {
	setTimeout(function() {
		var el, li, i;
		page = 1;
		document.getElementById('data_list').innerHTML = "";
		el = document.getElementById('data_list');
		$.ajax({
			type: "post",
			url: "/fmp/common_interface/query.do?actionid=10007&page="+page+"&telephone="+telephone,
			dataType: "json",
			async: false,
			success: function(dataJson) {
				if (dataJson.status == "ok"
					&& dataJson.code == "0") {
					var dataArray = dataJson.data.datalist;
					for (i = 0; i < dataArray.length; i++) {
						var data = dataArray[i];
						li = document.createElement('li');
						var template = "<div onclick='javascript:window.location.href=ad_ticket.html?ad_id=\""+data.ad_id+"\";' class='item clearfix'><img src='"+data.ad_thumb+"' /><p>"+data.ad_title+"</p></div>";
						li.innerHTML = template;
						el.insertBefore(li, el.childNodes[0]);
					}
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
			url: "/fmp/common_interface/query.do?actionid=10007&page="+page+"&telephone="+telephone,
			dataType: "json",
			async: false,
			success: function(dataJson) {
				if (dataJson.status == "ok"
					&& dataJson.code == "0") {
					var dataArray = dataJson.data.datalist;
					for (i = 0; i < dataArray.length; i++) {
						var data = dataArray[i];
						li = document.createElement('li');
						var template = "<div class='item clearfix'><img src='"+data.ad_thumb+"' /><p>"+data.ad_title+"</p></div>";
						li.innerHTML = template;
						el.appendChild(li, el.childNodes[0]);
					}
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