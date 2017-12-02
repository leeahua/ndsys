/**
 * 系统平台用户控制js
 *
 */
$(document).ready(function () {
    //加载table数据
    $("#dg").datagrid("hideColumn", "id");
    $('#dg').datagrid({
        url: "/pigpound/getforpage"

    });

});
function search(){
    $("#dg").datagrid('load',{
        "startTime":$("#startTime").datebox("getValue"),
        "endTime":$("#startTime").datebox("getValue")
    });
}
function reset() {
    $("#ff").form('clear');
}


function newUser(){
    $('#dlg').dialog('open').dialog('setTitle','新增');
    $('#fm').form('clear');
    url = '/pigpound/save';
}
if ($.fn.datagrid){
    $.fn.datagrid.defaults.pageSize = 20;
    $.fn.datagrid.defaults.pageList = [10,20,50,100];
}

