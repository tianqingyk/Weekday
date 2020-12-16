package com.team3.weekday.valueobject;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-31
 */
public class ChatMessage {

    private Long owner;

    private Long param1; // teamId; groupId

    private int type;

    private String message;

    public ChatMessage(Long owner, int type, String message) {
        this.owner = owner;
        this.type = type;
        this.message = message;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getParam1() {
        return param1;
    }

    public void setParam1(Long param1) {
        this.param1 = param1;
    }
}
