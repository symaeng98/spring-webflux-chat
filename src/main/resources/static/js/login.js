const loginCheck = () => {
    var id = $("#id-input").val();
    var pw = $("#pw-input").val();
    console.log(id);
    console.log(pw);

    if (id == ''){
        alert("아이디를 입력하세요.");
    }
    else if (pw == ''){
        alert("비밀번호를 입력하세요.");
    }
    else{
        var id = $('#id-input').val();
        var password = $("#pw-input").val();
        console.log(id);
        console.log(password);
        var data = {
            memberId: id,
            password: password
        };
        gfnPostFetch("/api/member/login", data)
        .then(function(result){
            console.log(result);
            if(result.status){
                openAlert(result.message)
                .then(function(){
                        location.href = "/home";
                    }
                )
            } else{
                openAlert("아이디 혹은 비밀번호를 확인하세요.");
            }
        }).catch(function(err) {
            console.log(err)
            openAlert(err);
        });
    }
}

$("#id-input").focusout(function () {
    var id = $("#id-input").val();
    console.log(id);
    if (id == ''){
        $("#id-empty").css('display', 'inline-block');
    }
    else{
        $("#id-empty").css('display', 'none');
    }
});