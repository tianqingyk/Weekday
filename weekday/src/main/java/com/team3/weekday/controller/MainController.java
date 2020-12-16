package com.team3.weekday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yangke
 * @title: MainController
 * @projectName weekday
 * @date 2020-09-19
 */

@Controller
public class MainController {

    @RequestMapping("/main")
    public String viewMain(){
        return "test/example/main";
    }
}
