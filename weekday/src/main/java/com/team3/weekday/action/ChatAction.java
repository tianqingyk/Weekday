package com.team3.weekday.action;

import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.db.entity.ChatGroup;
import com.team3.weekday.db.entity.Team;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.Command;
import com.team3.weekday.net.annotation.Param;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.ChatMessage;
import com.team3.weekday.valueobject.ChatType;
import com.team3.weekday.valueobject.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-30
 */
@Action
public class ChatAction {

    @Autowired
    private IDataBus dataBus;

    @Command
    public Response chat(@Param Long userId,
                         @Param int type,
                         @Param String message,
                         ChannelSession session) throws Exception {
        ChatType chatType = ChatType.getChatType(type);
        if (chatType == null) {
            ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG, "chatType");
        }
        Optional<User> optionalUser = dataBus.userService().getUserById(userId);
        if (optionalUser.isEmpty()) {
            ResponseUtil.sendErrorMessage(CommonConstant.CHAT_USER_NOT_EXIST);
        }

        return dataBus.chatService().chat(optionalUser.get(), new ChatMessage(session.getUserId(), type, message));
    }

    @Command
    public Response groupChat(@Param Long groupId,
                              @Param int type,
                              @Param String message,
                              ChannelSession session) throws Exception {
        ChatType chatType = ChatType.getChatType(type);
        if (chatType == null) {
            ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG, "chatType");
        }
        Optional<ChatGroup> chatGroup = dataBus.chatGroupRepository().findById(groupId);
        if (chatGroup.isEmpty()) {
            ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG);
        }

        return dataBus.chatService().groupChat(chatGroup.get(), new ChatMessage(session.getUserId(), type, message));

    }

    @Command
    public Response teamChat(@Param Long teamId,
                             @Param int type,
                             @Param String message,
                             ChannelSession session) throws Exception {
        ChatType chatType = ChatType.getChatType(type);
        if (chatType == null) {
            ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG, "chatType");
        }
        Optional<Team> team = dataBus.teamRepository().findById(teamId);
        if (team.isEmpty()) {
            ResponseUtil.sendErrorMessage(CommonConstant.TEAM_NOT_EXIST);
        }

        return dataBus.chatService().teamChat(team.get(), new ChatMessage(session.getUserId(), type, message));
    }
}