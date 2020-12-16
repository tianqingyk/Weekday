package com.team3.weekday.net;

import com.team3.weekday.db.entity.User;
import com.team3.weekday.utils.JsonUtil;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author yangke
 * @title: ChannelSession
 * @projectName weekday
 * @date 2020-09-27
 */
public class ChannelSession {

    private static Log log = LogFactory.getLog(ChannelSession.class);

    private Long userId;

    private User user;

    private String code;

    private ChannelHandlerContext ctx;

    private ChannelSessionState state = ChannelSessionState.INIT;

    private enum ChannelSessionState {
        INIT,
        LOGIN,
        OVERTIME
    }

    public ChannelSession(Long userId, String code) {
        this.userId = userId;
        this.code = code;
    }

    public ChannelSession(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ChannelSessionState getState() {
        return state;
    }

    public void setState(ChannelSessionState state) {
        this.state = state;
    }

    public static Log getLog() {
        return log;
    }

    public static void setLog(Log log) {
        ChannelSession.log = log;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    protected void sendReponse(Response response) {
//        ctx.writeAndFlush(JsonUtil.toJson(response));
        //TODO  yangke 需要修改 暂时这样写
        String res = JsonUtil.toJson(response);
        ctx.writeAndFlush(new TextWebSocketFrame(res));
        log.info("\nResponse:" + res);
    }

    protected void sendErrorMessage(String message, String cmd) {
        Response response = ResponseUtil.send(ResponseState.FAIL, message);
        response.setCmd(cmd);
        sendReponse(response);
    }

    public boolean isLogin() {
        return state == ChannelSessionState.LOGIN;
    }

    public void loginSuccess(User user) {
        userId = user.getId();
        this.user = user;
        state = ChannelSessionState.LOGIN;
        ChannelSessionManagement.getInstance().addChannelSession(this);
    }

}

