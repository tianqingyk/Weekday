package com.team3.weekday.net;

import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.net.annotation.ActionMethodHandler;
import com.team3.weekday.utils.ErrorMessageException;
import com.team3.weekday.utils.JsonUtil;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * author yangke
 * title: InBoundHandler
 * projectName weekday
 * date 2020-09-27
 */
public class InBoundHandler extends ChannelInboundHandlerAdapter {

    private Log log = LogFactory.getLog(InBoundHandler.class);

    private AttributeKey<ChannelSession> sessionKey = AttributeKey.valueOf("session");

    private Map<String, ActionMethodHandler> commandMap;

    public InBoundHandler(Map<String, ActionMethodHandler> commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.channel().attr(sessionKey).set(new ChannelSession(ctx));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else {
            ChannelSession session = ctx.channel().attr(sessionKey).get();
            if (msg instanceof TextWebSocketFrame) { //TODO yangke 需要修改
                String request = ((TextWebSocketFrame) msg).text();
                Map map = (Map) JsonUtil.toBean(request, HashMap.class);
                log.info("\nRequest: " + JSONObject.toJSONString(map, true));
                String cmd = (String) map.get("cmd");
                try {
                    tellChannelSession(session, cmd, map);
                } catch (ErrorMessageException e) {
                    session.sendErrorMessage(e.getMessage(), cmd);
                } catch (InvocationTargetException e) {
                    if (e.getTargetException() instanceof ErrorMessageException) {
                        session.sendErrorMessage(e.getTargetException().getMessage(), cmd);
                    } else {
                        e.getTargetException().printStackTrace();
                        session.sendErrorMessage(CommonConstant.PARAMETER_IS_WRONG, cmd);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest msg) {
        //TODO yangke
    }

    private void tellChannelSession(ChannelSession session, String cmd, Map map) throws Exception {
        if ( !CommonConstant.PERMITTED_COMMANDS.contains(cmd) && !session.isLogin()) {
            ResponseUtil.sendErrorMessage(CommonConstant.ERROR_NOT_LOG_IN);
            return;
        }

        if (commandMap.containsKey(cmd)) {
            var actionMethodHandler = commandMap.get(map.get("cmd"));
            Response response = (Response) actionMethodHandler.invokeMethod(map, session);
            response.setCmd(cmd);
            session.sendReponse(response);
        }
    }
}
