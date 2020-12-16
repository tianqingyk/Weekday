package com.team3.weekday.utils;

import org.apache.commons.beanutils.ConvertUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringBuilderUtil {

    public static String deleteElement(Long id, String oldString) {
        if(!oldString.isBlank()) {
            var list =stringToList(oldString);
            list.remove(id);
            return listToString(list);
        }
        return oldString;
    }
    public static String addElement(Long id, String oldString){
        var list = stringToList(oldString);
        list.add(id);
        return listToString(list);
    }

    public static String listToString(List<Long> list){
        StringBuilder newString = new StringBuilder();
        for (Long i : list) {
            newString.append(i).append(";");
        }
        if (newString.length() > 0) {
            newString.deleteCharAt(newString.length() - 1);
        }
        return newString.toString();
    }

    public static ArrayList<Long> stringToList(String str){
        if (str == null || str.isBlank()){
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList((Long[]) ConvertUtils.convert(str.split(";"), Long.class)));
    }
}
