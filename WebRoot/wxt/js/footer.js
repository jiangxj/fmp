var index = -1;
var src = document.scripts[document.scripts.length - 1].src;
var initIndex = function() {
	var paramStr = src.substring(src.indexOf("?") + 1);
	var params = paramStr.split("&");
	for ( var i = 0; i < params.length; i++) {
		var kvs = params[i].split("=");
		if (kvs[0] == "index") {
			index = parseInt(kvs[1]);
			break;
		}
	}
};
initIndex();
window.onload = function() {
	var data = [ {
		"selected" : {
			"img" : "img/icon_homepage_selected.png",
			"color" : "#FF4070"
		},
		"normal" : {
			"img" : "img/icon_homepage_normal.png",
			"color" : "darkgray"
		},
		"url" : "/fmp/wxt/index.html",
		"text" : "首页"
	}, {
		"selected" : {
			"img" : "img/icon_miaoshenghuo_selected.png",
			"color" : "#FF4070"
		},
		"normal" : {
			"img" : "img/icon_miaoshenghuo_normal.png",
			"color" : "darkgray"
		},
		"url" : "/fmp/ticket/ad_list.html",
		"text" : "优惠"
	}, {
		"selected" : {
			"img" : "img/icon_me_selected.png",
			"color" : "#FF4070"
		},
		"normal" : {
			"img" : "img/icon_me_normal.png",
			"color" : "darkgray"
		},
		"url" : "/fmp/wxt/me.html",
		"text" : "个人中心"
	} ]
	loadFooter(data);
};
var loadFooter = function(data) {
	var footerHtml = "<div class='line_gray'></div><ul>";
	for ( var i = 0; i < data.length; i++) {
		if (i == index) {
			footerHtml = footerHtml
					+ "<li><a href='javascript:void(0)' style='color:"
					+ data[i].selected.color + "'><img src='"
					+ data[i].selected.img + "' /><br/>" + data[i].text
					+ "</a></li>";
		} else {
			footerHtml = footerHtml + "<li><a href='" + data[i].url
					+ "' style='color:" + data[i].normal.color + "'><img src='"
					+ data[i].normal.img + "' /><br/>" + data[i].text
					+ "</a></li>";
		}

	}
	footerHtml = footerHtml + "</ul>";
	var footerNode = document.createElement("footer");
	footerNode.setAttribute("class", "footer");
	footerNode.innerHTML = footerHtml;
	document.getElementById("wrap").appendChild(footerNode);
};
