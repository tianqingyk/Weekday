package com.team3.weekday.service;

import com.team3.weekday.constant.NotifyConstant;
import com.team3.weekday.db.entity.ChatGroup;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.service.interf.IChatService;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.ChatMessage;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.stereotype.Service;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-11-02
 */
@Service
public class ChatService implements IChatService {

    @Override
    public Response chat(User chatUser, ChatMessage chatMessage) {
        ResponseUtil.notifyUserBody(chatUser.getId(), NotifyConstant.NOTIFY_CHAT, chatMessage);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response groupChat(ChatGroup chatGroup, ChatMessage chatMessage) {
        chatMessage.setParam1(chatGroup.getId());
        ResponseUtil.notifyUsersBody(chatGroup.getMemberList(), NotifyConstant.NOTIFY_GROUP_CHAT, chatMessage);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response teamChat(Team team, ChatMessage chatMessage) {
        chatMessage.setParam1(team.getId());
        ResponseUtil.notifyUsersBody(team.getMemberList(), NotifyConstant.NOTIFY_TEAM_CHAT, chatMessage);
        return ResponseUtil.send(ResponseState.OK);
    }

}
