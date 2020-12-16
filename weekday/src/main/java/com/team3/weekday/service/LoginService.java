package com.team3.weekday.service;

import com.team3.weekday.IDataBus;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.db.entity.User;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.service.interf.ILoginService;
import com.team3.weekday.service.interf.IUserService;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-17
 */
@Service
public class LoginService implements ILoginService {

    @Autowired
    private IDataBus dataBus;

    @Override
    public Response login(String username, String password, ChannelSession session) throws Exception {
        IUserService userService = dataBus.userService();
        User user = userService.getUserByName(username);
        if (user == null) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_LOG_IN_PARAM_WRONG);
        }
        if (!CommonConstant.passwordEncoder.matches(password, user.getPassword())) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_LOG_IN_PASSWORD_WRONG);
        }

        session.loginSuccess(user);
        return ResponseUtil.send(ResponseState.OK);
    }

    @Override
    public Response loginByAuthority(ChannelSession session) throws Exception {
        Optional<User> optionalUser = dataBus.userRepository().findById(session.getUserId());
        if (optionalUser.isEmpty()) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_LOG_IN_PARAM_WRONG);
        }
        session.loginSuccess(optionalUser.get());
        return ResponseUtil.send(ResponseState.OK);
    }
}
