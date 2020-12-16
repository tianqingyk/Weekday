package com.team3.weekday.service;

import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.constant.NotifyConstant;
import com.team3.weekday.db.dao.FriendApplyRepository;
import com.team3.weekday.db.dao.FriendListRepository;
import com.team3.weekday.db.entity.FriendApply;
import com.team3.weekday.db.entity.FriendList;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.service.interf.IFriendService;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService implements IFriendService {

    @Autowired
    private IDataBus dataBus;

    @Autowired
    private FriendListRepository friendListRepository;

    @Autowired
    private FriendApplyRepository friendApplyRepository;

    @Override
    public void createFriend(Long userId1, Long userId2) {
        friendListRepository.save(new FriendList(userId1, userId2));
        friendListRepository.save(new FriendList(userId2, userId1));
    }

    @Override
    public void removeFriend(Long userId1, Long userId2) {
        friendListRepository.delete(friendListRepository.findByUserId1AndUserId2(userId1, userId2));
    }

    @Override
    public List<FriendList> getFriend(Long userId1) {
        return friendListRepository.findByUserId1(userId1);
    }

    @Override
    public Response createFriendApply(Long userId, Long friendId) throws Exception {
        /*
         * CAN NOT apply null user,  Apply or add a user repeatedly, apply themselves
         */
        //if user and member exist or not
        Optional<User> userOption = dataBus.userService().getUserById(userId);
        Optional<User> friendOption = dataBus.userService().getUserById(friendId);
        if (userOption.isEmpty() || friendOption.isEmpty()){
            return ResponseUtil.sendErrorMessage(CommonConstant.MEMBER_NOT_EXIST);
        }
        //can not add yourself
        if (userId.equals(friendId)){
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_ADD_YOURSELF);
        }
        FriendList friendList = friendListRepository.findByUserId1AndUserId2(userId, friendId);
        FriendApply friendApply = friendApplyRepository.findByUserId1AndUserId2(userId, friendId);
        if (friendList != null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_ALREADY_BEEN_FRIENDS);
        }
        if (friendApply != null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.ERROR_ALREADY_APPLIED_FRIENDS);
        }
        friendApply = friendApplyRepository.save(new FriendApply(userId, friendId));

        ResponseUtil.notifyUser(friendId, NotifyConstant.NOTIFY_FRIENDS_APPLY, getFriendApplies(friendId));
        return ResponseUtil.sendBody(ResponseState.OK, friendApply);
    }

    @Override
    public void removeFriendApply(Long friendId, Long userId) {
        friendApplyRepository.delete(friendApplyRepository.findByUserId1AndUserId2(friendId, userId));
    }

    @Override
    public List<FriendApply> getFriendApply(Long userId) {
        return friendApplyRepository.findByUserId2(userId);
    }

    @Override
    public Response replyFriendApply(boolean acceptOrNot, Long friendApplyId) throws Exception {

        FriendApply friendApply = friendApplyRepository.findById(friendApplyId).get();
        if (friendApply == null) {
            return ResponseUtil.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG);
        }
        Long friendId = friendApply.getUserId1();
        Long userId = friendApply.getUserId2();

        if (acceptOrNot) {
            createFriend(friendId, userId);
        }
        friendApplyRepository.delete(friendApply);

        if (acceptOrNot){
            ResponseUtil.notifyUser(userId, NotifyConstant.NOTIFY_FRIENDS_LIST, getFriendList(userId));
            ResponseUtil.notifyUser(friendId, NotifyConstant.NOTIFY_FRIENDS_LIST, getFriendList(friendId));
        }
        ResponseUtil.notifyUser(userId, NotifyConstant.NOTIFY_FRIENDS_APPLY, getFriendApplies(userId));

        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response getFriendApplies(Long userId) {
        List<FriendApply> friendApplies = dataBus.friendService().getFriendApply(userId);
        if (friendApplies == null) {
            ResponseUtil.send(ResponseState.OK, CommonConstant.NUll_FRIEND_APPLY);
        }

        List<JSONObject> userList = new ArrayList<>();
        for (FriendApply friend : friendApplies) {
            User user = dataBus.userRepository().findById(friend.getUserId1()).get();
            if (user != null) {
                JSONObject json = new JSONObject();
                json.put("user", user);
                json.put("friendApplyId", friend.getId());
                userList.add(json);
            }
        }
        return ResponseUtil.sendBody(ResponseState.OK, userList);

    }

    @Override
    public Response getFriendList(Long userId) {
        List<FriendList> friendList = dataBus.friendService().getFriend(userId);
        if (friendList == null) {
            ResponseUtil.send(ResponseState.OK, CommonConstant.NUll_FRIEND_LIST);
        }
        List<User> userList = new ArrayList<>();
        for (FriendList friend : friendList) {
            User user = dataBus.userRepository().findById(friend.getUserId2()).get();
            if (user != null) {
                userList.add(user);
            }
        }
        return ResponseUtil.sendBody(ResponseState.OK, userList);
    }
}
