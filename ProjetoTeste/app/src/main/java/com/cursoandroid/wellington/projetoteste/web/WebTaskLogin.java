package com.cursoandroid.wellington.projetoteste.web;

import android.content.Context;

import com.cursoandroid.wellington.projetoteste.R;
import com.cursoandroid.wellington.projetoteste.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wellington on 28/01/18.
 */

public class WebTaskLogin extends WebTaskBase {

    private static final String SERVICE_URL = "login";
    private String email;
    private String password;

    private final String FIELD_USERNAME = "username";
    private final String FIELD_PASSWORD = "password";

    public WebTaskLogin(Context context, String email, String password) {
        super(context, SERVICE_URL);
        this.email = email;
        this.password = password;
    }

    @Override
    void handleResponse(String response) {
        try{
            //get Response with Gson
            Gson gson = new Gson();
            User user = gson.fromJson(response, User.class);

            //get Response with JSONObject
//            String username = new JSONObject(response).getString("username");
//            String token = new JSONObject(response).getString("token");
//            String photoUrl = new JSONObject(response).getString("photoUrl");
//
//            User user = new User();
//            user.setUsername(username);
//            user.setToken(token);
//            user.setPhotoUrl(photoUrl);

            EventBus.getDefault().post(user);

        }catch (JsonSyntaxException e) {
            EventBus.getDefault().post(new Error(getContext().getString(R.string.error_field_email)));
        }
    }

    @Override
    String getRequestBody() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put(FIELD_USERNAME, email);
        requestMap.put(FIELD_PASSWORD, password);

        JSONObject json = new JSONObject(requestMap);
        String jsonString  = json.toString();

        return jsonString;
    }
}
