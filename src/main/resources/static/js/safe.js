var patt_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
var patt_tel = /^1[3456789]\d{9}$/;
var time;
var id = null;
var i = 1;

function showtime() {
    if (time == 0) {
        $("#getCode-btn")[0].innerHTML = "重新获取";
        $("#getCode-btn").removeAttr("disabled");//设置可以点击
        if (id != null) {
            clearInterval(id);
            id = null;
        }

    } else {
        time = time - 1;
        $("#getCode-btn")[0].innerHTML = time + "s"
    }
}

function resert() {
    $("#getCode-btn").attr("disabled", "disabled");//设置不能点击
    if (time <= 0 || i == 1) {
        time = 60;
        if (id != null) {
            clearInterval(id);
            id = null;
        } else
            id = setInterval("showtime()", 1000);
        i++;
    } else {
        alert('操作频繁，请' + time + 's后再重试');
    }
}


$(function () {
    $("#next-btn").click(function () {
        if ($('.error-class').text()!==""){
            return;
        }
        var authCode = $("#authCode").val();
        $.ajax({
            url: "/user/inspect/register",
            type: "post",
            data: {
                "code": authCode
            },
            dataType: "json",
            success: function (data) {
                if (data["flag"]) {
                    location.href = "register.html"
                } else {
                    alert(data['message']);
                }
            }
        });
    });

    //获取验证码
    $("#getCode-btn").click(function () {
        if ($("#account").parent().next().text()!==""){
            return;
        }
        var account = $("#account").val();
        if (!account){
            $("#account").parent().next().text("未填写");
            return;
        }
        $.ajax({
            url: "/user/" + account + "/register",
            type: "get",
            dataType: "json",
            success: function (data) {
                alert(data['message']);
                if (data["flag"]) {
                    sessionStorage.setItem("account", account);
                    resert();
                }
            }
        });
    });


    $("#authCode").blur(function () {
        var code = $(this).val();
        if (code) {
            $(this).parent().next().text("");
        }else {
            $(this).parent().next().text("未填写");
        }
    });


    $("#account")[0].oninput = function(){
        //判断账号格式是否正确
        var account = $(this).val();
        if (!account){
            $(this).parent().next().text("未填写");
            return;
        }
        if (patt_email.test(account)||patt_tel.test(account)){
            //ok
            $(this).parent().next().text("");
        }else {
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
                if (data['flag']){
                    if (data['code']=="204"){//用户已存在
                        $("#account").parent().next().text(data["message"]);
                    }else {
                        $("#account").parent().next().text("");
                    }
                }
            }
        });
    });
});