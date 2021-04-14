var patt_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var patt_tel = /^1[3456789]\d{9}$/;


$(function () {
    $("#login-btn").click(function () {
        if ($('.error-class').text() !== "") {
            return;
        }
        var account = $("#account").val();
        var password = $("#password").val();
        $.ajax({
            url: "/user/login",
            type: "post",
            data: {
                "account": account,
                "password": password
            },
            dataType: "json",
            success: function (data) {
                alert(data['message']);
                if (data['flag']) {
                    localStorage.setItem("local_id", data['data']);
                    localStorage.setItem("login_account", account);
                    location.href = "msg.html";
                }
            }
        });
    });

    $("#register-href-btn").click(function () {
        location.href = "safe.html"
    });


    $("#account")[0].oninput = function () {
        //判断账号格式是否正确
        var account = $(this).val();
        if (!account) {
            $(this).parent().next().text("未填写");
            return;
        }
        if (patt_email.test(account) || patt_tel.test(account)) {
            //ok
            $(this).parent().next().text("");
        } else {
            $(this).parent().next().text("账号格式错误");
        }
    };

    $("#account").blur(function () {
        var account = $(this).val();
        if (!account) {
            $(this).parent().next().text("未填写");
            return;
        }
        $.ajax({
            url: "/user/" + account + "/isExist",
            type: 'get',
            dataType: "json",
            success: function (data) {
                if (data['code'] === 203) {//用户未注册
                    $("#account").parent().next().text(data["message"]);
                } else {
                    //取出缓存中的head_url
                    var head_url = localStorage.getItem("head_url" + account);
                    if (head_url){
                        $("#login-icon").attr("src",head_url);
                    }
                    $("#account").parent().next().text("");
                }
            }
        });
    });
});