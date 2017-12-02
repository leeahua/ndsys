<%@ page language="java" import="java.util.*" pageEncoding="utf-8" autoFlush="true"%>
<!DOCTYPE html>
<html>
<head>
    <title>SystemPlatform</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@include file="taglibs.jsp" %>
    <script type="application/javascript" src="/static/js/pigwidth.js"></script>
</head>

<body style="margin: 1px;">
    <table id="dg" title="猪肉采集宽度信息维护列表" class="easyui-datagrid"
       toolbar="#toolbar" idField="itemid"  pagination="true" rownumbers="true"
       fitColumns="true" singleSelect="true" fit="true">
        <thead>
        <tr>
            <th field="id" width="10" type='hidden'>id</th>
            <th field="pigNum" width="10">序号</th>
            <th field="pigBatchNo" width="30">批次号</th>
            <th field="pigWidth" width="10">宽度</th>
            <%--<th field="pigWeight" width="20">重量</th>--%>
            <th field="pigColor" width="20">颜色</th>
            <th field="pigLevel" width="20" >级别</th>
            <%--<th field="chargeMan" width="50">负责人</th>--%>
            <th field="createTime" width="35" formatter="formatDateTime">创建时间</th>
            <th field="updateTime" width="35" formatter="formatDateTime">更新时间</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar"><!--查询div-->
        <form id="ff">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                      <%--  更新时间 从: <input class="easyui-datebox"  style="width:120px">
                        到: <input class="easyui-datebox" style="width:120px">--%>
                        批次号：&nbsp;<input type="text" id="pigBatchNo" class="easyui-textbox" size="15" onkeydown="if(event.keyCode==13) search()"/>
                        <%--平台编码：&nbsp;<input type="text" id="platformCode" class="easyui-textbox" size="15" onkeydown="if(event.keyCode==13) search()"/>--%>
                        <%--激活状态：&nbsp;<select style="width:150px" id="platformStatus" class="easyui-combobox">
                            <option value="">请选择</option>
                            <option value="0">未激活</option>
                            <option value="1">已激活</option>
                        </select>--%>
                        <a href="javascript:search()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" title="搜索"  >搜索</a>
                        <a href="javascript:reset()" class="easyui-linkbutton" data-options="iconCls:'icon-reload'"  title="重置" >重置</a>
                    </td>
                </tr>
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
    <div id="dlg" class="easyui-dialog" style="width:400px;height:400px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post" >
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" name="pigBatchNo" style="width:100%" data-options="label:'批次号:',required:true">
            </div>
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" name="pigNum" style="width:100%" data-options="label:'序号:',required:true">

            </div >
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" name="pigWidth" style="width:100%" data-options="label:'宽度:',required:true">
            </div>
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" name="pigLevel" style="width:100%" data-options="label:'级别:',required:true">

            </div>
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" name="pigColor" style="width:100%" data-options="label:'颜色:',required:true">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
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
        $('#dlg').dialog('open').dialog('setTitle','新增窗口');
        $('#fm').form('clear');
        url = '/pigwidth/save';
    }
    function editUser(){
        var row = $('#dg').datagrid('getSelected');
        console.log(row);
        if (row){
            $('#dlg').dialog('open').dialog('setTitle','编辑窗口');
            $('#fm').form('load',row);
            url = '/pigwidth/update?id='+row.id;
        }
    }
    function saveUser(){
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify($("#fm").serializeObject()),
            dataType: "json",
            contentType:"application/json",
            success: function(result){
                if (result.code != "00"){
                    alert(result.msg);
                } else {
                    $('#dlg').dialog('close');		// close the dialog
                    $('#dg').datagrid('reload');	// reload the user data
                }
            },
            error:function(data){
                var result = eval('('+data+')');
                alert(result.msg);
            }
        });

    }
    function destroyUser(){
        var row = $('#dg').datagrid('getSelected');
        if (row){
            $.messager.confirm('警告','您确定要删除这条记录么?',function(r){
                if (r){
                    $.post('/pigwidth/delete',{id:row.id},function(result){
                        alert(result.msg);
                        if (result.code=="00"){
                            $('#dg').datagrid('reload');	// reload the user data
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



