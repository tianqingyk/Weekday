package com.team3.weekday.service.interf;

import com.team3.weekday.db.entity.FriendApply;
import com.team3.weekday.db.entity.FriendList;
import com.team3.weekday.valueobject.Response;

import java.util.List;

public interface IFriendService {

    void createFriend(Long userId1, Long userId2);

    void removeFriend(Long userId1, Long userId2);

    List<FriendList> getFriend(Long userId1);

    Response createFriendApply(Long userId, Long friendId) throws Exception;

    void removeFriendApply(Long friendId, Long userId);

    List<FriendApply> getFriendApply(Long userId);

    Response replyFriendApply(boolean acceptOrNot, Long friendApplyId) throws Exception;

    Response getFriendApplies(Long userId);

    Response getFriendList(Long userId);
}
