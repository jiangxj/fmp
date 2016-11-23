<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>附件上传</title>
    <script type="text/javascript">
    	var json = eval("<%=reuqest.getAttribute("data"); %>");
    	function init(){
    		if(json != null && json.status == 'ok'){
    			parent.uploadCallback();
    		}
    	}
    </script>
  </head>
  
  <body onload="init()">
  	<form action="/eklm/attachment/upload.do" method="post" enctype="multipart/form-data">
  		附件：<input type="file" name="attachment"><br/>
  		<input type="submit" value="提 交"/>
  	</form>
  </body>
</html>
