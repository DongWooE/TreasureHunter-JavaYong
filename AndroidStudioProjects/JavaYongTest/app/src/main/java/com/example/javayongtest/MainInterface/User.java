package com.example.javayongtest.MainInterface;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String IP;
    public String nickName;
    public Integer score;

    public User(){};

    public User(String nickName, Integer score){
        this.nickName = nickName;
        this.score = score;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("nickName", nickName);
        result.put("score", score);

        return result;
    }
}
