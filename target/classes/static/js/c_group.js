$(function () {
    $("#create").click(function () {
        //获取群名称
        var group_name = $("#group_name").val();
        $.ajax({
            url: "/group",
            type: "post",
            data: {
                "groupName": group_name
            },
            dataType: "json",
            success: function (data) {
                if (data["flag"]){
                    alert("创建群聊成功");
                    location.href = "friends.html";
                }
            }
        });
    });
});