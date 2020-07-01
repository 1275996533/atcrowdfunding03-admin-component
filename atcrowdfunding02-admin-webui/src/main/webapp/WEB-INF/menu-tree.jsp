<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/14
  Time: 1:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-addDiyDom.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        generatorTree();

        //弹出保存模态框
        $("#treeDemo").on("click",".addBtn",function () {
            $("#menuAddModal").modal("show");
            window.pid = this.id
        });
        //确认保存按钮
        $("#menuSaveBtn").click(function () {
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $.trim($("#menuAddModal [name=icon]:checked").val());

            $.ajax({
                url : "menu/save.json",
                data : {
                    "name" : name,
                    "url" : url,
                    "icon" : icon,
                    "pid" : window.pid
                },
                dataType : "json",
                type : "post",
                success : function (response) {
                    if(response.operationResult == "SUCCESS"){
                        layer.msg("添加成功")
                        $("#menuAddModal").modal("hide");
                        generatorTree();
                    }

                },
                error : function (response) {
                    layer.msg("响应码:" + response.status + "错误信息:" + response.statusText)
                }
            })

        });
        //弹出删除模态框
        $("#treeDemo").on("click",".removeBtn",function () {
            window.id = this.id;
            $("#menuConfirmModal").modal("show");
        });
        //为删除按钮绑定事件
        $("#confirmBtn").click(function () {
            $.ajax({
                url : "menu/remove.json",
                data : {
                    "id" : window.id
                },
                dataType : "json",
                type : "post",
                success : function (response) {
                    if(response.operationResult == "SUCCESS"){
                        layer.msg("删除成功")
                        $("#menuConfirmModal").modal("hide");
                        generatorTree();
                    }

                },
                error : function (response) {
                    layer.msg("响应码:" + response.status + "错误信息:" + response.statusText)
                }
            })
        });
        //弹出修改模态框
        $("#treeDemo").on("click",".editBtn",function () {
            window.id = this.id;
            $("#menuEditModal").modal("show");
            //拿到zTreeObj对象
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            //通过treeObj搜寻当前的节点全部属性
            var key = "id";
            var value = window.id;
            var currentNode = treeObj.getNodeByParam(key,value);
            console.log(currentNode)
            //将数据回显回表单
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);
            $("#menuEditModal [name=icon]").val([currentNode.icon]);
        });
        //
        $("#menuEditBtn").click(function () {
            var name = $.trim($("#menuEditModal [name=name]").val());
            var url = $.trim($("#menuEditModal [name=url]").val());
            var icon = $.trim($("#menuEditModal [name=icon]:checked").val());
            $.ajax({
                url : "menu/edit.json",
                data : {
                    "id" : window.id,
                    "name" : name,
                    "url" : url,
                    "icon" : icon
                },
                dataType : "json",
                type : "post",
                success : function (response) {
                    if(response.operationResult == "SUCCESS"){
                        layer.msg("更新成功")
                        $("#menuEditModal").modal("hide");
                        generatorTree();
                    }

                },
                error : function (response) {
                    layer.msg("响应码:" + response.status + "错误信息:" + response.statusText)
                }
            })
        })
    })
</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
    </div>
    <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

        <div class="panel panel-default">
            <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                        class="glyphicon glyphicon-question-sign"></i></div>
            </div>
            <div class="panel-body">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>
    </div>
</div>
<%@include file="modal-menu-add.jsp"%>
<%@include file="modal-menu-edit.jsp"%>
<%@include file="modal-menu-confirm.jsp"%>
</body>
</html>
