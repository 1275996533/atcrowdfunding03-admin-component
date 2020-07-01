<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/14
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="include-head.jsp" %>
<script type="text/javascript" src="crowd/my-role.js"></script>
<link rel="stylesheet" href="css/pagination.css">
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-assign.js"></script>

<script type="text/javascript">
    $(function () {

        window.pageSize = 5;
        window.pageNum = 1;
        window.keyword = "";
        $("#searchBtn").click(function () {
            window.keyword = $("#keyword").val();
            generatePage();
        });

        generatePage();
        //给add按钮添加绑定事件
        $("#addRoleBtn").click(function () {
            $("#addModal").modal("show");
        });
        $("#saveRoleBtn").click(function () {
            var roleName = $("#addModal [name=roleName]").val();
            $.ajax({
                url : "role/save.json",
                data : {
                    "name" : roleName
                },
                dataType : "json",
                type : "post",
                success : function (response) {
                  if (response.operationResult == "SUCCESS"){
                      layer.msg("新增用户成功");
                      window.pageNum = 999999;
                      generatePage();
                      $("#addModal").modal("hide")
                  }else{
                      layer.msg("新增用户失败");
                  }
                },
                error : function (response) {
                    layer.msg("错误码为" + response.status + "错误信息:" + response.statusText)
                }
            });

        });
      //

        //给update按钮绑定事件
        $("#rolePageBody").on("click",".pencil",function () {
            //弹出修改模态框
            $("#updateModal").modal("show");
            //将输入框的值取到
            var inputContent = $(this).parent().prev().text();
            window.roleId = this.id;
            //将输入框的值进行填充
            $("#updateModal [name=roleName]").val(inputContent);
        });
        //给提交修改按钮绑定事件
        $("#updateRoleBtn").click(function () {
            var roleName = $("#updateModal [name=roleName]").val();
            $.ajax({
                url : "role/edit.json",
                data: {
                    "id" : window.roleId,
                    "name" : roleName
                },
                dataType: "json",
                success : function (response) {
                  if(response.operationResult == "SUCCESS"){
                      layer.msg("删除成功")
                  }
                    $("#updateModal").modal("hide");
                    generatePage();
                },
                error : function (response) {
                    layer.msg("响应码:" + response.status + "错误信息:" + response.statusText)
                }
            })
        });


        $("#deleteRoleBtn").click(function () {
            var requestBody = JSON.stringify(window.roleIdArray);
            console.log(window.roleIdArray);
            $.ajax({
                url : "role/remove/role/by/roleId.json",
                data : requestBody,
                dataType : "json",
                contentType : "application/json",
                type : "post",
                success : function (response) {
                    if(response.operationResult == "SUCCESS"){
                        layer.msg("删除成功")
                    }
                    $("#deleteModal").modal("hide");
                    generatePage();
                },
                error : function (response) {
                    layer.msg("响应码:" + response.status + "错误信息:" + response.statusText)
                }
            })
        });
        //给单个的删除按钮绑定事件
        $("#rolePageBody").on("click",".delete",function () {
            var Content = $(this).parent().prev().text();
            var roleArray = [{
                roleId:this.id,
                roleName:Content
            }];
            openDeleteModal(roleArray);

        });
        //给全选按钮绑定单机事件
        $("#summaryCheckBox").click(function () {
            var currentCheck = this.checked;
            $(".itemCheckBox").prop("checked",currentCheck);

        });
        //给单个选择按钮绑定事件
        $("#rolePageBody").on("click",".itemCheckBox",function () {
            var nowCheckBoxCount = $(".itemCheckBox:checked").length;
            var totalCheckBox = $(".itemCheckBox").length;
            $("#summaryCheckBox").prop("checked",nowCheckBoxCount == totalCheckBox);
        });

        $("#deleteBtn").click(function () {
            var roleArray = [];
            var nowCheckBox = $(".itemCheckBox:checked");
            nowCheckBox.each(function () {
                var roleName = $(this).parent().next().text();
                var roleId = this.id;
                roleArray.push(
                    {roleId : roleId,roleName : roleName}
                )
            });

            openDeleteModal(roleArray);
        });
        //给分配权限按钮绑定事件
        $("#rolePageBody").on("click",".checkedBtn",function () {
            window.roleId = this.id;

            $("#assignTreeModal").modal("show");

            getAuthTree();
        });
        //
        $("#assignSaveBtn").click(function () {
            assignSaveBtnClick();
        })
    })
</script>
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keyword" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="deleteBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="addRoleBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryCheckBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="model-role-add.jsp"%>
<%@include file="model-role-edit.jsp"%>
<%@include file="model-role-delete.jsp"%>
<%@include file="modal-assign-tree.jsp"%>
</body>
</html>

