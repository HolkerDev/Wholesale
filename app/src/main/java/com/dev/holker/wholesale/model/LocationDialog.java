package com.dev.holker.wholesale.model;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import com.dev.holker.wholesale.R;

public class LocationDialog extends Dialog {

    Activity activityLocation;
    Dialog dialog;

    public LocationDialog(Activity activity) {
        super(activity);
        this.activityLocation = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_location);

    }
}
