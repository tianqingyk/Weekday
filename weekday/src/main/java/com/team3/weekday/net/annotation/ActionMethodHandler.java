package com.team3.weekday.net.annotation;

import com.team3.weekday.constant.CommonConstant;
import com.team3.weekday.net.ChannelSession;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-15
 */
public class ActionMethodHandler {

    private Object object;

    private Method method;

    private List<Parameter> params;

    public ActionMethodHandler(Object object, Method method) {
        this.object = object;
        this.method = method;
        this.params = new ArrayList<>();
        for (Parameter param : this.method.getParameters()) {
            for (var annotation : param.getAnnotations())
                if (annotation.annotationType().equals(Param.class)) {
                    this.params.add(param);
                }
        }
    }

    public Object invokeMethod(Map<String, String> paramMap, ChannelSession session) throws Exception {
        Object[] args = new Object[params.size() + 1];
        for (int i = 0; i < params.size(); i++) {
            Parameter param = params.get(i);
            String value = paramMap.get(param.getName());
            args[i] = covertType(param.getType(), value);
        }
        args[params.size()] = session;
        return method.invoke(object, args);
    }

    private Object covertType(Class type, String value) throws ParseException {
        if (type.equals(String.class)) {
            if (value == null) return "";
            return value;
        }
        if (value == null || value.isBlank()) {
            return null;
        }
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        if (type.equals(long.class) || type.equals(Long.class)) {
            return Long.parseLong(value);
        }
        if (type.equals(float.class) || type.equals(Float.class)) {
            return Float.parseFloat(value);
        }
        if (type.equals(double.class) || type.equals(Double.class)) {
            return Double.parseDouble(value);
        }
        if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return Boolean.parseBoolean(value);
        }
        if (type.equals(Date.class)) {
            return CommonConstant.DATE_FORMAT.parse(value);
        }
        return null;
    }

    public List<Parameter> getParams() {
        return params;
    }
}
