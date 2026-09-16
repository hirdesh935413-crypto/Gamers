package com.gameturbopro.app;

import android.content.Context;
import android.content.SharedPreferences;

public class GameProfileManager {

    private final SharedPreferences preferences;

    public GameProfileManager(Context context) {
        preferences = context.getSharedPreferences(
                "game_profiles",
                Context.MODE_PRIVATE
        );
    }

    public void saveProfile(GameProfile profile) {

        String key = profile.getPackageName();

        preferences.edit()

                .putInt(key + "_dpi", profile.getDpi())
                .putInt(key + "_brightness", profile.getBrightness())
                .putInt(key + "_timeout", profile.getTimeout())
                .putFloat(key + "_refresh", profile.getRefreshRate())

                .putString(key + "_sensitivity", profile.getSensitivity())
                .putString(key + "_graphics", profile.getGraphics())

                .putBoolean(key + "_gaming", profile.isGamingMode())
                .putBoolean(key + "_dnd", profile.isDnd())

                .apply();
    }

    public GameProfile getProfile(String packageName) {

        GameProfile profile = new GameProfile(packageName);

        profile.setDpi(
                preferences.getInt(packageName + "_dpi", 0)
        );

        profile.setBrightness(
                preferences.getInt(packageName + "_brightness", 70)
        );

        profile.setTimeout(
                preferences.getInt(packageName + "_timeout", 10)
        );

        profile.setRefreshRate(
                preferences.getFloat(packageName + "_refresh", 120f)
        );

        profile.setSensitivity(
                preferences.getString(
                        packageName + "_sensitivity",
                        "High"
                )
        );

        profile.setGraphics(
                preferences.getString(
                        packageName + "_graphics",
                        "Smooth + Extreme"
                )
        );

        profile.setGamingMode(
                preferences.getBoolean(
                        packageName + "_gaming",
                        true
                )
        );

        profile.setDnd(
                preferences.getBoolean(
                        packageName + "_dnd",
                        false
                )
        );

        return profile;
    }
}
