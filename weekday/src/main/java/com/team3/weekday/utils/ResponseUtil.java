package com.team3.weekday.utils;

import com.team3.weekday.net.ChannelSessionManagement;
import com.team3.weekday.valueobject.Response;
import com.team3.weekday.valueobject.ResponseState;

import java.util.List;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-16
 */
public class ResponseUtil {

    public static Response send(ResponseState state) {
        return send(state, null, "");
    }

    public static Response send(ResponseState state, String message) {
        return send(state, null, message);
    }

    public static Response sendBody(ResponseState state, Object body) {
        return send(state, body, "");
    }

    private static Response send(ResponseState state, Object body, String message) {
        boolean success = false;
        switch (state) {
            case OK:
                success = true;
                break;
            case FAIL:
                success = false;
                break;
        }
        return new Response(success, message, body);
    }

    public static Response sendErrorMessage(String message, String... args) throws ErrorMessageException {
        throw WeekdayExcepetionFactory.getInstance().createErrorMessageException(String.format(message,args));
    }

    public static void notifyUser(Long userId, String cmd, Response response) {
        response.setCmd(cmd);
        ChannelSessionManagement.getInstance().notifyUser(userId, response);
    }

    public static void notifyUserBody(Long userId, String cmd, Object body) {
        Response response = new Response(cmd, true, body);
        notifyUser(userId, cmd, response);
    }

    public static void notifyUsers(List<Long> userIdList, String cmd, Response response) {
        response.setCmd(cmd);
        for (Long userId  : userIdList) {
            ChannelSessionManagement.getInstance().notifyUser(userId, response);
        }
    }

    public static void notifyUsersBody(List<Long> userIdList, String cmd, Object body) {
        Response response = new Response(cmd, true, body);
        notifyUsers(userIdList, cmd, response);
    }

}
