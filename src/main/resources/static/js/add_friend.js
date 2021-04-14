function view_result(id, name, status,head_url) {
    var str;
    if (status === 1) {
        str = "已是好友";
    } else if (status === -1) {
        str = "我自己";
    } else {
        str = "";
    }
    var temp = $('<li id=' + id + '>\n' +
        '                    <div class="head-border-class">\n' +
        '                        <img src='+head_url+'>\n' +
        '                    </div>\n' +
        '                    <div class="li_right">\n' +
        '                        <div class="div_left">\n' +
        '                            ' + name + '\n' +
        '                        </div>\n' +
        '                        <div class="div_right">' + str + '</div>\n' +
        '                    </div>\n' +
        '                </li>');
    temp.appendTo("#result-list");
    temp.click(function () {
        sessionStorage.setItem("selectId", id);
        location.href = "info.html";
    });
}

$(function () {
    $("#search")[0].oninput = function () {
        var keyword = $(this).val();
        if (!keyword) {
            $("#result-list").html("");
            return;
        }
        $.ajax({
            url: '/user/' + keyword + '/findUser',
            type: 'get',
            dataType: "json",
            success: function (data) {
                $("#result-list").html("");
                if (data['flag']) {
                    // console.log(data);
                    var list = data['data'];
                    for (var i = 0; i < list.length; i++) {
                        view_result(list[i]['userId'], list[i]['userName'], list[i]['status'],list[i]['headUrl']);
                    }
                }
            }
        })
    }
});