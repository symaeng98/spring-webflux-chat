//한 페이지에는 (1,이지윤,1) 다른페이지에는 (5, 이종혁,1) 넣으면 테스트 데이터 확인 가능
const searchParams=new URLSearchParams(location.search);

// http://localhost:8080/?user-idx=1&user-name=맹순영&other-name=뉴진스
// http://localhost:8080/?user-idx=2&user-name=뉴진스&other-name=맹순영
let userIdx=searchParams.get('user-idx') //채팅방 조회 api로 세개 다 넘어올 예정-> prompt는 사용하지 않고 대체할 것
let userName=searchParams.get('user-name'); //userIdx, userName : 자기 자신 회원 idx, 이름
//let roomNum=searchParams.get('room-num') // 채팅방 번호
let otherName=searchParams.get('other-name')//이건 채팅방 목록 조회 api에서 넘어옵니다
console.log(userIdx)

document.querySelector("#username").innerHTML = `${otherName}님과 채팅중입니다.`;

const eventSource= new EventSource("http://localhost:8080/chat/sse");
eventSource.onmessage=(event)=>{
    const data=JSON.parse(event.data);
    //상대방으로부터 메세지가 전송되는 이벤트가 발생하면 DB에 저장된 데이터를 불러온다.
    if(data.chatDto.senderId==userIdx){ //전송자가 내가 아니면 다른사람이므로 반대쪽에 렌더링하면 됨
        //파란박스 (내가 보낸 메세지)
        initMyMessage(data.chatDto);
    }
    else{
        //회색박스 (상대방이 보낸 메세지)
        initYourMessage(data.chatDto);
    }


}



//const eventSource= new EventSource("http://localhost:8080/chat/sse");
//eventSource.onmessage=(event)=>{
//    console.log(event.data);
//    const data = JSON.parse(event.data);
//    console.log(data);
//    var content = document.createElement("p");
//    content.innerHTML = `${data.chatDto.senderName}: ${data.chatDto.content}`;
//    document.querySelector("#chat-content").append(content);
//}


function getSendMsgBox(data) {

//    let md = data.createdAt.substring(5, 10)
//    let tm = data.createdAt.substring(11, 16)
//    convertTime = tm + " | " + md
    convertTime = data.createdAt;
    //내가 입력한 채팅 박스 생성하기
    return `
    <div class="sent_msg">
    <p>${data.content}</p>
    <span class="time_date"> ${convertTime} / <b>${data.senderName}</b> </span>
    </div>
    `;
}

function getReceivedMsgBox(data) {

    let md = data.createdAt.substring(5, 10)
    let tm = data.createdAt.substring(11, 16)
    convertTime = tm + " | " + md
    //상대편에서 보낸 채팅 박스 생성하기
    return `
    <div class="received_withd_msg">
    <p>${data.content}</p>
    <span class="time_date"> ${convertTime} / <b>${data.senderName}</b></span>
    </div>
    `;
}

//최초 채팅방 로드시 이전 DB 저장 내역 불러오며 초기화
function initMyMessage(data){
    //내가 보낸 메세지 초기화
    let chatBox= document.querySelector("#chat-box");

    let chatOutGoingBox =document.createElement("div");
    chatOutGoingBox.className="outgoing_msg";

    chatOutGoingBox.innerHTML=getSendMsgBox(data);
    chatBox.append(chatOutGoingBox);
    document.documentElement.scrollTop = document.body.scrollHeight;

}

function initYourMessage(data){
    //상대 쪽으로부터 메세지가 전송되면 DB로부터 불러옴
    let chatBox= document.querySelector("#chat-box");

    let chatReceivedBox =document.createElement("div");
    chatReceivedBox.className="received_msg";

    chatReceivedBox.innerHTML=getReceivedMsgBox(data);
    chatBox.append(chatReceivedBox);
    document.documentElement.scrollTop = document.body.scrollHeight;
}


//AJAX로 채팅 메세지 전송

async function newChat(){
    //DB에 insert하면 자동으로 event가 발생하면서 chatroom의 다른 상대방에게 보내짐 (다중 채팅도 가능)

    let msgInput=document.querySelector("#chat-outgoing-msg");


    let date=new Date();
    let now= date.getHours()+":"+date.getMinutes()+" | "+date.getMonth()+"- "+date.getDate();
    let today = new Date();
    let amPm = "오전 ";
    let hours = today.getHours();
    if (hours>=13){
        hours = hours %12;
        amPm = "오후 ";
    } else if(hours == 12){
        amPm = "오후 ";
    }
    let minutes = today.getMinutes();
    let chat={
        senderId:userIdx,
        senderName:userName,
        createdAt: amPm + hours + "시 " + minutes + "분",
//        room_num:roomNum,
        content: msgInput.value
    };

    fetch("/chat",{
        method:"post",//http post 메소드 (새로운 데이터를 write할때 사용)
        body:JSON.stringify(chat),
        headers:{
            "Content-Type":"application/json; charset=utf-8"
        }
    });

    msgInput.value="";
}

//채팅 전송 버튼 누를 시
document.querySelector("#chat-send").addEventListener("click",()=>{
    newChat();
})


//enter 누를 시
document.querySelector("#chat-outgoing-msg").addEventListener("keydown",(e)=>{
    if(e.keyCode==13){
        newChat();
    }
})
function send() {
    let content = document.getElementById('content').value;
    let user = document.getElementById('select-user').value;
    console.log(user);
    let senderId = 2;
    let receiverId = 1;
    let senderName = "조휴일";
    let receiverName = "맹순영";
    if (user==1){
        senderId = 1;
        receiverId = 2;
        senderName = "맹순영";
        receiverName = "조휴일";
    }
    let param = {
        content:content,
        senderId:senderId,
        receiverId:receiverId,
        senderName:senderName,
        receiverName:receiverName
    }
    console.log(param);

    fetch("/chat", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(param),
        }).then((response) => console.log(response));
}
