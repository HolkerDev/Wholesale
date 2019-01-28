package com.dev.holker.wholesale.model;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import java.util.ArrayList;

public class Functions {
    public static void sortDates(ArrayList<MessageItem> messages) {
        int n = messages.size();
        MessageItem temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (messages.get(j - 1).getDate().after(messages.get(j).getDate())) {
                    //swap elements
                    temp = messages.get(j - 1);
                    messages.set(j - 1, messages.get(j));
                    messages.set(j, temp);
                }

            }
        }
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
