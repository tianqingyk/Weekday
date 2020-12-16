package com.team3.weekday.service.interf;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @author yangke
 * @title: IAuthorityService
 * @projectName weekday
 * @date 2020-09-27
 */
public interface IAuthorityService {

    String getAccessToken(String code) throws Exception;

    JSONObject getUserInfo(String accessToken) throws IOException;
}
