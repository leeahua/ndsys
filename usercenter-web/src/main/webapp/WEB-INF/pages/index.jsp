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
    document.onkeydown = function (e) {
        if (!e) e = window.event;
        if ((e.keyCode || e.which) == 118 ) {//f7

            $.ajax({
                type: "POST",
                url: '/pigpound/getforpage',
                data: {},
                dataType: "json",
                contentType:"application/json",
                success: function(result){
                    console.log(result);
                    console.log(result.rows[0]);
                    if (result.total == 0){
                        alert("请先在等级设置里面添加等级信息！");
                    } else {
						var row = result.rows[0];
                        $('#indexdlg').dialog('open').dialog('setTitle','编辑窗口');
                        $('#indexfm').form('load',row);
                        url = '/pigpound/update?id='+row.id;
                    }
                },
                error:function(data){

                    alert("处理失败");
                }
            });
        }
    }
	$(function(){
		openTab('服务器信息管理','index/towelcome');

	});
    function savePound(){
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify($("#indexfm").serializeObject()),
            dataType: "json",
            contentType:"application/json",
            success: function(result){
                if (result.code != "00"){
                    alert(result.msg);
                } else {
                    $('#indexdlg').dialog('close');
                    $('#indexdg').datagrid('reload');	// reload the user data
                    alert(result.msg);// close the dialog
                }
            },
            error:function(data){

                alert("处理失败");
            }
        });

    }
    /**
     * 将表单数据转换为JSON格式数据
     * */
    $.fn.serializeObject = function()
    {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
</script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 80px;background-color: #E0ECFF">
	<h1>猪肉嘌呤管理系统</h1>

</div>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
	</div>
</div>
<div region="west" style="width: 180px; padding: 10px" title="导航菜单" split="true">
	<a href="javascript:openTab('猪肉信息查询管理','index/topiginfo')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉信息查询管理</a>
	<a href="javascript:openTab('猪肉信息统计管理','index/tostatic')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉信息统计管理</a>
	<a href="javascript:openTab('猪肉重量信息管理','index/topigweight')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉重量信息管理</a>
	<a href="javascript:openTab('猪肉宽度信息管理','index/topigwidth')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">猪肉宽度信息管理</a>
	<a href="javascript:openTab('底磅信息管理','index/topigpounds')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">底磅信息管理</a>
	<a href="javascript:openTab('等级阀值设置','index/tolevel')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">等级信息设置</a>
	<a href="javascript:openTab('用户信息管理','index/touser')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">用户信息管理</a>
	<a href="javascript:openTab('服务器信息管理','index/towelcome')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">服务器信息管理</a>
	<a href="/login/dologinout" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-large-shapes'">退出登录</a>
</div>


<div id="indexdlg" class="easyui-dialog" style="width:200px;height:180px;padding:10px 20px"
	 closed="true" buttons="#dlg-buttons">
	<form id="indexfm" method="post" >
		<div style="margin-bottom:10px">
			<input class="easyui-textbox" name="botpounds" style="width:100%" data-options="label:'底磅重(kg):',required:true">
			<input class="easyui-textbox" name="batchNum" style="width:100%" data-options="label:'批次号:',required:true">
		</div>
	</form>
</div>
<div id="dlg-buttons">
	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePound()">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#indexdlg').dialog('close')">取消</a>
</div>
<div region="south" style="height: 25px;padding: 5px" align="center">
	猪肉嘌呤称重管理系统 V1.0 All Rights Reserved
</div>
 
</body>
</html>