<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理系统</title>
<%@include file="taglibs.jsp" %>
<script type="text/javascript">
	function openTab(text,url){
		if($("#tabs").tabs("exists",text)){
			$("#tabs").tabs("select",text);
		}else{
			var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/"+url+"'></iframe>";
			$("#tabs").tabs("add",{
				title:text,
				closable:true,
				content:content
			});
		}
	}
    function ajaxsubmit(url,data){
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            dataType: "json",
            contentType:"application/x-www-form-urlencoded",
            success: function(result){
                alert("启动串口监听服务器完成");
                $("#startbtn").hide();
            },
            error:function(result){

                alert("连接服务器异常");
            }
        });
    }
	$(function(){
		openTab('猪肉信息','index/topiginfo');
	});
</script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 80px;background-color: #E0ECFF">
	<h1>猪肉嘌呤管理系统</h1>

</div>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
		<button id="startbtn" onclick="ajaxsubmit('/serial/start','')">启动端口服务</button>
	</div>
</div>
<div region="west" style="width: 180px; padding: 10px" title="导航菜单" split="true">
	<a href="javascript:openTab('猪肉信息查询管理','index/topiginfo')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉信息查询管理</a>
	<a href="javascript:openTab('猪肉信息统计管理','index/tostatic')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉信息统计管理</a>
	<a href="javascript:openTab('猪肉重量信息管理','index/topigweight')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉重量信息管理</a>
	<a href="javascript:openTab('猪肉宽度信息管理','index/topigwidth')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉宽度信息管理</a>
	<a href="javascript:openTab('底磅信息管理','index/topigpounds')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">底磅信息管理</a>
	<a href="javascript:openTab('用户信息管理','index/touser')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">用户信息管理</a>
	<a href="/login/dologinout" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">退出登录</a>
</div>
<div region="south" style="height: 25px;padding: 5px" align="center">
	猪肉嘌呤称重管理系统 V1.0 All Rights Reserved
</div>
 
</body>
</html>