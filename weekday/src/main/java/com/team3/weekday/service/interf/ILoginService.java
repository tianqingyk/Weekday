package com.team3.weekday.service.interf;

import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.valueobject.Response;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-17
 */
public interface ILoginService {
    Response login(String username, String password, ChannelSession session) throws Exception;

    Response loginByAuthority(ChannelSession session) throws Exception;
}
