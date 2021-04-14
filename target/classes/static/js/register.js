var patt_password = /^[a-zA-Z]\w{5,17}$/;

$(function () {
    $("#register-btn").click(function () {
        if ($('.error-class').text()!==""){
            return;
        }
        var account = sessionStorage.getItem("account");
        var password = $("#password0").val();
        if (!account) {
            alert("系统错误");
            return;
        }
        $.ajax({
            url: "/user/register",
            type: "post",
            data: {
                "account": account,
                "password": password
            },
            dataType: "json",
            success: function (data) {
                alert(data['message']);
                if (data['flag']) {
                    sessionStorage.removeItem("account");
                    location.href = "msg.html";
                }
            }
        });
    });


    $("#password0")[0].oninput = function () {
        //判断密码格式是否正确
        var password = $(this).val();
        var temp = $("#password1").val();
        isPassword(password,temp);
    };


    $("#password1")[0].oninput = function () {
        //判断密码格式是否正确
        var password = $(this).val();
        var temp = $("#password0").val();
        isPassword(password,temp);
    };


});

function isPassword(password, temp) {
    if (!password) {
        $(this).parent().next().text("未填写");
        return;
    }
    if (patt_password.test(password)) {
        //ok
        $(this).parent().next().text("");
    } else {
        $(this).parent().next().text("以字母开头，长度在6~18之间");
    }
    if (temp) {//如果另一个密码框有值
        if (password === temp) {
            $(this).parent().next().text("");
        } else {
            $(this).parent().next().text("两次密码不一致");
        }
    }
}