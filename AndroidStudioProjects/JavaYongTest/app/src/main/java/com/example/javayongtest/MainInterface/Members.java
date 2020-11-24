package com.example.javayongtest.MainInterface;

public class Members {

    private String userNickName;
    private String userIP;
    private boolean readyCheck;

    public Members(){}

    public Members(String userNickName, boolean readyCheck){
        this.userNickName = userNickName;
        this.readyCheck = readyCheck;
    }

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
