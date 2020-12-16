package com.team3.weekday.net;

import com.team3.weekday.valueobject.Response;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangke
 * @title: ChannelSessionManagement
 * @projectName weekday
 * @date 2020-10-01
 */

public class ChannelSessionManagement {

    private static ChannelSessionManagement instance = new ChannelSessionManagement();

    public static ChannelSessionManagement getInstance(){
        return instance;
    }

    private Map<Long, ChannelSession> sessionMap;

    public ChannelSessionManagement() {
        this.sessionMap = new ConcurrentHashMap<>();
    }

    public ChannelSession getChannelSession(Long userId){
        return sessionMap.get(userId);
    }

    public void addChannelSession(ChannelSession channelSession){
        sessionMap.put(channelSession.getUserId(), channelSession);
    }

    public void notifyUser(Long userId, Response response){
        if (sessionMap.containsKey(userId)) {
            ChannelSession channelSession = sessionMap.get(userId);
            channelSession.sendReponse(response);
        }
    }

    public Collection<ChannelSession> getAllSession(){
        return sessionMap.values();
    }

}
