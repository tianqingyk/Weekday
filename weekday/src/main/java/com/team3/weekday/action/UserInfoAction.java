package com.team3.weekday.action;

import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.Command;
import com.team3.weekday.net.annotation.Param;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * @author yangke
 * @title: UserInfoAction
 * @projectName weekday
 * @date 2020-10-04
 */
@Action
public class UserInfoAction {

    @Autowired
    private IDataBus dataBus;

    @Command
    public Response getUserInfo(ChannelSession session) throws Exception{
        return ResponseUtil.sendBody(ResponseState.OK, session.getUser());
    }
    @Command
    public Response updateUser(@Param String name,
                               @Param String avatar,
                               @Param String email,
                               @Param Long phonenum,
                               @Param Date birthday,
                               @Param String label,
                               ChannelSession session) throws Exception {
        return dataBus.userService().updateUserInfo(session.getUser(), name, avatar, email, phonenum, birthday, label);
    }
    @Command
    public Response getUserListByName(@Param String name,
                                      @Param Integer page,
                                      ChannelSession session) throws Exception {

        Pageable pageable= PageRequest.of(page, CommonConstant.PAGE_SIZE);
        Page<User> userList= dataBus.userService().listUsersByNameLike(name,pageable);//getUserListByName(name);
        return ResponseUtil.sendBody(ResponseState.OK,userList);
    }

//    @Command
//    public Response activeUser(ChannelSession session) throws Exception {
//        return dataBus.emailSenderService().sendActiveEmail(session.getUser());
//    }
}
