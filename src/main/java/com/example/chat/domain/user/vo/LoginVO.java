package com.example.chat.domain.user.vo;

public class LoginVO {
    private String memberId;
    private String password;

    public LoginVO(){}

    public LoginVO(String memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }
}
