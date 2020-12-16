package com.team3.weekday.valueobject;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-31
 */
public enum ChatType {
    NONE(0),
    TEXT(1),
    IMAGE(2),
    VIDEO(3);


    private int type;

    ChatType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static ChatType getChatType(int type){
        for ( var chatType : ChatType.values()){
            if (chatType.type == type){
                return chatType;
            }
        }
        return null;
    }
}
