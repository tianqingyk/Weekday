package com.team3.weekday.action;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/11/28
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.team3.weekday.IDataBus;
import com.team3.weekday.net.ChannelSession;
import com.team3.weekday.net.NettyServer;
import com.team3.weekday.net.annotation.Action;
import com.team3.weekday.net.annotation.ActionMethodHandler;
import com.team3.weekday.net.annotation.Command;
import com.team3.weekday.utils.ResponseUtil;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Action
public class TestAction {

    @Autowired
    private IDataBus dataBus;
    @Autowired
    private NettyServer nettyServer;

    @Command
    public Response getCmd(ChannelSession session) throws Exception {
        Map<String, ActionMethodHandler> commandMap = nettyServer.getCommandMap();
        JSONArray array = new JSONArray();
        for (var entry  : commandMap.entrySet()) {
            JSONObject json = new JSONObject(true);
            json.put("cmd", entry.getKey());
            for (var param  : entry.getValue().getParams() ) {
                json.put(param.getName(), "");
            }
            array.add(json);
        }
        return ResponseUtil.sendBody(ResponseState.OK, array);
    }

}