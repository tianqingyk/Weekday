package com.team3.weekday.net;

import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.ActionMethodHandler;
import com.team3.weekday.net.annotation.Command;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangke
 * @title: NettyServer
 * @projectName weekday
 * @date 2020-09-27
 */
@Component
public class NettyServer implements ApplicationRunner, ApplicationContextAware {

    @Autowired
    private NettyConf nettyConf;

    private ApplicationContext applicationContext;

    private Map<String, ActionMethodHandler> commandMap = new HashMap<>();

    private void beforeStart() throws Exception {
        var beanMap = applicationContext.getBeansWithAnnotation(Action.class);

        for (Object action : beanMap.values()) {
            var methods = action.getClass().getMethods();
            for (var method : methods) {
                for (var annotation : method.getAnnotations()) {
                    if (annotation.annotationType().equals(Command.class)) {
                        commandMap.put(method.getName(), new ActionMethodHandler(action, method));
                        break;
                    }
                }
            }

        }
    }


    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            sb.option(ChannelOption.SO_BACKLOG, nettyConf.getMtu());
            sb.group(group, bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(nettyConf.getPort())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-codec", new HttpServerCodec());
                            ch.pipeline().addLast("aggregator", new HttpObjectAggregator(nettyConf.getMaxContentLength()));
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true));
                            ch.pipeline().addLast(new InBoundHandler(commandMap));
                        }
                    });
            ChannelFuture cf = sb.bind().sync();
            System.out.println(NettyServer.class + " Start Listening： " + cf.channel().localAddress());
            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.beforeStart();
        this.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Map<String, ActionMethodHandler> getCommandMap() {
        return commandMap;
    }
}
