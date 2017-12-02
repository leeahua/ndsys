<%@ page language="java" import="java.util.*" pageEncoding="utf-8" autoFlush="true"%>
<!DOCTYPE html>
<html>
<head>
    <title>SystemPlatform</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@include file="taglibs.jsp" %>
    <script type="application/javascript" src="/static/js/pigstatic.js"></script>
</head>

<body style="margin: 1px;">
    <table id="dg" title="猪肉统计信息列表" class="easyui-datagrid"
       toolbar="#toolbar" idField="itemid"  pagination="true" rownumbers="true"
       fitColumns="true" singleSelect="true" fit="true">
        <thead>
        <tr>
            <th field="pigBatchNo" width="30">批次号</th>
            <th field="totalWeight" width="10">总重量</th>
            <th field="aTotal" width="20">A级别个数</th>
            <th field="bTotal" width="20">B级别个数</th>
            <th field="cTotal" width="20">C级别个数</th>
            <th field="dTotal" width="20">D级别个数</th>
            <th field="eTotal" width="20">E级别个数</th>
            <th field="createTime" width="35" formatter="formatDateTime">创建时间</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar"><!--查询div-->
        <form id="ff">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        批次号：&nbsp;
                        <input class="easyui-datebox" id="staticdate" style="width:120px">
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
        return year + "-" + month + "-" + day ;
    }
</script>

</html>



