package com.team3.weekday.action;

import com.team3.weekday.IDataBus;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.Command;
import com.team3.weekday.net.annotation.Param;
import com.team3.weekday.valueobject.Response;
import org.springframework.beans.factory.annotation.Autowired;

@Action
public class FriendAction {
    @Autowired
    private IDataBus dataBus;

    @Command
    public Response getFriendList(ChannelSession session) throws Exception {
        return dataBus.friendService().getFriendList(session.getUserId());
    }

    @Command
    public Response getFriendApplies(ChannelSession session) throws Exception {
        return dataBus.friendService().getFriendApplies(session.getUserId());
    }

    @Command
    public Response friendApply(@Param Long friendId,
                                ChannelSession session) throws Exception {
        return dataBus.friendService().createFriendApply(session.getUserId(), friendId);
    }

    @Command
    public Response friendReply(@Param boolean acceptOrNot,
                                @Param Long friendApplyId,
                                ChannelSession session) throws Exception {
        return dataBus.friendService().replyFriendApply(acceptOrNot, friendApplyId);
    }
}
