/**
 * 系统平台用户控制js
 *
 */
$(document).ready(function () {
    //加载table数据
    $("#dg").datagrid("hideColumn", "id");
    $('#dg').datagrid({
        url: "/pigwidth/getforpage",

    });

});
function search(){
    $("#dg").datagrid('load',{
        "pigBatchNo":$("#pigBatchNo").val(),
        /*"platformCode":$("#platformCode").val(),
        "platformStatus":$("#platformStatus").datebox('getValue'),*/
    });
}
function reset() {
    $("#ff").form('clear');
}


function newUser(){
    $('#dlg').dialog('open').dialog('setTitle','新增');
    $('#fm').form('clear');
    url = '/pigwidth/save';
}
if ($.fn.datagrid){
    $.fn.datagrid.defaults.pageSize = 20;
    $.fn.datagrid.defaults.pageList = [10,20,50,100];
}

