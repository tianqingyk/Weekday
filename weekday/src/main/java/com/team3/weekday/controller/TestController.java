package com.team3.weekday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author yangke
 * @title: Test Controller
 * @projectName weekday
 * @date 2020-09-10
 */

@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello world!";
    }

    @RequestMapping("/test")
    public String test(){
        //classpath:/templates/testNodejs.html
        return "test/example/testNodejs";
    }

    @RequestMapping("/test2")
    public String test2(Map<String, Object> map){
        map.put("info1", "Information1");
        return "test/example/test2";
    }

    @RequestMapping("/testNetty")
    public String testNetty(){
        return "test/testNetty";
    }

}
