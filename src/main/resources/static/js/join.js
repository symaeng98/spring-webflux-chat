var isValidId = false;
var isValidPw = false;
var isSamePw = false;
var hasName = false;
var curId;

const checkId = () => {
    console.log("check ID");
    var id = $('.userid').val();
    console.log(id);
    if (id == '') {
        openAlert("아이디를 입력해주세요");
    }
    else {
        gfnGetFetch("/api/join/id-checking?userId="+id,)
            .then(function (result) {
                if (result.status) {
                    openAlert("사용 가능한 아이디입니다.");
                    curId = id;
                    isValidId = true;
                    return;
                }
                openAlert("사용할 수 없는 아이디입니다.");
                isValidId = false;
                return;
            })
    }
}

const joinCheck = () => {
    var id = $('.userid').val();
    if (curId != id){
        openAlert("아이디 중복확인이 필요합니다.")
    }
    else if (isValidId && isValidPw & isSamePw & hasName) {
        var password = $("#password_1").val();
        var name = $("#username").val();
        console.log(id);
        console.log(password);
        console.log(name);
        var data = {
            memberId: id,
            password: password,
            name: name,
        };
        gfnPostFetch("/api/join", data)
            .then(function (result) {
                  console.log(result);
                  if (result.status) {
                      openAlert("회원가입 성공")
                      .then(()=>{
                        location.href = "/login";
                      })
                  } else {
                      openAlert("회원가입 실패");
                  }
              })
            .catch(function (err) {
                console.log(err)
                openAlert(err);
            });
    } else {
        openAlert("조건에 충족하지 않는 항목이 있습니다.");
    }
}

$(".userpw").focusout(function () {
    var pwd1 = $("#password_1").val();
    var pwd2 = $("#password_2").val();

    if (pwd1 != '' && pwd2 == '') {
        null;
    } else if (pwd1 != "" || pwd2 != "") {
        if (pwd1 == pwd2) {
            $("#pw-correct").css('display', 'inline-block');
            $("#pw-wrong").css('display', 'none');
            isSamePw = true;
        } else {
            $("#pw-correct").css('display', 'none');
            $("#pw-wrong").css('display', 'inline-block');
            isSamePw = false;
        }
    }
});

$("#password_1").focusout(function () {
    var pwd = $("#password_1").val();
    var reg = new RegExp("^(?=.*[A-Za-z])(?=.*[$!%*#?&])(?=.*[0-9]).{8,}$");

    if (reg.test(pwd)) {
        $("#pw-combination-correct").css('display', 'inline-block');
        $("#pw-combination-wrong").css('display', 'none');
        isValidPw = true;
    } else {
        $("#pw-combination-correct").css('display', 'none');
        $("#pw-combination-wrong").css('display', 'inline-block');
        isValidPw = false;
    }
});

$("#username").focusout(function () {
    var username = $("#username").val();

    if (username == '') {
        $("#username-empty").css('display', 'inline-block');
        hasName = false;
    }
    else {
        $("#username-empty").css('display', 'none');
        hasName = true;
    }
});