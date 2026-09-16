package com.gameturbopro.app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout content;

    private DeviceMonitor deviceMonitor;
    private NetworkMonitor networkMonitor;
    private DpiManager dpiManager;
    private SettingsHelper settingsHelper;
    private GameLauncher gameLauncher;
    private GameProfileManager profileManager;

    private final int BG = Color.rgb(3, 5, 10);
    private final int WHITE = Color.rgb(245, 250, 255);
    private final int MUTED = Color.rgb(130, 149, 170);
    private final int GREEN = Color.rgb(0, 245, 160);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);

        deviceMonitor = new DeviceMonitor(this);
        networkMonitor = new NetworkMonitor(this);
        dpiManager = new DpiManager(this);
        settingsHelper = new SettingsHelper(this);
        gameLauncher = new GameLauncher(this);
        profileManager = new GameProfileManager(this);

        BottomNavigationView nav = findViewById(R.id.bottomNav);

        nav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                showHome();
                return true;
            }

            if (id == R.id.nav_boost) {
                showBoost();
                return true;
            }

            if (id == R.id.nav_dpi) {
                showDpi();
                return true;
            }

            if (id == R.id.nav_games) {
                showGames();
                return true;
            }

            if (id == R.id.nav_settings) {
                showSettings();
                return true;
            }

            return false;
        });

        nav.setSelectedItemId(R.id.nav_home);
    }

    private void clear() {
        content.removeAllViews();
    }

    private LinearLayout page() {

        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setPadding(
                16,
                18,
                16,
                30
        );

        layout.setBackgroundColor(BG);

        return layout;
    }

    private ScrollView scroll(View view) {

        ScrollView scroll = new ScrollView(this);

        scroll.setFillViewport(true);

        scroll.addView(view);

        return scroll;
    }

    private TextView text(
            String value,
            float size
    ) {

        TextView t = new TextView(this);

        t.setText(value);
        t.setTextColor(WHITE);
        t.setTextSize(size);

        t.setPadding(
                4,
                4,
                4,
                4
        );

        return t;
    }

    private TextView title(String value) {

        TextView t = text(
                value,
                25
        );

        t.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        t.setPadding(
                4,
                6,
                4,
                8
        );

        return t;
    }

    private TextView muted(String value) {

        TextView t = text(
                value,
                12
        );

        t.setTextColor(MUTED);

        return t;
    }

    private LinearLayout card() {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                14,
                12,
                14,
                12
        );

        card.setBackgroundResource(
                R.drawable.bg_card
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        params.setMargins(
                0,
                5,
                0,
                5
        );

        card.setLayoutParams(params);

        return card;
    }

    private Button button(String value) {

        Button button =
                new Button(this);

        button.setText(value);
        button.setTextColor(Color.BLACK);
        button.setTextSize(14);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);

        button.setBackgroundResource(
                R.drawable.bg_primary
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        56
                );

        params.setMargins(
                0,
                6,
                0,
                6
        );

        button.setLayoutParams(params);

        return button;
    }

    private void status(
            LinearLayout parent,
            String name,
            String value
    ) {

        LinearLayout c = card();

        c.addView(
                text(name, 14)
        );

        TextView result =
                text(value, 13);

        result.setTextColor(GREEN);

        c.addView(result);

        parent.addView(c);
    }

    private void infoRow(
            LinearLayout parent,
            String a,
            String b,
            String c
    ) {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        infoBox(row, a);
        infoBox(row, b);
        infoBox(row, c);

        parent.addView(row);
    }

    private void infoBox(
            LinearLayout row,
            String value
    ) {

        LinearLayout box = card();

        box.setGravity(
                Gravity.CENTER
        );

        TextView t =
                text(value, 12);

        t.setGravity(
                Gravity.CENTER
        );

        box.addView(t);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        params.setMargins(
                3,
                3,
                3,
                3
        );

        box.setLayoutParams(params);

        row.addView(box);
    }

    private void showHome() {

        clear();

        LinearLayout p = page();

        p.addView(
                title("⚡ GAME TURBO PRO")
        );

        p.addView(
                muted(
                        "ULTIMATE GAMING CONTROL CENTER"
                )
        );

        LinearLayout device = card();

        TextView model =
                text(
                        "📱 " +
                                deviceMonitor.getModel(),
                        18
                );

        model.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        device.addView(model);

        device.addView(
                muted(
                        deviceMonitor.getAndroidVersion()
                )
        );

        p.addView(device);

        infoRow(
                p,
                "DPI\n" +
                        deviceMonitor.getDpi(),

                "RESOLUTION\n" +
                        deviceMonitor.getResolution(),

                "REFRESH\n" +
                        Math.round(
                                deviceMonitor.getRefreshRate()
                        ) +
                        " Hz"
        );

        int battery =
                deviceMonitor.getBatteryPercent();

        String batteryText =
                battery >= 0
                        ? battery + "%"
                        : "N/A";

        float temp =
                deviceMonitor.getBatteryTemperature();

        String tempText =
                temp >= 0
                        ? temp + "°C"
                        : "N/A";

        infoRow(
                p,
                "🔋 BATTERY\n" +
                        batteryText,

                "🌡 TEMP\n" +
                        tempText,

                "💾 RAM\n" +
                        deviceMonitor.getRamInfo()
        );

        infoRow(
                p,
                "📶 NETWORK\n" +
                        networkMonitor.getNetworkType(),

                "PING\n" +
                        getPing(),

                "MODE\nACTIVE"
        );

        LinearLayout mode = card();

        TextView modeTitle =
                text(
                        "🎮 GAMING MODE",
                        17
                );

        modeTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        mode.addView(modeTitle);

        TextView active =
                text(
                        "● ACTIVE",
                        14
                );

        active.setTextColor(GREEN);

        mode.addView(active);

        p.addView(mode);

        Button boost =
                button(
                        "⚡ BOOST NOW ⚡"
                );

        boost.setTextSize(19);

        boost.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        boost.setOnClickListener(
                v -> showBoost()
        );

        p.addView(boost);

        p.addView(
                muted(
                        "PLAY SMARTER • NOT HARDER"
                )
        );

        content.addView(
                scroll(p)
        );
    }

    private String getPing() {

        int ping =
                networkMonitor.getPing();

        if (ping < 0) {
            return "N/A";
        }

        return ping + " ms";
    }

    private void showBoost() {

        clear();

        LinearLayout p = page();

        p.addView(
                title("⚡ BOOST MODE")
        );

        LinearLayout hero = card();

        hero.setGravity(
                Gravity.CENTER
        );

        TextView icon =
                text("🎮", 50);

        icon.setGravity(
                Gravity.CENTER
        );

        hero.addView(icon);

        TextView heading =
                text(
                        "BOOST MODE",
                        24
                );

        heading.setGravity(
                Gravity.CENTER
        );

        heading.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        hero.addView(heading);

        hero.addView(
                muted(
                        "Official Android gaming controls"
                )
        );

        p.addView(hero);

        status(
                p,
                "🎮 Game Mode",
                "Android settings available"
        );

        status(
                p,
                "🔒 Screen Awake",
                "Supported by app controls"
        );

        status(
                p,
                "⭕ Immersive Mode",
                "Supported where available"
        );

        status(
                p,
                "🔕 Do Not Disturb",
                "Android settings"
        );

        Button display =
                button(
                        "🖥 DISPLAY SETTINGS"
                );

        display.setOnClickListener(
                v -> settingsHelper.openDisplay()
        );

        p.addView(display);

        Button battery =
                button(
                        "🔋 BATTERY SETTINGS"
                );

        battery.setOnClickListener(
                v -> settingsHelper.openBattery()
        );

        p.addView(battery);

        Button game =
                button(
                        "🎮 ANDROID GAME MODE"
                );

        game.setOnClickListener(
                v -> settingsHelper.openGameMode()
        );

        p.addView(game);

        Button dnd =
                button(
                        "🔕 DO NOT DISTURB"
                );

        dnd.setOnClickListener(
                v -> settingsHelper.openDnd()
        );

        p.addView(dnd);

        Button developer =
                button(
                        "🛠 DEVELOPER OPTIONS"
                );

        developer.setOnClickListener(
                v -> settingsHelper.openDeveloper()
        );

        p.addView(developer);

        p.addView(
                muted(
                        "No fake FPS, RAM or ping claims."
                )
        );

        content.addView(
                scroll(p)
        );
    }

    private void showDpi() {

        clear();

        LinearLayout p = page();

        p.addView(
                title("◉ DPI MANAGER")
        );

        LinearLayout current = card();

        current.setGravity(
                Gravity.CENTER
        );

        TextView dpi =
                text(
                        String.valueOf(
                                deviceMonitor.getDpi()
                        ),
                        40
                );

        dpi.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        dpi.setGravity(
                Gravity.CENTER
        );

        current.addView(dpi);

        TextView label =
                muted(
                        "CURRENT DPI / DENSITY"
                );

        label.setGravity(
                Gravity.CENTER
        );

        current.addView(label);

        p.addView(current);

        p.addView(
                muted("DPI PRESETS")
        );

        int[] presets = {
                360,
                420,
                480,
                540,
                600
        };

        for (int value : presets) {

            Button b =
                    button(
                            "DPI " + value
                    );

            final int selected = value;

            b.setOnClickListener(
                    v -> showDpiDialog(selected)
            );

            p.addView(b);
        }

        Button developer =
                button(
                        "⚙ OPEN DEVELOPER OPTIONS"
                );

        developer.setOnClickListener(
                v -> dpiManager.openDeveloperOptions()
        );

        p.addView(developer);

        LinearLayout note = card();

        note.addView(
                text(
                        "ⓘ IMPORTANT",
                        15
                )
        );

        note.addView(
                muted(
                        "A normal Android app cannot silently "
                                + "change system DPI on standard "
                                + "devices. Official Android "
                                + "settings are used instead."
                )
        );

        p.addView(note);

        content.addView(
                scroll(p)
        );
    }

    private void showDpiDialog(
            int dpi
    ) {

        new AlertDialog.Builder(this)
                .setTitle(
                        "DPI " + dpi
                )
                .setMessage(
                        "Android may require Developer "
                                + "Options, ADB or OEM-specific "
                                + "controls to change system density."
                )
                .setPositiveButton(
                        "OPEN SETTINGS",
                        (dialog, which) ->
                                dpiManager
                                        .openDeveloperOptions()
                )
                .setNegativeButton(
                        "CANCEL",
                        null
                )
                .show();
    }

    private void showGames() {

        clear();

        LinearLayout p = page();

        p.addView(
                title("🎮 MY GAMES")
        );

        p.addView(
                muted(
                        "INSTALLED LAUNCHABLE APPS"
                )
        );

        List<android.content.pm.ResolveInfo> apps =
                gameLauncher.getLaunchableApps();

        if (apps == null || apps.isEmpty()) {

            LinearLayout empty = card();

            empty.addView(
                    text(
                            "No launchable apps found.",
                            16
                    )
            );

            p.addView(empty);

        } else {

            for (
                    android.content.pm.ResolveInfo info :
                    apps
            ) {

                String name =
                        info.loadLabel(
                                getPackageManager()
                        ).toString();

                String packageName =
                        info.activityInfo.packageName;

                addGame(
                        p,
                        name,
                        packageName
                );
            }
        }

        p.addView(
                muted(
                        "Long press a game for its profile."
                )
        );

        content.addView(
                scroll(p)
        );
    }

    private void addGame(
            LinearLayout parent,
            String name,
            String packageName
    ) {

        LinearLayout c = card();

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setGravity(
                Gravity.CENTER_VERTICAL
        );

        TextView gameName =
                text(
                        "🎮 " + name,
                        16
                );

        gameName.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        row.addView(
                gameName,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        Button play =
                button("PLAY");

        LinearLayout.LayoutParams playParams =
                new LinearLayout.LayoutParams(
                        95,
                        52
                );

        play.setLayoutParams(playParams);

        play.setOnClickListener(
                v -> gameLauncher.launch(
                        packageName
                )
        );

        row.addView(play);

        c.addView(row);

        c.setOnLongClickListener(
                v -> {

                    showProfile(
                            packageName,
                            name
                    );

                    return true;
                }
        );

        parent.addView(c);
    }

    private void showProfile(
            String packageName,
            String gameName
    ) {

        GameProfile profile =
                profileManager.getProfile(
                        packageName
                );

        clear();

        LinearLayout p = page();

        p.addView(
                title(
                        "🎮 " +
                                gameName +
                                "\nGAME PROFILE"
                )
        );

        String dpi;

        if (profile.getDpi() == 0) {

            dpi =
                    deviceMonitor.getDpi()
                            + " Default";

        } else {

            dpi =
                    String.valueOf(
                            profile.getDpi()
                    );
        }

        status(
                p,
                "DPI",
                    
