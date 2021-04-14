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
        load_msg(data['id'], data['msg'], data['name'], data['time'],data['headUrl']);
    };
    webSocket.onerror = function () {
        // alert("WebSocket发生错误");
    };
    window.onbeforeunload = function () {
        webSocket.close();
    };
}

function load_msg(id, msg, name, time,head_url) {
    saveOne(id, msg,"left");//存储消息
    var test = $('#' + id);//将同一个人消息合并到一起
    if (test.length > 0) {
        var t = $("#no_read");
        if (t.text()>99){
            t.text("99+");
        }else {
            t.text(parseInt(t.text())+1);//累加消息数
        }
        test.find(".msg_info").text(msg);
        test.find(".time-class").text(time);
        return;
    }
    var temp = $('  <li id=' + id + '>\n' +
        '            <div class="head-border-class">\n' +
        '                <img src='+head_url+'>\n' +
        '            </div>\n' +
        '            <div class="msg_right">\n' +
        '                <div class="msg_up">\n' +
        '                    <div class="bzhu-class">' + name + '</div>\n' +
        '                    <div class="time-class">' + time + '</div>\n' +
        '                </div>\n' +
        '                <div class="msg_down">\n' +
        '                    <div class="msg_info">'+msg+'</div>\n' +
        '                    <span id="no_read">1</span>\n' +
        '                </div>' +
        '            </div>\n' +
        '        </li>');
    temp.prependTo("#msg_list");
    temp.click(function () {
        //获取到id和名字
        var id = $(this).attr("id");
        var name = $(this).find('.bzhu-class').text();
        sessionStorage.setItem("targetId", id);
        sessionStorage.setItem("targetName", name);
        location.href = "chat.html";
    });
}

// $(window).scroll(function() {
//     var scrollTop = $(this).scrollTop();
//     var scrollHeight = $(document).height();
//     var windowHeight = $(this).height();
//     console.log(scrollTop);
//     console.log(scrollHeight);
//     console.log(windowHeight);
//     if (scrollTop + windowHeight === scrollHeight) {
//         alert("下一页");
//         // load_friends();
//     }
// });

