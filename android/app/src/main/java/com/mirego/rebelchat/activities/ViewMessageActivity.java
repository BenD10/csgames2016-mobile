package com.mirego.rebelchat.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mirego.rebelchat.R;
import com.mirego.rebelchat.controllers.ViewMessageController;
import com.mirego.rebelchat.controllers.ViewMessagesControllermpl;

public class ViewMessageActivity extends AppCompatActivity {

    private String currentUserId;
    private static String EXTRA_USER_ID = "extra_user_id";
    private ViewMessagesControllermpl viewMessageController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        Intent intent = getIntent();

        currentUserId = intent.getStringExtra(EXTRA_USER_ID);
        System.out.println(currentUserId);

        viewMessageController = new ViewMessagesControllermpl();

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
