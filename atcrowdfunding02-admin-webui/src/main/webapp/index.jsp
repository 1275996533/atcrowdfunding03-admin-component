<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/13
  Time: 1:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $("#btn1").click(function () {
            var json = {
                name : "黄政杰",
                age : 12,
                subjects : [
                    {name : "javaEE",score : 90},
                    {name : "HTML",score : 100},
                ],
                address : {
                    province : "福建",
                    city : "漳州",
                    street : "龙文区"
                },
                map : {
                    "k1" : "v1",
                    "k2" : "v2"
                }

            }
            $.ajax({
                url : "test/json.json",
                type : "post",
                data : JSON.stringify(json),
                dataType :"json",
                contentType : "application/json",
                success : function (response) {
                    console.log(response)
                }
            })
        })
    })
</script>
</head>
<body>
<a href="test/ssm.html">测试SSM整合</a>
<button id="btn1">测试复杂数据回传</button>
<button id="btn2">点我弹窗</button>
</body>
</html>
