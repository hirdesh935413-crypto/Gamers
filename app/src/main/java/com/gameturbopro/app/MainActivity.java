package com.gameturbopro.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout content;

    private DeviceMonitor deviceMonitor;
    private NetworkMonitor networkMonitor;
    private DpiManager dpiManager;
    private SettingsHelper settingsHelper;
    private GameLauncher gameLauncher;
    private GameProfileManager profileManager;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);

        BottomNavigationView bottomNav =
                findViewById(R.id.bottomNav);

        deviceMonitor =
                new DeviceMonitor(this);

        networkMonitor =
                new NetworkMonitor(this);

        dpiManager =
                new DpiManager(this);

        settingsHelper =
                new SettingsHelper(this);

        gameLauncher =
                new GameLauncher(this);

        profileManager =
                new GameProfileManager(this);

        showHome();

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {

                showHome();

            } else if (id == R.id.nav_boost) {

                showBoost();

            } else if (id == R.id.nav_dpi) {

                showDpi();

            } else if (id == R.id.nav_games) {

                showGames();

            } else if (id == R.id.nav_settings) {

                showSettings();
            }

            return true;
        });
    }

    private void clear() {

        content.removeAllViews();
    }

    private ScrollView scroll() {

        ScrollView scroll =
                new ScrollView(this);

        scroll.setFillViewport(true);

        return scroll;
    }

    private LinearLayout page() {

        LinearLayout root =
                new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setPadding(
                18,
                18,
                18,
                20
        );

        return root;
    }

    private TextView title(String text) {

        TextView tv =
                new TextView(this);

        tv.setText(text);

        tv.setTextColor(
                Color.WHITE
        );

        tv.setTextSize(22);

        tv.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        tv.setPadding(
                4,
                4,
                4,
                18
        );

        return tv;
    }

    private TextView text(
            String value,
            int size
    ) {

        TextView tv =
                new TextView(this);

        tv.setText(value);

        tv.setTextColor(
                Color.WHITE
        );

        tv.setTextSize(size);

        tv.setPadding(
                4,
                3,
                4,
                3
        );

        return tv;
    }

    private TextView muted(
            String value
    ) {

        TextView tv =
                text(value, 12);

        tv.setTextColor(
                Color.rgb(130,149,170)
        );

        return tv;
    }

    private TextView card(
            String heading,
            String value
    ) {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setPadding(
                14,
                12,
                14,
                12
        );

        box.setBackgroundResource(
                R.drawable.bg_card
        );

        TextView h =
                text(heading, 11);

        h.setTextColor(
                Color.rgb(0,200,255)
        );

        TextView v =
                text(value, 17);

        v.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        box.addView(h);

        box.addView(v);

        return cardWrapper(box);
    }

    private View cardWrapper(
            View view
    ) {

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        lp.setMargins(
                5,
                5,
                5,
                5
        );

        view.setLayoutParams(lp);

        return view;
    }

    private LinearLayout row() {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setGravity(
                Gravity.CENTER
        );

        return row;
    }

    private Button button(
            String label
    ) {

        Button button =
                new Button(this);

        button.setText(label);

        button.setTextColor(
                Color.BLACK
        );

        button.setTextSize(14);

        button.setAllCaps(false);

        button.setBackgroundResource(
                R.drawable.bg_primary
        );

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        55
                );

        lp.setMargins(
                5,
                7,
                5,
                7
        );

        button.setLayoutParams(lp);

        return button;
    }

    private void showHome() {

        clear();

        LinearLayout root = page();

        root.addView(
                title("⚡ GAME TURBO PRO")
        );

        root.addView(
                muted("ULTIMATE GAMING CONTROL CENTER")
        );

        LinearLayout device =
                new LinearLayout(this);

        device.setOrientation(
                LinearLayout.VERTICAL
        );

        device.setPadding(
                16,
                16,
                16,
                16
        );

        device.setBackgroundResource(
                R.drawable.bg_card
        );

        device.addView(
                text(
                        "📱 " + deviceMonitor.getModel(),
                        18
                )
        );

        device.addView(
                muted(
                        deviceMonitor.getAndroidVersion()
                )
        );

        root.addView(device);

        LinearLayout r1 = row();

        r1.addView(
                card(
                        "DPI / DENSITY",
                        String.valueOf(
                                deviceMonitor.getDpi()
                        )
                )
        );

        r1.addView(
                card(
                        "RESOLUTION",
                        deviceMonitor.getResolution()
                )
        );

        r1.addView(
                card(
                        "REFRESH RATE",
                        Math.round(
                                deviceMonitor.getRefreshRate()
                        ) + " Hz"
                )
        );

        root.addView(r1);

        LinearLayout r2 = row();

        r2.addView(
                card(
                        "🔋 BATTERY",
                        deviceMonitor.getBatteryPercent()
                                + "%"
                )
        );

        r2.addView(
                card(
                        "🌡 TEMP",
                        deviceMonitor.getBatteryTemperature()
                                + "°C"
                )
        );

        r2.addView(
                card(
                        "💾 RAM",
                        deviceMonitor.getRamInfo()
                )
        );

        root.addView(r2);

        LinearLayout r3 = row();

        r3.addView(
                card(
                        "📶 NETWORK",
                        networkMonitor.getNetworkType()
                )
        );

        r3.addView(
                card(
                        "PING",
                        pingText()
                )
        );

        root.addView(r3);

        LinearLayout gaming =
                new LinearLayout(this);

        gaming.setPadding(
                15,
                15,
                15,
                15
        );

        gaming.setGravity(
                Gravity.CENTER_VERTICAL
        );

        gaming.setBackgroundResource(
                R.drawable.bg_card
        );

        TextView game =
                text(
                        "🎮 GAMING MODE\nON",
                        15
                );

        game.setTextColor(
                Color.rgb(0,245,160)
        );

        gaming.addView(game);

        root.addView(gaming);

        Button boost =
                button("⚡  BOOST NOW  ⚡");

        boost.setTextSize(19);

        boost.setTextColor(Color.BLACK);

        Animation pulse =
                new AlphaAnimation(
                        0.75f,
                        1.0f
                );

        pulse.setDuration(800);

        pulse.setRepeatMode(
                Animation.REVERSE
        );

        pulse.setRepeatCount(
                Animation.INFINITE
        );

        boost.startAnimation(pulse);

        boost.setOnClickListener(
                v -> showBoost()
        );

        root.addView(boost);

        root.addView(
                muted(
                        "PLAY SMARTER  ⚡  NOT HARDER"
                )
        );

        ScrollView scroll = scroll();

        scroll.addView(root);

        content.addView(scroll);
    }

    private String pingText() {

        int ping =
                networkMonitor.getPing();

        if (ping < 0) {
            return "N/A";
        }

        return ping + " ms";
    }

    private void showBoost() {

        clear();

        LinearLayout root = page();

        root.addView(
                title("⚡ BOOST MODE")
        );

        LinearLayout hero =
                new LinearLayout(this);

        hero.setOrientation(
                LinearLayout.VERTICAL
        );

        hero.setGravity(
                Gravity.CENTER
        );

        hero.setPadding(
                20,
                30,
                20,
                30
        );

        hero.setBackgroundResource(
                R.drawable.bg_card
        );

        TextView icon =
                text("🎮", 55);

        icon.setGravity(
                Gravity.CENTER
        );

        hero.addView(icon);

        TextView boostTitle =
                text(
                        "BOOST MODE",
                        27
                );

        boostTitle.setGravity(
                Gravity.CENTER
        );

        boostTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        hero.addView(boostTitle);

        hero.addView(
                muted(
                        "Optimized for Gaming"
                )
        );

        root.addView(hero);

        root.addView(
                statusCard(
                        "🎮 Gaming Mode",
                        "ON"
                )
        );

        root.addView(
                statusCard(
                        "🔒 Keep Screen Awake",
                        "ON"
                )
        );

        root.addView(
                statusCard(
                        "⭕ Immersive Mode",
                        "Supported where available"
                )
        );

        root.addView(
                statusCard(
                        "🔕 Do Not Disturb",
                        "OFF"
                )
        );

        root.addView(
                text(
                        "QUICK SHORTCUTS",
                        17
                )
        );

        Button battery =
                button("🔋 Battery Settings");

        battery.setOnClickListener(
                v -> settingsHelper.openBattery()
        );

        root.addView(battery);

        Button display =
                button("🖥 Display Settings");

        display.setOnClickListener(
                v -> settingsHelper.openDisplay()
        );

        root.addView(display);

        Button refresh =
                button("⚡ Refresh Rate Settings");

        refresh.setOnClickListener(
                v -> settingsHelper.openDisplay()
        );

        root.addView(refresh);

        Button gameMode =
                button("🎮 Android Game Mode");

        gameMode.setOnClickListener(
                v -> settingsHelper.openGameMode()
        );

        root.addView(gameMode);

        ScrollView scroll = scroll();

        scroll.addView(root);

        content.addView(scroll);
    }

    private View statusCard(
            String title,
            String value
    ) {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.HORIZONTAL
        );

        box.setGravity(
                Gravity.CENTER_VERTICAL
        );

        box.setPadding(
                16,
                14,
                16,
                14
        );

        box.setBackgroundResource(
                R.drawable.bg_card
        );

        TextView left =
                text(title, 15);

        TextView right =
                text(value, 14);

        right.setTextColor(
                Color.rgb(0,245,160)
        );

        box.addView(
                left,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        box.addView(right);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        lp.setMargins(
                0,
                5,
                0,
                5
        );

        box.setLayoutParams(lp);

        return box;
    }

    private void showDpi() {

        clear();

        LinearLayout root = page();

        root.addView(
                title("◉ DPI MANAGER")
        );

        TextView current =
                text(
                        "CURRENT DPI\n\n"
                                + deviceMonitor.getDpi(),
                        30
                );

        current.setGravity(
                Gravity.CENTER
        );

        current.setTextColor(
                Color.WHITE
        );

        current.setPadding(
                20,
                35,
                20,
                35
        );

        current.setBackgroundResource(
                R.drawable.bg_card
        );

        root.addView(current);

        root.addView(
                muted(
                        "Choose a DPI preset"
                )
        );

        int[] presets = {
                360,
                420,
                480,
                540,
                600
        };

        for (int dpi : presets) {

            Button b =
                    button(
                            dpi + " DPI"
                    );

            final int selected = dpi;

            b.setOnClickListener(
                    v -> showDpiDialog(selected)
            );

            root.addView(b);
        }

        Button developer =
                button(
                        "⚙ Open Developer Options"
                );

        developer.setOnClickListener(
                v -> dpiManager.openDeveloperOptions()
        );

        root.addView(developer);

        root.addView(
                muted(
                        "Note: Normal Android apps cannot silently "
                                + "change system density on standard devices. "
                                + "This app uses official Android controls."
                )
        );

        ScrollView scroll = scroll();

        scroll.addView(root);

        content.addView(scroll);
    }

    private void showDpiDialog(
            int dpi
    ) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle(
                "Apply DPI " + dpi
        );

        builder.setMessage(
                "Android may require Developer Options, "
                        + "ADB or OEM-specific permissions to "
                        + "actually change system density."
        );

        builder.setPositiveButton(
                "OPEN SETTINGS",
                (dialog, which) ->
                        dpiManager.openDeveloperOptions()
        );

        builder.setNegativeButton(
                "CANCEL",
                null
        );

        builder.show();
    }

    private void showGames() {

        clear();

        LinearLayout root = page();

        root.addView(
                title("🎮 MY GAMES")
        );

        root.addView(
                muted(
                        "Installed launchable applications"
                )
        );

        for (
                android.content.pm.ResolveInfo info :
                gameLauncher.getLaunchableApps()
        ) {

            String name =
                    info.loadLabel(
                            getPackageManager()
                    ).toString();

            String pkg =
                    info.activityInfo.packageName;

            LinearLayout game =
                    new LinearLayout(this);

            game.setOrientation(
                    LinearLayout.HORIZONTAL
            );

            game.setGravity(
                    Gravity.CENTER_VERTICAL
            );

            game.setPadding(
                    14,
                    12,
                    14,
                    12
            );

            game.setBackgroundResource(
                    R.drawable.bg_card
            );

            TextView nameView =
                    text(
                            "🎮 " + name,
                            16
                    );

            game.addView(
                    nameView,
                    new LinearLayout.LayoutParams(
                            0,
                            -2,
                            1
                    )
            );

            Button play =
                    button("PLAY");

            play.setTextSize(11);

            play.setOnClickListener(
                    v -> gameLauncher.launch(pkg)
            );

            game.addView(play);

            game.setOnLongClickListener(
                    v -> {

                        showProfile(pkg, name);

                        return true;
                    }
            );

            LinearLayout.LayoutParams lp =
                    new LinearLayout.LayoutParams(
                            -1,
                            -2
                    );

            lp.setMargins(
                    0,
                    5,
                    0,
                    5
            );

            game.setLayoutParams(lp);

            root.addView(game);
        }

        Button profile =
                button(
                        "＋ ADD / MANAGE GAME PROFILE"
                );

        profile.setOnClickListener(
          
