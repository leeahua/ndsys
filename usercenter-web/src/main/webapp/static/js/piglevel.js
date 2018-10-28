/**
 * 系统平台用户控制js
 *
 */
$(document).ready(function () {
    //加载table数据
    $("#userdg").datagrid("hideColumn", "id");
    $('#userdg').datagrid({
        url: "/level/getforpage"
    });


});
function search(){
    $("#userdg").datagrid('load',{
       /* "username":$("#username").val()*/
    });
}
function reset() {
    $("#userff").form('clear');
}


function newUser(){
    $('#userdlg').dialog('open').dialog('setTitle','新增');
    $('#userfm').form('clear');
    url = '/level/save';
}
if ($.fn.datagrid){
    $.fn.datagrid.defaults.pageSize = 20;
    $.fn.datagrid.defaults.pageList = [10,20,50,100];
}

