function getAuthTree() {
    var ajaxResult = $.ajax({
        url : "get/assign/auth.page.json",
        dataType : "json",
        type : "post",
        async : false
    });


    if(ajaxResult.status != 200){
        layer.msg("请求错误响应码:" + ajaxResult.status + "提示信息:" + ajaxResult.statusText)
        return;
    }
    var responseJson = ajaxResult.responseJSON;
    if (responseJson.operationResult == "SUCCESS"){
        var setting = {
            data: {
                key: {
                    //自定义name的映射名
                    name: "title"
                },
                simpleData: {
                    enable: true,
                    //自定义pId的映射名
                    pIdKey: "categoryId"
                },

            },
            //设置使所有节点加上复选框
            "check": { "enable": true }
        };
        var zNodes = responseJson.queryData;
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);

        //设置所有节点默认展开
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.expandAll(true);
    }
    //让数据库数据回显
    ajaxResult = $.ajax({
        url : "get/assign/by/roleId.json",
        dataType : "json",
        data: {
            "roleId" : window.roleId
        },
        type : "post",
        async : false
    });
    if(ajaxResult.status != 200){
        layer.msg("请求错误响应码:" + ajaxResult.status + "提示信息:" + ajaxResult.statusText)
        return;
    }
    if (responseJson.operationResult == "SUCCESS"){
        var responseJson = ajaxResult.responseJSON;
        var authIdList = responseJson.queryData;
        var key = "id";
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        for (var i = 0;i<authIdList.length;i++){
            //通过authId获取节点
            var node = treeObj.getNodeByParam("id", authIdList[i], null);

            var isChecked = true;
            //将获取的node节点的选中设为true
            //联动效果设为false
            treeObj.checkNode(node, isChecked, false);
        }
    }
}
function assignSaveBtnClick() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    var checkedNodes = treeObj.getCheckedNodes(true);
    var checkedNodeIds= [];
    for (var i = 0 ; i<checkedNodes.length;i++){
        var checkedNode = checkedNodes[i];
        checkedNodeIds.push(checkedNode.id);
    }
   $.ajax({
       url : "save/assign.json",
       type:"post",
       data : {
           "checkedNodeIds" : checkedNodeIds,
           "roleId" : roleId
       },
       dataType : "json",
       success : function (response) {
           if(response.operationResult == "SUCCESS"){
               layer.msg("关联成功")
           }
           $("#assignTreeModal").modal("hide");
       },
       error : function (response) {
           layer.msg("响应码:" + response.status + "错误信息:" + response.statusText)
       }
   })
}