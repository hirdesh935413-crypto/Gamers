package com.gameturbopro.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameLauncher {

    private final Context context;

    public GameLauncher(Context context) {
        this.context = context;
    }

    public List<ResolveInfo> getLaunchableApps() {

        PackageManager pm =
                context.getPackageManager();

        Intent intent =
                new Intent(Intent.ACTION_MAIN);

        intent.addCategory(
                Intent.CATEGORY_LAUNCHER
        );

        List<ResolveInfo> apps =
                pm.queryIntentActivities(
                        intent,
                        PackageManager.MATCH_ALL
                );

        Collections.sort(
                apps,
                new Comparator<ResolveInfo>() {

                    @Override
                    public int compare(
                            ResolveInfo a,
                            ResolveInfo b) {

                        return a.loadLabel(
                                pm
                        ).toString().compareToIgnoreCase(
                                b.loadLabel(pm).toString()
                        );
                    }
                }
        );

        return apps;
    }

    public void launch(String packageName) {

        try {

            Intent intent =
                    context.getPackageManager()
                            .getLaunchIntentForPackage(
                                    packageName
                            );

            if (intent != null) {

                intent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK
                );

                context.startActivity(intent);
            }

        } catch (Exception ignored) {
        }
    }

    public String getAppName(String packageName) {

        try {

            ApplicationInfo info =
                    context.getPackageManager()
                            .getApplicationInfo(
                                    packageName,
                                    0
                            );

            return context.getPackageManager()
                    .getApplicationLabel(info)
                    .toString();

        } catch (Exception e) {

            return "Unknown Game";
        }
    }
}
