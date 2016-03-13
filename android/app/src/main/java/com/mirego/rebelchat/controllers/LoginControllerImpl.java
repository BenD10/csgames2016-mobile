package com.mirego.rebelchat.controllers;

import android.content.Context;

import com.mirego.rebelchat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginControllerImpl implements LoginController {

    private final String USERS_PATH = "users";

    private final String PARAMETER_USER_ID = "_id";
    private final String PARAMETER_USERNAME = "name";
    private final String PARAMETER_PASSWORD = "password";
    private final String PARAMETER_EMAIL = "email";

    private OkHttpClient client = new OkHttpClient();

    @Override
    public void login(Context context, String username, String password, final LoginCallback loginCallback) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("97.107.131.23")
                .port(3000)
                .addPathSegment(USERS_PATH)
                .addQueryParameter(PARAMETER_USERNAME, username)
                .addQueryParameter(PARAMETER_PASSWORD, md5(password))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (loginCallback != null) {
                    String userId = getUserIdFromResponseArray(response);
                    if (userId != null) {
                        loginCallback.onLoginSuccess(userId);
                    } else {
                        loginCallback.onLoginFail();
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (loginCallback != null) {
                    loginCallback.onLoginFail();
                }
            }
        });

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public void register(Context context, String username, String password, String email, final RegisterCallback registerCallback) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("97.107.131.23")
                .port(3000)
                .addPathSegment(USERS_PATH)
                .build();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(PARAMETER_USERNAME, username);
            jsonObject.put(PARAMETER_EMAIL, email);
            jsonObject.put(PARAMETER_PASSWORD, md5(password));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), jsonObject.toString()))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (registerCallback != null) {
                    String userId = getUserIdFromResponseObject(response);
                    if (userId != null) {
                        registerCallback.onRegisterSuccess(userId);
                    } else {
                        registerCallback.onRegisterFail();
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if(registerCallback != null){
                    registerCallback.onRegisterFail();
                }
            }
        });
    }

    private String getUserIdFromResponseArray(Response response) {
        try {
            JSONArray userList = new JSONArray(response.body().string());
            JSONObject user = (JSONObject) userList.get(0);
            return user.getString(PARAMETER_USER_ID);
        } catch (Exception e) {
            return null;
        }
    }

    private String getUserIdFromResponseObject(Response response) {
        try {
            JSONObject user = new JSONObject(response.body().string());
            return user.getString(PARAMETER_USER_ID);
        } catch (Exception e) {
            return null;
        }
    }
}
