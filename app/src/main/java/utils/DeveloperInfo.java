/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;

/**
 * Created by Jayant Singh on 20/1/18.
 */

public class DeveloperInfo {

    Context context;

    public DeveloperInfo(Context context) {
        this.context = context;
    }

    public void visitDeveloperSite(Button button) {
        button.setOnClickListener(v ->
                context.startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://jayantsingh.in"))));
    }
}
