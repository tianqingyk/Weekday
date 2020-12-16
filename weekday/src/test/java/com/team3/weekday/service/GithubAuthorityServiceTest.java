package com.team3.weekday.service;

import com.team3.weekday.IDataBus;
import com.team3.weekday.WeekdayApplication;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/11/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeekdayApplication.class)
class GithubAuthorityServiceTest {

    @Autowired
    private IDataBus dataBus;

    @Test
    void getParam() {
        Map<String, String> map = new HashMap<>();
        map.put("src", "src1");
        map.put("img", "img1");
        Assert.assertEquals(GithubAuthorityService.getParam("src=src1&img=img1"), map);

        Map<String, String> map1 = new HashMap<>();
        map1.put("src", "src1");
        map1.put("img", "img1");
        map1.put("name", "yangke");
        Assert.assertEquals(GithubAuthorityService.getParam("src=src1&img=img1&name=yangke"), map1);
    }
}