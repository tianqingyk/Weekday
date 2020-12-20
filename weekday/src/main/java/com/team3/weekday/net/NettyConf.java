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
@ConfigurationProperties(prefix = "netty")
public class NettyConf {

    @NotEmpty
    private int port;

    @NotEmpty
    private int mtu;

    @NotEmpty
    private int maxContentLength;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMtu() {
        return mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    public int getMaxContentLength() {
        return maxContentLength;
    }

    public void setMaxContentLength(int maxContentLength) {
        this.maxContentLength = maxContentLength;
    }
}
