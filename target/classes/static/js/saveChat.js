function save() {
    // console.log($("#chat_note").text());
    // console.log($("#chat_note").val());
    var chat_note = $("#chat_note").html();
    localStorage.setItem(localStorage.getItem("local_id")+"_"+sessionStorage.getItem("targetId"), chat_note);
}

function saveOne(targetId, msg,status) {
    var key = localStorage.getItem("local_id")+"_"+targetId;
    var chat_note = localStorage.getItem(key);
    var style0;
    if (status=="right"){
        style0 = "msg_right_border";
    }else {
        style0 = "msg_left_border";
    }
    var temp = '<span class='+style0+'>' + msg + '</span>\n' +
        '        <div class="time-split"></div>';
    if (chat_note) {
        chat_note = chat_note + temp;
    } else {
        chat_note = temp;
    }
    localStorage.setItem(key,chat_note);
}