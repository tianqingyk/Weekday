package com.team3.weekday.constant;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020-10-17
 */
public class CommonConstant {

    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String DEFAULT_AVATAR = "/static/images/head.png";

    public static List<String> PERMITTED_COMMANDS = Arrays.asList("login", "getCmd", "loginByAuthority");

    //Error Message
    public static String PARAMETER_IS_WRONG = "Parameters %s is wrong!";
    public static final String ERROR_MESSAGE = "Error message: %s !";
    public static String ERROR_NOT_LOG_IN = "Please log in first!";
    public static String ERROR_LOG_IN_PARAM_WRONG = "Username is wrong!";
    public static String ERROR_LOG_IN_PASSWORD_WRONG = "Password is wrong!";
    public static String ERROR_ALREADY_LOGGED_IN = "Already logged in!";
    public static String ERROR_ALREADY_BEEN_FRIENDS = "Already been friends!";
    public static String ERROR_ALREADY_APPLIED_FRIENDS = "Already applied!";
    public static final String ERROR_ADD_YOURSELF = "Can't add yourself";
    public static final String PERMISSION_DENIED = "Permission denied: %s !";
    public static final String ALREADY_BEEN_MEMBER = "Already been member";

    //Null Message
    public static String NUll_FRIEND_LIST = "Friend list is empty!";
    public static String NUll_FRIEND_APPLY = "No friend apply!";

    //Empty Message
    public static final String ERROR_LOG_IN_EMPTY_WRONG = "Username/Password is empty!";
    public static final String ERROR_TEAM_ID_EMPTY = "MemberID/TeamID is empty!";
    public static final String ERROR_TEAM_EMPTY = "TeamID is empty!";
    public static final String ERROR_PROJECT_EMPTY = "ProjectID is empty!";
    public static final String ERROR_TASK_EMPTY = "TaskID is empty!";

    //Chat Error Message
    public static String CHAT_USER_NOT_EXIST = "UserId is wrong";

    //NOT EXIST
    public static final String MEMBER_NOT_EXIST = "Member does not exist";
    public static final String TEAM_NOT_EXIST = "Team does not exist";
    public static final String PROJECT_NOT_EXIST = "Project does not exist";
    public static final String TASK_NOT_EXIST = "Task does not exist";

    public static final Integer PAGE_SIZE = 4;
}
