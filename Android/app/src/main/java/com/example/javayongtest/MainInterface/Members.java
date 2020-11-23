package com.example.javayongtest;

public class Members {

    private String userNickName;
    private String userIP;
    private boolean readyCheck;

    public Members(){}

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public boolean isReadyCheck() {
        return readyCheck;
    }

    public void setReadyCheck(boolean readyCheck) {
        this.readyCheck = readyCheck;
    }
}
