package com.mirego.rebelchat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mirego.rebelchat.R;
import com.mirego.rebelchat.controllers.ViewMessageController;
import com.mirego.rebelchat.controllers.ViewMessagesControllerlmpl;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewMessageActivity extends BaseActivity {

    private String currentUserId;
    private static String EXTRA_USER_ID = "extra_user_id";
    private ViewMessagesControllerlmpl viewMessageController;
    private Handler messageHandler;

    @Bind(R.id.messages_list)
    TextView messages_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        currentUserId = intent.getStringExtra(EXTRA_USER_ID);
        System.out.println(currentUserId);


        viewMessageController = new ViewMessagesControllerlmpl();

        viewMessageController.viewMessages(getApplicationContext(), currentUserId, new ViewMessageController.RetrieveMessagesCallback() {
            @Override
            public void onViewMessagesSuccess(String message) {

                System.out.println(message);
            }

            @Override
            public void onViewMessagesFail() {

            }
        });
    }


}
