function myAddDiyDom(treeId, treeNode) {
    // 将每个图标的id取出
    var taget = treeNode.tId + "_ico";
    // 移除图标的样式,加入我们的样式
    $("#" + taget).removeClass().addClass(treeNode.icon);
    //
    console.log(treeNode)

}
function myAddHoverDom(treeId, treeNode) {
    //获取每个节点的id
    var aId = treeNode.tId + "_a";
    //查询设置包裹按钮的span的id
    var btnGroupId = aId + "_btnGrp";

    var editBtn   = "<a id='"+treeNode.id+"' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='修改权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>"
    var removeBtn = "<a id='"+treeNode.id+"' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='删除权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>"
    var addBtn    = "<a id='"+treeNode.id+"' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;'  title='删除权限信息'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>"

    if($("#" + btnGroupId).length > 0){
        return;
    }
    //在节点后加入span元素

    //判断当前的等级来计算要加入的按钮

    var level = treeNode.level;
    var btnHtml = "";
        //如果等级为0(根节点)为其添加 添加按钮
    if (level == 0){
        btnHtml = addBtn;
        //如果等级为1(分支节点)为其添加 添加 修改按钮
    }else if (level == 1){
        btnHtml = btnHtml + addBtn + " " + editBtn;
        var length = treeNode.children.length;
        //如果分支节点没有叶子节点,那么拼接删除按钮
        if(length < 1){
            btnHtml = btnHtml + removeBtn;
        }
        //如果等级为2 为其拼接 修改按钮 添加按钮
    }else if (level == 2){
        btnHtml = btnHtml + editBtn + " " + removeBtn;
    }
    $("#" + aId).after("<span id='"+btnGroupId+"'>"+btnHtml+"</span>");
}
function myRemoveHoverDom(treeId, treeNode) {
    var aId = treeNode.tId + "_a";
    var btnGroupId = aId + "_btnGrp";
    $("#" + btnGroupId).remove();
}