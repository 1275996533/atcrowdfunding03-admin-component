function generatorTree() {
    $.ajax({
        url:"menu/get/page.json",
        type : "post",
        dataType : "json",
        success : function (response) {
            if (response.operationResult == "SUCCESS"){
                console.log(response)
                var setting = {
                    view: {
                        //自定义节点样式
                        addDiyDom: myAddDiyDom,
                        //给节点添加移入效果
                        addHoverDom: myAddHoverDom,
                        removeHoverDom: myRemoveHoverDom,
                    },
                    data:{
                        key : {
                            url : "meme"
                        }
                    }
                };
                var zNodes = response.queryData;
                $.fn.zTree.init($(".ztree"), setting, zNodes);
            }
        }
    })
}