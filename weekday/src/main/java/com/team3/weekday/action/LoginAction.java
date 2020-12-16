package com.team3.weekday.action;

import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.ChannelSessionManagement;
import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.Command;
import com.team3.weekday.net.annotation.Param;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-09
 */
@Action
public class LoginAction {

    @Autowired
    private IDataBus dataBus;


    @Command
    public Response login(@Param String username,
                          @Param String password,
                          ChannelSession session) throws Exception {
        if (session.isLogin()) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_ALREADY_LOGGED_IN);
        }
        if (username.isBlank() || password.isBlank()) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_LOG_IN_EMPTY_WRONG);
        }

        return dataBus.loginService().login(username, password, session);
    }

    @Command
    public Response loginByAuthority(@Param Long userId,
                                     @Param String code,
                                     ChannelSession session)  throws Exception {
        if (userId == null || code.isBlank()) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_LOG_IN_PARAM_WRONG);
        }
        ChannelSession channelSession = ChannelSessionManagement.getInstance().getChannelSession(userId);
        if (channelSession == null) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_LOG_IN_PARAM_WRONG);
        }
        if (session.isLogin()) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_ALREADY_LOGGED_IN);
        }
        if ( !code.equals(channelSession.getCode())) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_LOG_IN_PARAM_WRONG);
        }

        session.setUserId(userId);
        return dataBus.loginService().loginByAuthority(session);
    }

//    //just for test
//    @Command
//    public Response resetPassword(@Param String to,
//                                ChannelSession session) throws Exception {
//        System.out.println("to");
//        dataBus.emailSenderService().sendResetPwdEmail(to);
//        return ResponseUtil.send(ResponseState.OK);
//    }
}
