package com.team3.weekday.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-08
 */
public class JsonUtil {

    public static String toJson(Object object){
        JSONObject json = (JSONObject) JSONObject.toJSON(object);
        return JSONObject.toJSONString(json, true);
    }

    public static Object toBean(String json, Class clss){
        return JSON.parseObject(json, clss);
    }


}
