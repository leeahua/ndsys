<%@ page language="java" import="java.util.*" pageEncoding="utf-8" autoFlush="true"%>
<!DOCTYPE html>
<html>
<head>
    <title>猪肉管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@include file="taglibs.jsp" %>
    <script type="application/javascript" src="/static/js/piginfo.js"></script>
</head>

<body style="margin: 1px;">
    <table id="dg" title="猪肉信息列表" class="easyui-datagrid"
       toolbar="#toolbar" idField="itemid"  pagination="true" rownumbers="true"
       fitColumns="true" singleSelect="true" fit="true">
        <thead>
        <tr>
            <th field="id" width="10" type='hidden'>id</th>
            <th field="pigNum" width="10">序号</th>
            <th field="pigBatchNo" width="30">批次号</th>
            <th field="pigWidth" width="10">宽度</th>
            <th field="pigWeight" width="20">重量</th>
            <th field="pigColor" width="20">扣款标识</th>
            <th field="pigLevel" width="20" >级别</th>
           <%-- <th field="chargeMan" width="50">负责人</th>--%>
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
                        批次号：&nbsp;<input type="text" id="pigBatchNo" class="easyui-textbox" size="15" onkeydown="if(event.keyCode==13) search()"/>
                        <a href="javascript:search()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" title="搜索"  >搜索</a>
                        <a href="javascript:reset()" class="easyui-linkbutton" data-options="iconCls:'icon-reload'"  title="重置" >重置</a>
                    </td>
                </tr>
                <tr>
                    <td style="width:100%;">
                       <%-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>--%>
                    </td>

                </tr>

            </table>

        </form>
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



