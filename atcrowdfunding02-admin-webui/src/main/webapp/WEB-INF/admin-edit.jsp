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
<body>
<%@include file="include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <p class="help-block label label-warning">${requestScope.exception.message}</p>
                <div class="panel-body">
                    <form action="edit/admin.html" method="post" role="form">
                        <input type="hidden" name="pageNum" value="${requestScope.pageNum}">
                        <input type="hidden" name="keyword" value="${requestScope.keyword}">
                        <input type="hidden" name="id" value="${requestScope.admin.id}">
                        <div class="form-group">
                            <label for="exampleInputPassword1">登陆账号</label>
                            <input name="loginAcct"
                                   value="${requestScope.admin.loginAcct}"
                                   type="text" class="form-control" id="exampleInputPassword1"
                                   placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">用户昵称</label>
                            <input name="userName"
                                   value="${requestScope.admin.userName}"
                                   type="text" class="form-control" id="exampleInputPassword3"
                                   placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input name="email"
                                   value="${requestScope.admin.email}"
                                   type="email" class="form-control" id="exampleInputEmail1"
                                   placeholder="请输入邮箱地址">
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改
                        </button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
