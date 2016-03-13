package com.mirego.rebelchat.controllers;

import android.content.Context;

import com.mirego.rebelchat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by owner on 2016-03-12.
 */
public class ViewMessagesControllermpl implements ViewMessageController {

    private final String MESSAGES = "messages";
    private final String USERS_PATH = "messages";

    private final String PARAMETER_USER_ID = "userId";
    private final String PARAMETER_RECIPIENT = "recipient";

    private OkHttpClient client = new OkHttpClient();

    @Override
    public void viewMessages(Context context, String userId, final RetrieveMessagesCallback retrieveMessagesCallback) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("97.107.131.23")
                .port(3000)
                .addPathSegment(USERS_PATH)
                .addQueryParameter(PARAMETER_RECIPIENT, "erin")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (retrieveMessagesCallback != null) {
                    String messages = getMessagesFromResponseArray(response);
                    if (messages != null) {
                        retrieveMessagesCallback.onViewMessagesSuccess(messages);
                    } else {
                        retrieveMessagesCallback.onViewMessagesFail();
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (retrieveMessagesCallback != null) {
                    retrieveMessagesCallback.onViewMessagesFail();
                }
            }
        });
    }

    private String getMessagesFromResponseArray(Response response) {
        try {
            JSONArray userList = new JSONArray(response.body().string());
            JSONObject user = (JSONObject) userList.get(0);
            return user.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
