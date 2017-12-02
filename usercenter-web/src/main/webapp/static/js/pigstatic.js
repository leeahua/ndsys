/**
 * 系统平台用户控制js
 *
 */
$(document).ready(function () {
    //加载table数据

    loadData();

});
function loadData(){
    $('#dg').datagrid({
        url: "/piginfo/statisticsData",
    });
}
function search(){
    $("#dg").datagrid('load',{
        "date":$("#staticdate").datebox('getValue'),
        /*"platformCode":$("#platformCode").val(),
         "platformStatus":$("#platformStatus").datebox('getValue'),*/
    });
}
function reset() {
    $("#ff").form('clear');
}

if ($.fn.datagrid){
    $.fn.datagrid.defaults.pageSize = 20;
    $.fn.datagrid.defaults.pageList = [10,20,50,100];
}

