package com.team3.weekday.utils;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-17
 */
public class WeekdayExcepetionFactory {

    private static WeekdayExcepetionFactory instance = new WeekdayExcepetionFactory();

    public static WeekdayExcepetionFactory getInstance(){
        return instance;
    }

    public static ErrorMessageException createErrorMessageException(String message){
        return new ErrorMessageException(message);
    }


}
