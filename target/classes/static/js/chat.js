var webSocket = null;
if ('WebSocket' in window) {
    webSocket = new WebSocket('ws://39.108.186.224:9999/chatWebSocket');
    // webSocket = new WebSocket('ws://localhost:9999/chatWebSocket');
} else {
    alert("浏览器不支持WebSocket");
}
if (webSocket) {
    webSocket.onopen = function (event) {
        console.log('建立连接');
    };
    webSocket.onclose = function (event) {
        console.log('连接关闭');
    };
    webSocket.onmessage = function (event) {
        var data = JSON.parse(event.data);
        //此时是双方都在聊天界面
        if (sessionStorage.getItem("targetId") == data['id']) {
            $('<span class="msg_left_border">' + data['msg'] + '</span><div class="time-split"></div>').appendTo('#chat_note');
            $('#chat_note').scrollTop($('#chat_note')[0].scrollHeight);
        } else {
            //在与其他人聊天
            //显示在当前页面的头顶上
            $("#to_hide").text(data['name'] + "；" + data['msg']);
            $("#to_hide").removeAttr("style");
            $("#to_hide").click(function () {
                sessionStorage.setItem("targetId", data['id']);
                sessionStorage.setItem("targetName", data['name']);
                location.href = "chat.html";
            });
        }
        saveOne(data['id'], data['msg'], "left");


        // load_msg(data['id'],data['msg'],data['name'],data['time']);
    };
    webSocket.onerror = function () {
        // alert("WebSocket发生错误");
    };
    window.onbeforeunload = function () {
        webSocket.close();
    };
}


$(function () {
    //加载聊天记录
    $("#name").text(sessionStorage.getItem("targetName"));
    var chat_note = localStorage.getItem(localStorage.getItem("local_id") + "_" + sessionStorage.getItem("targetId"));
    $("#chat_note").html(chat_note);

    $('#chat_note').scrollTop($('#chat_note')[0].scrollHeight);
    $("#sendMsg-btn").click(function () {
        $("#chat_note").css("height", "75%");
        var msg = $("#msg").val();
        if (!msg) {
            alert("请输入内容");
            return;
        }
        var id = sessionStorage.getItem("targetId");
        $.ajax({
            url: "/chatting",
            type: "post",
            data: {
                'message': msg,
                'friendId': id
            },
            dataType: "json",
            success: function (data) {
                // console.log(data);
                if (data['flag']) {
                    //自己这边也要显示一下自己发的消息
                    $("#msg").val("");//清空消息栏
                    $('<span class="msg_right_border">' + msg + '</span><div class="time-split"></div>').appendTo('#chat_note');
                    saveOne(id, msg, "right");
                    $('#chat_note').scrollTop($('#chat_note')[0].scrollHeight);
                } else {
                    alert(data['message']);
                }
            }
        });
    });

    $("#msg").click(function () {
        $("#chat_note").css("height", "40%")
    });

    $("#chat_note").click(function () {
        $("#chat_note").css("height", "75%");
    });

});
