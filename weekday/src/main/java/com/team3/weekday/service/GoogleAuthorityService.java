package com.team3.weekday.service;

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author yangke
 * @projectName weekday
 * @date 2020/11/11
 */
@Service
public class GoogleAuthorityService extends DefaultAuthorityService {

    private static String CLIENT_ID = "550032846021-nfahvmb9ll8id6f6ccfkkf9ij0vmq39h";

    @Override
    public JSONObject getUserInfo(String accessToken) throws IOException {
        GoogleIdToken idToken = GoogleIdToken.parse(JacksonFactory.getDefaultInstance(), accessToken);
        JSONObject jsonObject = new JSONObject();
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            jsonObject.put("name", (String) payload.get("name"));
            jsonObject.put("avatar", (String) payload.get("picture"));
            jsonObject.put("id", userId);

            return jsonObject;

        } else {
            System.out.println("Invalid ID token.");
        }
        return null;
    }
}
