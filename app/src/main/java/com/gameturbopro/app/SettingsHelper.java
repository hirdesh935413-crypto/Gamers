package com.gameturbopro.app;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class SettingsHelper {

    private final Context context;

    public SettingsHelper(Context context) {
        this.context = context;
    }

    private void open(String action) {

        try {

            context.startActivity(
                    new Intent(action)
            );

        } catch (Exception e) {

            context.startActivity(
                    new Intent(Settings.ACTION_SETTINGS)
            );
        }
    }

    public void openDisplay() {

        open(Settings.ACTION_DISPLAY_SETTINGS);
    }

    public void openBattery() {

        open(Settings.ACTION_BATTERY_SAVER_SETTINGS);
    }

    public void openDnd() {

        open(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
    }

    public void openDeveloper() {

        open(
                Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS
        );
    }

    public void openAppInfo() {

        Intent intent =
                new Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                );

        intent.setData(
                android.net.Uri.parse(
                        "package:" + context.getPackageName()
                )
        );

        context.startActivity(intent);
    }

    public void openGameMode() {

        if (android.os.Build.VERSION.SDK_INT >= 31) {

            try {

                open("android.settings.GAME_MODE_SETTINGS");

            } catch (Exception e) {

                openDisplay();
            }

        } else {

            openDisplay();
        }
    }
}
