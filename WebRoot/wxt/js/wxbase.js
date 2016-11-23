var src = document.scripts[document.scripts.length - 1].src;
var share;
var initShare = function() {
	var paramStr = src.substring(src.indexOf("?") + 1);
	var params = paramStr.split("&");
	for ( var i = 0; i < params.length; i++) {
		var kvs = params[i].split("=");
		if (kvs[0] == "share") {
			share = parseInt(kvs[1]);
			break;
		}
	}
};
var onShare = function(type){
	$.ajax( {
		type : "post",
		url : "/fmp/share/share_do.do?type="+type+"&telephone="+window.localStorage.getItem("user.telephone"),
		dataType : "json",
		async : false,
		success : function(dataJson) {
		}
	});
};
var initWeiXin = function(){
	$.ajax( {
		type : "post",
		url : "/fmp/wx_jsapi/sign.do?appid=wx9c542dd7c5d61186&url="+window.location.href,
		dataType : "json",
		async : false,
		success : function(dataJson) {
			if (dataJson.status == "ok" && dataJson.code == "0") {
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: 'wx9c542dd7c5d61186', // 必填，公众号的唯一标识//da02cffebfb528b81c8fd8ba117aa55f
				    timestamp: dataJson.data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: dataJson.data.nonceStr, // 必填，生成签名的随机串
				    signature: dataJson.data.signature,// 必填，签名，见附录1
				    jsApiList: [
						'onMenuShareTimeline',
						'onMenuShareAppMessage',
						'onMenuShareQQ',
						'onMenuShareWeibo',
						'hideOptionMenu',
						'showOptionMenu',
						'chooseImage',
						'previewImage',
						'uploadImage'
					] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				wx.ready(function(){
					wx.onMenuShareAppMessage({
						title: '易搜财邀请有礼，高额返利', // 分享标题
					    desc: '易搜财，一站式金融理财超市，投资除享P2P平台收益以外，另外还可获得本平台额外返现', // 分享描述
					    link: 'http://m.soumoney.net/fmp/wxt/invitation_share.html', // 分享链接
					    imgUrl: 'http://m.soumoney.net/attachment/image/7fd1a5c2aba84ce4b106d1860f76cfaf.JPG', // 分享图标
					    type: 'link', // 分享类型,music、video或link，不填默认为link
					    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
					    success: function () { 
							onShare("0");
					    },
					    cancel: function () { 
					    	//alert("取消好友分享");
					    }
					});
					wx.onMenuShareTimeline({
						title: '易搜财邀请有礼，高额返利', // 分享标题
						link: 'http://m.soumoney.net/fmp/wxt/invitation_share.html', // 分享链接
						imgUrl: 'http://m.soumoney.net/attachment/image/7fd1a5c2aba84ce4b106d1860f76cfaf.JPG', // 分享图标
					    success: function () { 
							onShare("1");
					    },
					    cancel: function () { 
					    	//alert("取消朋友圈分享");
					    }
					});
					wx.onMenuShareQQ({
						title: '易搜财邀请有礼，高额返利', // 分享标题
					    desc: '易搜财，一站式金融理财超市，投资除享P2P平台收益以外，另外还可获得本平台额外返现', // 分享描述
					    link: 'http://m.soumoney.net/fmp/wxt/invitation_share.html', // 分享链接
					    imgUrl: 'http://m.soumoney.net/attachment/image/7fd1a5c2aba84ce4b106d1860f76cfaf.JPG', // 分享图标
					    type: 'link', // 分享类型,music、video或link，不填默认为link
					    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
					    success: function () { 
							onShare("2");
					    },
					    cancel: function () { 
					    	//alert("取消好友分享");
					    }
					});
					wx.onMenuShareWeibo({
						title: '易搜财邀请有礼，高额返利', // 分享标题
					    desc: '易搜财，一站式金融理财超市，投资除享P2P平台收益以外，另外还可获得本平台额外返现', // 分享描述
					    link: 'http://m.soumoney.net/fmp/wxt/invitation_share.html', // 分享链接
					    imgUrl: 'http://m.soumoney.net/attachment/image/7fd1a5c2aba84ce4b106d1860f76cfaf.JPG', // 分享图标
					    type: 'link', // 分享类型,music、video或link，不填默认为link
					    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
					    success: function () { 
							onShare("3");
					    },
					    cancel: function () { 
					    	//alert("取消好友分享");
					    }
					});
					wx.onMenuShareQZone({
						title: '易搜财邀请有礼，高额返利', // 分享标题
					    desc: '易搜财，一站式金融理财超市，投资除享P2P平台收益以外，另外还可获得本平台额外返现', // 分享描述
					    link: 'http://m.soumoney.net/fmp/wxt/invitation_share.html', // 分享链接
					    imgUrl: 'http://m.soumoney.net/attachment/image/7fd1a5c2aba84ce4b106d1860f76cfaf.JPG', // 分享图标
					    type: 'link', // 分享类型,music、video或link，不填默认为link
					    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
					    success: function () { 
							onShare("4");
					    },
					    cancel: function () { 
					    	//alert("取消好友分享");
					    }
					});
					
					/*
					wx.chooseImage({
					    count: 1, // 默认9
					    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
					    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
					    success: function (res) {
					        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					    }
					});
					*/
				});
				wx.error(function(res){
					
				});
			}
		}
	});
};
(function(window, undefined){
	initShare();
	if (window.WeixinJSBridge) {
		onBridgeReady();
	} else {
		if (document.addEventListener) {
			document.addEventListener("WeixinJSBridgeReady", onBridgeReady, false);
		} else if (document.attachEvent) {
			document.attachEvent("WeixinJSBridgeReady", onBridgeReady);
			document.attachEvent("onWeixinJSBridgeReady", onBridgeReady);
		}
	}
	function onBridgeReady(){
		initWeiXin();
		initShareBtn()
	}
	function initShareBtn(){
		if(share == 1) {
			WeixinJSBridge.invoke("showOptionMenu");
		}else{
			WeixinJSBridge.invoke("hideOptionMenu");
		}
	}

})(this);