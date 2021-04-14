function operation(url){
    $.ajax({
        url: url,
        type: 'get',
        dataType: "json",
        success: function (data) {
            alert(data["message"] + "：" + name);
            location.href = "friends.html";
        }
    });
}

$(function () {
    var userId = sessionStorage.getItem("selectId");
    $.ajax({
        url: '/user/'+userId+'',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            if (data['flag']){
                var info = data['data'];
                loadInfo(
                    info["userId"],
                    info["userTel"],
                    info["userEmail"],
                    info["userName"],
                    info["userGender"],
                    info["userBorn"],
                    info["userSign"],
                    info["userMotto"],
                    info["userHeadUrl"]);
                if (info['status']===-1){
                    $("#operation-btn").css("display","none");
                    $("#send_msg").css("display","none");
                }else if (info['status']===1){
                    $("#operation-btn").text("删除好友");
                }else {
                    //
                }
            }
        }
    });

    $("#operation-btn").click(function () {
        var id = $("#id_value").text();
        if ($(this).text()==="删除好友"){
            //进行删除操作
            operation('/friend/' + id + '/delete');
        }else if ($(this).text()==="添加好友"){
            //进行添加操作
            operation('/friend/' + id + '/add');
        }
    });

    $("#send_msg").click(function () {
        //进入chat.html
        //获取到id和名字
        var id = $("#id_value").text();
        var name = $("#name_value").text();
        sessionStorage.setItem("targetId", id);
        sessionStorage.setItem("targetName", name);
        location.href = "chat.html";
    });


    if (localStorage.getItem("local_id")==userId){
        $("#tel_value").click(function () {
            //更换手机号
            alert("因为需要发手机短信验证，暂时没有该功能，所以目前不能修改手机号");
        });
        $("#email_value").click(function () {
            //更换邮箱
            //邮箱验证，是否已经被注册过、是否是用户自己的邮箱
            // sessionStorage.setItem("old_value",$(this).text());
            location.href = "change_email.html";
        });
        $("#name_value").click(function () {
            //修改昵称
            sessionStorage.setItem("old_value",$(this).text());
            location.href = "change_name.html";
        });
        $("#gender_value").click(function () {
            //修改性别
            sessionStorage.setItem("old_value",$(this).text());
            location.href = "change_gender.html";
        });
        $("#born_value").click(function () {
            //修改出生日期/生日
            sessionStorage.setItem("old_value",$(this).text());
            location.href = "change_born.html";
        });
        // $("#sign_value").click(function () {
        //     //修改星座
        //     location.href = "change_sign.html";
        // });
        $("#motto_value").click(function () {
            //修改座右铭
            sessionStorage.setItem("old_value",$(this).text());
            location.href = "change_motto.html";
        });
        $("#head_url").click(function () {
            //更换头像
            //触发一个打开文件夹的效果
            $("#upload_head").trigger('click');
        });

        $("#upload_head").change(function () {
            var formData = new FormData();
            formData.append('file', document.getElementById('upload_head').files[0]);
            $.ajax({
                url:"/file",
                type:"post",
                data: formData,
                dataType:"json",
                contentType: false,
                processData: false,
                success: function(data) {
                   if (data['flag']){
                       $("#head_url").attr("src",data["data"]);
                       update_value("userHeadUri",data["data"]);
                       localStorage.setItem("head_url"+localStorage.getItem("login_account"),data["data"]);//去除缓存的头像信息
                   }
                }
            });
        });
    }




});

function loadInfo(userId,tel,email,name,gender,born,sign,motto,head_url) {
    $("#id_value").text(userId);
    $("#tel_value").text(tel);
    $("#email_value").text(email);
    $("#name_value").text(name);
    $("#gender_value").text(gender);
    $("#born_value").text(born);
    $("#sign_value").text(sign);
    $("#motto_value").text(motto);
    $("#head_url").attr("src",head_url);
}