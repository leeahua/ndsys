<%@ page language="java" import="java.util.*" pageEncoding="utf-8" autoFlush="true"%>
<!DOCTYPE html>
<html>
<head>
    <title>SystemPlatform</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@include file="taglibs.jsp" %>
    <script type="application/javascript" src="/static/js/piglevel.js"></script>
</head>

<body style="margin: 1px;">
<table id="userdg" title="等级阀值信息维护列表" class="easyui-datagrid"
       toolbar="#toolbar" idField="itemid"  pagination="true" rownumbers="true"
       fitColumns="true" singleSelect="true" fit="true">
    <thead>
    <tr>
        <th field="id"  width="10" type='hidden'>id</th>
        <th field="level1" width="10">等级A</th>
        <th field="level2" width="30">等级B</th>
        <th field="level3" width="10">等级C</th>
        <th field="level4" width="30">等级D</th>

    </tr>
    </thead>
</table>
<div id="toolbar"><!--查询div-->
    <form id="userff">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
                </td>

            </tr>

        </table>

    </form>
</div>
<div id="userdlg" class="easyui-dialog" style="width:280px;height:250px;padding:5px 5px"
     closed="true" buttons="#dlg-buttons">
    <form id="userfm" method="post" >
        <div style="margin-bottom:10px">
            <input class="easyui-numberbox" name="level1" style="width:100%" data-options="label:'等级A:',required:true">
        </div>
        <div style="margin-bottom:10px">
            <input class="easyui-numberbox" name="level2" style="width:100%" data-options="label:'等级B:',required:true">
        </div>
        <div style="margin-bottom:10px">
            <input class="easyui-numberbox" name="level3" style="width:100%" data-options="label:'等级C:',required:true">
        </div>
        <div style="margin-bottom:10px">
            <input class="easyui-numberbox" name="level4" style="width:100%" data-options="label:'等级D:',required:true">
        </div>

    </form>
</div>
<div id="dlg-buttons">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#userdlg').dialog('close')">取消</a>
</div>
</body>
<script>
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
    function newUser(){
        $('#userdlg').dialog('open').dialog('setTitle','新增窗口');
        $('#userfm').form('clear');
        url = '/level/save';
    }
    function editUser(){
        var row = $('#userdg').datagrid('getSelected');
        if (row){
            $('#userdlg').dialog('open').dialog('setTitle','编辑窗口');
            $('#userfm').form('load',row);
            url = '/level/update?id='+row.id;
        }
    }
    function saveUser(){
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify($("#userfm").serializeObject()),
            dataType: "json",
            contentType:"application/json",
            success: function(result){
                if (result.code != "00"){
                    $('#userdlg').dialog('close');
                } else {
                    $('#userdg').datagrid('reload');	// reload the user data
                }
                alert(result.msg);

            },
            error:function(data){

                alert("处理失败");
            }
        });

    }
    function destroyUser(){
        var row = $('#userdg').datagrid('getSelected');
        if (row){
            $.messager.confirm('警告','您确定要删除这条记录么?',function(r){
                if (r){
                    $.post('/level/delete',{id:row.id},function(result){
                        alert(result.msg);
                        if (result.code=="00"){
                            $('#userdg').datagrid('reload');	// reload the user data
                        }
                    },'json');
                }
            });
        }
    }
    function formatterStatus(value, row, index){
        if(value==0){
            return "未激活";
        }else if(value==1){
            return "已激活";
        }
    }
    function formatDateTime(value,row,index){
        var date = new Date(value);
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1);
        var day = date.getDate().toString();
        var hour = date.getHours().toString();
        var minutes = date.getMinutes().toString();
        var seconds = date.getSeconds().toString();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
    }
</script>

</html>



