package com.gameturbopro.app;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.DisplayMetrics;

public class DpiManager {

    private final Context context;

    public DpiManager(Context context) {
        this.context = context;
    }

    public int getCurrentDpi() {

        DisplayMetrics metrics =
                context.getResources().getDisplayMetrics();

        return metrics.densityDpi;
    }

    public void openDeveloperOptions() {

        try {

            Intent intent = new Intent(
                    Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
            );

            context.startActivity(intent);

        } catch (Exception e) {

            Intent intent = new Intent(
                    Settings.ACTION_SETTINGS
            );

            context.startActivity(intent);
        }
    }
}
