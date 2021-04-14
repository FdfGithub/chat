function load_friend(id, name, head_url) {
    var temp = $('            <li id=' + id + '>\n' +
        '                <div class="head-border-class">\n' +
        '                    <img src='+head_url+'>\n' +
        '                </div>\n' +
        '                <div class="li_right">' + name + '</div>\n' +
        '            </li>');
    temp.appendTo("#friends_list");
    temp.click(function () {
        //查看好友的个人信息
        sessionStorage.setItem("selectId", id);
        location.href = "info.html"
    });
}
function view_friend(){
    //获取好友列表
    $.ajax({
        url: "/friend/all",
        type: "get",
        dataType: "json",
        success: function (data) {
            if (data['flag']) {
                var data0 = data['data'];
                for (var i = 0; i < data0.length; i++) {
                    load_friend(data0[i]['userId'], data0[i]['name'], data0[i]['headUrl']);
                }
            }
        }
    });
}
function view_group(){
    //获取群聊
    $.ajax({
        url: "/group/all",
        type: "get",
        dataType: "json",
        success: function (data) {
            if (data['flag']) {
                var data0 = data['data'];
                for (var i = 0; i < data0.length; i++) {
                    load_friend(data0[i]['groupId'], data0[i]['groupName'], data0[i]['groupHeadUri']);
                }
            }
        }
    });
}

$(function () {
    // if (!localStorage.getItem("local_id")) {
    //     alert("未登录");
    //     location.href = "login.html";
    // }


    $("#head_url").click(function () {
        sessionStorage.setItem("selectId", localStorage.getItem("local_id"));
        location.href = "info.html"
    });

    //获取头像
    var temp = localStorage.getItem("head_url"+localStorage.getItem("login_account"));
    if (temp){
        $("#head_url").attr("src", temp);
    }else {
        $.ajax({
            url: "/user/findHeadUrl",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data["flag"]) {
                    var head_url = data["data"];
                    $("#head_url").attr("src", head_url);
                    localStorage.setItem("head_url"+localStorage.getItem("login_account"),head_url);
                }
            }
        });
    }
    // $("#friend").trigger('click');
    view_friend();



    $("#add-btn").click(function () {
        var style = $("#add-list").attr("style");
        if (style == "display: none;") {
            $("#add-list").removeAttr("style");
        } else {
            $("#add-list").css("display", "none");
        }
    });

    $("#add_group").click(function () {
        location.href = "c_group.html";
    });

    $("#add_friend").click(function () {
        //添加好友
        location.href = "add_friend.html";
    });

    $("#exit").click(function () {
        $.ajax({
            url: "/user/exit",
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data['flag']) {
                    localStorage.removeItem("local_id");
                    location.href = "login.html";
                }
            }
        });
    });

    $("#friend").click(function () {
        // $(this).css("background","blue");
        view_friend();
    });

    $("#group").click(function () {
        // $(this).css("background","blue");
        view_group();
    });
});