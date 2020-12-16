package com.team3.weekday.service;

import com.team3.weekday.IDataBus;
import com.team3.weekday.WeekdayApplication;
import com.team3.weekday.db.dao.*;
import com.team3.weekday.db.entity.FriendApply;
import com.team3.weekday.db.entity.FriendList;
import com.team3.weekday.db.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/11/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeekdayApplication.class)
class FriendServiceTest {

    @Autowired
    private IDataBus dataBus;
    @MockBean
    private FriendApplyRepository friendApplyRepository;
    @MockBean
    private FriendListRepository friendListRepository;

    @Test
    void getFriend() {
        FriendList friendList1 = new FriendList(1L,2L);
        FriendList friendList2 = new FriendList(2L,1L);
        FriendList friendList3 = new FriendList(1L,3L);
        FriendList friendList4 = new FriendList(3L,1L);
        FriendList friendList5 = new FriendList(2L,3L);
        FriendList friendList6 = new FriendList(3L,2L);
        List<FriendList> friendList = new ArrayList<>();
        friendList.add(friendList1);        friendList.add(friendList2);
        friendList.add(friendList3);        friendList.add(friendList4);
        friendList.add(friendList5);        friendList.add(friendList6);
        Mockito.when(friendListRepository.findByUserId1(1L)).thenReturn(friendList);
        Assert.assertEquals(dataBus.friendService().getFriend(1L),friendList);
        Mockito.when(friendListRepository.findByUserId1(2L)).thenReturn(friendList);
        Assert.assertEquals(dataBus.friendService().getFriend(2L),friendList);
        Mockito.when(friendListRepository.findByUserId1(3L)).thenReturn(friendList);
        Assert.assertEquals(dataBus.friendService().getFriend(3L),friendList);
    }

    @Test
    void getFriendApply() {
        FriendApply friendApply1 = new FriendApply(1L,3L);
        FriendApply friendApply2 = new FriendApply(2L,3L);
        FriendApply friendApply3 = new FriendApply(1L,2L);
        List<FriendApply> friendApply = new ArrayList<>();
        List<FriendApply> friendApply0 = new ArrayList<>();
        friendApply.add(friendApply1);        friendApply.add(friendApply2);
        friendApply0.add(friendApply3);
        Mockito.when(friendApplyRepository.findByUserId2(3L)).thenReturn(friendApply);
//        Assert.assertEquals(dataBus.friendService().getFriendApply(2L),friendApply);
        Assert.assertEquals(dataBus.friendService().getFriendApply(3L),friendApply);
        Mockito.when(friendApplyRepository.findByUserId2(2L)).thenReturn(friendApply);
        Assert.assertEquals(dataBus.friendService().getFriendApply(2L),friendApply0);
    }
}