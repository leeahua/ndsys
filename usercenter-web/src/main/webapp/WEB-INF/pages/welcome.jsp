<%--
  Created by IntelliJ IDEA.
  User: lyh
  Date: 2017/10/29
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" autoFlush="true"%>
<!DOCTYPE html>
<html>
<head>
    <title>欢迎页</title>
    <%@include file="taglibs.jsp" %>
    <script>
        $(document).ready(function () {
            //加载table数据
            $("#stopbtn").hide();
            $("#startbtn").show();
        });
        function ajaxsubmit(url,data){
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                dataType: "json",
                contentType:"application/x-www-form-urlencoded",
                success: function(result){
                    console.log(result);
                    if(result.code=='00') {
                        if (data == 1) {
                            alert("启动串口监听服务器完成");
                            $("#startbtn").hide();
                            $("#stopbtn").show();
                        }
                        if (data == 0) {
                            alert("关闭串口监听服务器完成");
                            $("#stopbtn").hide();
                            $("#startbtn").show();
                        }
                    }else{
                        if (data == 1) {
                            alert(result.msg);

                        }
                        if (data == 0) {
                            alert("关闭串口监听服务器完成");

                        }
                    }

                },
                error:function(result){

                    alert("连接服务器异常");
                }
            });
        }

    </script>
</head>
<body >

<div region="center">
    <button id="startbtn" onclick="ajaxsubmit('/serial/start','1')">启动串口服务</button>
    <button id="stopbtn" onclick="ajaxsubmit('/serial/stop','0')">关闭串口服务</button>
</div>
</body>
</html>
