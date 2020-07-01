function generatePage(){
    var pageList = getPageInfoRemote();
    fillTableBody(pageList);
}
// 获取后端回传的PageInfo
function getPageInfoRemote() {
    var ajaxResult = $.ajax({
        url : "role/get/page.json",
        type : "post",
        dataType : "json",
        data : {
            "pageNum" : window.pageNum,
            "pageSize" : window.pageSize,
            "keyword" : window.keyword
        },
        async : false
    })
    var status = ajaxResult.status;
    var statusText = ajaxResult.statusText;
    if(status!=200){
        layer.msg("失败" + "状态码:" + status + "错误原因:" + statusText)
        return null;
    }
    var resultJson = ajaxResult.responseJSON;
    if (resultJson.operationResult == ""){
        layer.msg(operationMessage);
        return null;
    }
    var queryData = resultJson.queryData;
    return queryData;
}
//填充展示列表
function fillTableBody(pageInfo) {
    $("#rolePageBody").empty();
    $("#Pagination").empty();
   if (pageInfo==null || pageInfo=="undefined" || pageInfo.list == null || pageInfo.list.length == 0){
        $("#rolePageBody").append("<tr><td colspan='4'>抱歉没有搜索到数据!!</td></tr>")
   }
   var pageList = pageInfo.list;
   for(var i=0;i<pageList.length;i++){
       var role = pageList[i];
       var roleNameTd = "<td>"+role.name+"</td>";
       var checkBoxTd = "<td><input id='"+role.id+"' type='checkbox' class='itemCheckBox'></td>"
       var pencilBtn = "<button id='"+role.id+"' type='button' class='btn btn-primary btn-xs pencil'><i class=' glyphicon glyphicon-pencil'></i></button>";
       var checkBtn= "<button id='"+role.id+"' type='button' class='btn btn-success btn-xs checkedBtn'><i class=' glyphicon glyphicon-check'></i></button>";
       var removeBtn = "<button id='"+role.id+"' type='button' class='btn btn-danger btn-xs delete'><i class=' glyphicon glyphicon-remove'></i></button>";

       var NumberTd = "<td>"+i+"</td>"
       var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>"
       var tr = "<tr>"+NumberTd+checkBoxTd+roleNameTd+buttonTd+"</tr>"
       $("#rolePageBody").append(tr);
       generateNavigator(pageInfo)
   }

}
function generateNavigator(pageInfo) {
//记录总条数
    var total = pageInfo.total;
//给生成导航条需要的参数
    var properties = {
        num_edge_entries: 3,                                               //边缘页数
        num_display_entries: 5,                                            //主体页数
        callback : paginationCallBack,                                     //指定用户点击按钮时的回调函数
        current_page : window.pageNum-1,                                 //当前页数
        prev_text : "上一页",                                               //上一页的文字信息
        next_text : "下一页"                                                //下一页的文字信息
    };
    $("#Pagination").pagination(total,properties);
}
//分页的回调函数
function paginationCallBack(pageIndex, jQuery) {
    window.pageNum =  pageIndex + 1;
    //每次点击之后重新发请求刷新页面
    generatePage();
    return false;
}
//弹出删除框的函数
function openDeleteModal(roleArray) {
    $("#deleteConfirmBody").empty();
    $("#deleteModal").modal("show");
    var roleIdArray = [];
    for (var i = 0;i<roleArray.length;i++){
        var role = roleArray[i];
        var roleId = role.roleId;
        var roleName = role.roleName;
        $("#deleteConfirmBody").append(roleName + "</br>");
        roleIdArray.push(roleId);
    }
    window.roleIdArray = roleIdArray;
}