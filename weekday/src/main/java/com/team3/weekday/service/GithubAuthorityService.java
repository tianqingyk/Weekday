package com.team3.weekday.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangke
 * @title: AuthorityService
 * @projectName weekday
 * @date 2020-09-27
 */
@Service
public class GithubAuthorityService extends DefaultAuthorityService {

    private static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s&state=%s";

    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&state=%s";

    private static final String USER_INFO_URL = "https://api.github.com/user";

    private static final String CALLBACK_URL = "http://localhost:8080/github";

    private static final String API_KEY = "988ac063dc209e02252f";//Client ID
    private static final String API_SECRET = "75d9b3d653d745144702bd9dccf259ba87f7fba2";//Client Secret
    private static final String GITHUB_STATE = "use-login";

    public static Map<String, String> getParam(String string){
        Map<String, String> map = new HashMap<>();
        String[] kstring = string.split("&");
        for (String str  : kstring) {
            String[] kv = str.split("=");
            if (kv.length == 2){
                map.put(kv[0], kv[1]);
            }else if (kv.length == 1){
                map.put(kv[0], "");
            }

        }
        return map;
    }

    @Override
    public String getAccessToken(String code) {
        String url = String.format(ACCESS_TOKEN_URL, API_KEY, API_SECRET, code, CALLBACK_URL, GITHUB_STATE);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = uriComponentsBuilder.build().encode().toUri();
        String resp = getRestTemplate().getForObject(uri, String.class);
        System.out.println(resp);
        if (resp != null && resp.contains("access_token")){
            Map<String, String> data = getParam(resp);
            return data.get("access_token");
        }
        return null;
    }

    @Override
    public JSONObject getUserInfo(String accessToken) {
        URL url = null;
        HttpURLConnection httpUrlConnection = null ;
        InputStream inputStream = null;
        BufferedReader in = null;
        String resp = "";
        String str;
        try {
            url = new URL(USER_INFO_URL);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setConnectTimeout(1000);
            httpUrlConnection.setReadTimeout(1000);
            httpUrlConnection.setRequestProperty("Authorization","token "+accessToken);
            inputStream = httpUrlConnection.getInputStream();
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((str = in.readLine()) != null) {
                resp += str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(resp);
    }
}
