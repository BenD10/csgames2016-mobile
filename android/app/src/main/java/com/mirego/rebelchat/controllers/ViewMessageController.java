package com.mirego.rebelchat.controllers;

import android.content.Context;

/**
 * Created by owner on 2016-03-12.
 */
public interface ViewMessageController {

        interface RetrieveMessagesCallback {
            void onViewMessagesSuccess(String messages);
            void onViewMessagesFail();
        }

        void viewMessages(Context context, String userId, RetrieveMessagesCallback retrieveMessagesCallback);
    }

