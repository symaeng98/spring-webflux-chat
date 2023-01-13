const searchParams=new URLSearchParams(location.search);

// http://localhost:8080/?user_id=1&room_id=1&receiver_id=2&username=맹순영&receiver_name=뉴진스
// http://localhost:8080/?user_id=2&room_id=1&receiver_id=1&username=뉴진스&receiver_name=맹순영
let userId=searchParams.get('user_id') //채팅방 조회 api로 세개 다 넘어올 예정-> prompt는 사용하지 않고 대체할 것
console.log(userId + '잉')
let userName=searchParams.get('username'); //userId, userName : 자기 자신 회원 idx, 이름
let roomId=searchParams.get('room_id') // 채팅방 번호
let receiverId=searchParams.get('receiver_id') // 채팅방 번호
let receiverName=searchParams.get('receiver_name')//이건 채팅방 목록 조회 api에서 넘어옵니다

document.querySelector("#username").innerHTML = `${receiverName}님과 채팅중입니다.`;

loadChat();

const eventSource= new EventSource("http://localhost:8080/chat/"+userId+"/sse");
eventSource.onmessage=(event)=>{
    console.log(event.data);
    const data=JSON.parse(event.data);
    if (data.chatDto.roomId == roomId){
        //상대방으로부터 메세지가 전송되는 이벤트가 발생하면 DB에 저장된 데이터를 불러온다.
        if(data.chatDto.senderId==userId){ //전송자가 내가 아니면 다른사람이므로 반대쪽에 렌더링하면 됨
            //파란박스 (내가 보낸 메세지)
            initMyMessage(data.chatDto);
        }
        else if(data.chatDto.senderId == receiverId){
            //회색박스 (상대방이 보낸 메세지)
            initYourMessage(data.chatDto);
        }
        else{
            console.log(data.chatDto.senderId + "님께서 메시지를 보냈습니다.");
        }
    }
}


function getSendMsgBox(data) {
    convertTime = data.createdAt;
    console.log(data);
    //내가 입력한 채팅 박스 생성하기
    return `
    <div class="sent_msg">
    <p>${data.content}</p>
    <span class="time_date"> ${convertTime} / <b>${data.senderName}</b> </span>
    </div>
    `;
}

function getReceivedMsgBox(data) {
    convertTime = data.createdAt;
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
    console.log(userName);
    let chat={
        senderId:userId,
        senderName:userName,
        receiverId:receiverId,
        receiverName:receiverName,
        createdAt: amPm + hours + "시 " + minutes + "분",
        roomId:roomId,
        content: msgInput.value
    };
    // 내꺼 뿌리고 전송
    initMyMessage(chat);
    fetch("/chat",{
        method:"post",//http post 메소드 (새로운 데이터를 write 할 때 사용)
        body:JSON.stringify(chat),
        headers:{
            "Content-Type":"application/json; charset=utf-8"
        }
    });

    msgInput.value="";
}

function loadChat(){
    console.log("loadChat 실행");
    fetch("/chat?" + new URLSearchParams({
        userId : userId,
        receiverId : receiverId
    }))
    .then((response)=>response.json())
    .then(function(res){
        if(res.status){
            let data = res.data;
            console.log(data);
            data.forEach(c => {
                console.log(c);
                //상대방으로부터 메세지가 전송되는 이벤트가 발생하면 DB에 저장된 데이터를 불러온다.
                if(c.senderId==userId){ //전송자가 내가 아니면 다른사람이므로 반대쪽에 렌더링하면 됨
                    //파란박스 (내가 보낸 메세지)
                    initMyMessage(c);
                }
                else if(c.senderId == receiverId){
                    //회색박스 (상대방이 보낸 메세지)
                    initYourMessage(c);
                }
                else{
                    console.log(c.senderId + "님께서 메시지를 보냈습니다.");
                }
            })
        }
        else{
            alert("이전 채팅 불러오기를 실패하였습니다.");
        }
    })
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