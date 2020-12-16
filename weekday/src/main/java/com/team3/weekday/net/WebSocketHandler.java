package com.team3.weekday.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Scanner;

/**
 * @author yangke
 * @title: WebSocketHandler
 * @projectName weekday
 * @date 2020-09-27
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame twsf) throws Exception {
        System.out.println("客户端收到服务器数据：" + twsf.text());
        Scanner s = new Scanner(System.in);
        System.out.println("服务器推送：");
        while (true) {
            String line = s.nextLine();
            if (line.equals("exit")) {
                ctx.channel().close();
                break;
            }
            String resp = "(" + ctx.channel().remoteAddress() + "):" + line;
            ctx.writeAndFlush(new TextWebSocketFrame(resp));
        }
    }
}
