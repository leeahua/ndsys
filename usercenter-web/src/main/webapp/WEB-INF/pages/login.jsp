<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统登录</title>
    <%@include file="taglibs.jsp" %>
    <style type=text/css>
        body {
            text-align: center;
            padding-bottom: 0px;
            background-color: #ddeef2;
            margin: 0px;
            padding-left: 0px;
            padding-right: 0px;
            padding-top: 0px
        }

        .input {
            border-bottom: #ccc 1px solid;
            border-left: #ccc 1px solid;
            line-height: 20px;
            width: 182px;
            height: 20px;
            border-top: #ccc 1px solid;
            border-right: #ccc 1px solid
        }
        .input1 {
            border-bottom: #ccc 1px solid;
            border-left: #ccc 1px solid;
            line-height: 20px;
            width: 120px;
            height: 20px;
            border-top: #ccc 1px solid;
            border-right: #ccc 1px solid;
        }
    </style>

</head>
<body>
<form id="loginform"  method="POST"  action="/login/dologin">
    <div></div>
    <table style="margin: auto; width: 100%; height: 100%" border="0"
           cellSpacing="0" cellPadding="0">
        <tbody>
        <tr>
            <td height=150>&nbsp;</td>
        </tr>
        <tr style="height: 254px">
            <td>
                <div style="margin: 0px auto; width: 936px"><img
                        style="display: block" src="${pageContext.request.contextPath}/static/images/body_03.png"></div>
                <div style="background-color: #278296">
                    <div style="margin: 0px auto; width: 936px">
                        <div
                                style="BACKGROUND: url(${pageContext.request.contextPath}/static/images/body_05.png) no-repeat; height: 155px">
                            <div
                                    style="text-align: left; width: 250px; float: right; height: 125px; _height: 95px">
                                <table border="0" cellSpacing="0" cellPadding="0" width="100%">
                                    <tbody>
                                    <tr>
                                        <td style="height: 45px">
                                            <input type="text" class=input name="username" id="username">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <input type="password" class=input  name="password" id="password"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><span id="errormsg"></span></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div style="height: 1px; clear: both"></div>
                            <div style="width: 380px; float: right; clear: both">
                                <table border=0 cellSpacing=0 cellPadding=0 width=300>
                                    <tbody>

                                    <tr>
                                        <td width=100 align=right>
                                            <button type="button" onclick="javascript:login();" class="easyui-linkbutton" >登录</button>
                                                <%--<img src="${pageContext.request.contextPath}/static/images/btn1.jpg"  />--%>
                                        </td>
                                        <td width="100" align="middle">
                                            <button type="reset"  onclick="javascript:clear();" class="easyui-linkbutton" >重置</button>
                                               <%-- <img src="${pageContext.request.contextPath}/static/images/btn2.jpg"   />--%>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="margin: 0px auto; width: 936px"><img
                        src="${pageContext.request.contextPath}/static/images/body_06.jpg"></div>
            </td>
        </tr>
        <tr style="height: 30%">
            <td>&nbsp;</td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
<script type=text/javascript>
    /**
     * 将表单数据转换为JSON格式数据
     * */
    $.fn.serializeObject = function() {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
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
    }
    function clear(){
        $("#username").val("");
        $("#password").val("");
    }
    function login(){
        if(checkUser()){
            var data = $("#loginform").serializeObject();
            var url = "/login/dologin";
            ajaxsubmit(url,data);
        }else{
            return ;
        }

    }
    function checkUser(){
        if($("#username").val()==''){
            alert("用户名不能为空");
            //$("#errormsg").css("color","red").html("用户名不能为空");
            return false;
        }
        if($("#password").val()==''){
            alert("密码不能为空");
           // $("#errormsg").css("color","red").html("密码不能为空");
            return false;
        }
        return true;
    }
    //$("#fm").serializeObject()
    function ajaxsubmit(url,data){
        $.ajax({
            type: "POST",
            url: url,
            data: data,
            dataType: "json",
            contentType:"application/x-www-form-urlencoded",
            success: function(result){
                if (result.code != "00"){
                    alert(result.msg);
                } else {
                    window.location.href = "/index/toindex"
                }
            },
            error:function(result){

                alert("连接服务器异常");
            }
        });
    }
</script>