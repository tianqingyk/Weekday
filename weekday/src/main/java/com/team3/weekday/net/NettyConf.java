package com.team3.weekday.net;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

/**
 * @author yangke
 * @title: NettyConf
 * @projectName weekday
 * @date 2020-09-27
 */

@Component
@ConfigurationProperties(prefix = "nettyconf")
public class NettyConf {

    @NotEmpty
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
