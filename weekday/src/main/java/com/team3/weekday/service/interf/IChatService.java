package com.team3.weekday.service.interf;

import com.team3.weekday.db.entity.ChatGroup;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.valueobject.ChatMessage;
import com.team3.weekday.valueobject.Response;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-11-02
 */
public interface IChatService {

    Response chat(User chatUser, ChatMessage chatMessage);

    Response groupChat(ChatGroup chatGroup, ChatMessage chatMessage);

    Response teamChat(Team team,  ChatMessage chatMessage);
}
