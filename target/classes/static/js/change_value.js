function update_value(key,value) {
    $.ajax({
        url: "/user",
        dataType: "json",
        type: "post",
        data: ''+key+'='+value,
        success: function (data) {
            if (data["flag"]) {
                alert(data['message']);
                if (key!="userHeadUri"){
                    location.href = "info.html";
                }
            }
        }
    });
}