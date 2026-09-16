package com.gameturbopro.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.BatteryManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowMetrics;

public class DeviceMonitor {

    private final Context context;

    public DeviceMonitor(Context context) {
        this.context = context;
    }

    public String getModel() {

        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    public String getAndroidVersion() {

        return "Android " + Build.VERSION.RELEASE;
    }

    public int getBatteryPercent() {

        IntentFilter filter =
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        Intent battery =
                context.registerReceiver(null, filter);

        if (battery == null) {
            return -1;
        }

        int level =
                battery.getIntExtra(
                        BatteryManager.EXTRA_LEVEL,
                        -1
                );

        int scale =
                battery.getIntExtra(
                        BatteryManager.EXTRA_SCALE,
                        -1
                );

        if (level < 0 || scale <= 0) {
            return -1;
        }

        return (level * 100) / scale;
    }

    public float getBatteryTemperature() {

        IntentFilter filter =
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        Intent battery =
                context.registerReceiver(null, filter);

        if (battery == null) {
            return -1;
        }

        int temp =
                battery.getIntExtra(
                        BatteryManager.EXTRA_TEMPERATURE,
                        -1
                );

        if (temp < 0) {
            return -1;
        }

        return temp / 10f;
    }

    public String getRamInfo() {

        ActivityManager manager =
                (ActivityManager) context.getSystemService(
                        Context.ACTIVITY_SERVICE
                );

        if (manager == null) {
            return "N/A";
        }

        ActivityManager.MemoryInfo info =
                new ActivityManager.MemoryInfo();

        manager.getMemoryInfo(info);

        long total =
                info.totalMem / (1024 * 1024);

        long available =
                info.availMem / (1024 * 1024);

        long used =
                total - available;

        return formatMemory(used)
                + " / "
                + formatMemory(total);
    }

    private String formatMemory(long mb) {

        if (mb > 1024) {
            return String.format(
                    "%.1f GB",
                    mb / 1024f
            );
        }

        return mb + " MB";
    }

    public String getResolution() {

        try {

            if (Build.VERSION.SDK_INT >= 30) {

                WindowMetrics metrics =
                        ((Activity) context)
                                .getWindowManager()
                                .getMaximumWindowMetrics();

                int width =
                        metrics.getBounds().width();

                int height =
                        metrics.getBounds().height();

                return width + " × " + height;
            }

            Display display =
                    ((Activity) context)
                            .getWindowManager()
                            .getDefaultDisplay();

            DisplayMetrics metrics =
                    new DisplayMetrics();

            display.getRealMetrics(metrics);

            return metrics.widthPixels
                    + " × "
                    + metrics.heightPixels;

        } catch (Exception e) {

            return "Not Available";
        }
    }

    public float getRefreshRate() {

        try {

            Display display =
                    ((Activity) context)
                            .getWindowManager()
                            .getDefaultDisplay();

            return display.getRefreshRate();

        } catch (Exception e) {

            return 60f;
        }
    }

    public int getDpi() {

        DisplayMetrics metrics =
                context.getResources()
                        .getDisplayMetrics();

        return metrics.densityDpi;
    }
}
