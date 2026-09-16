package com.gameturbopro.app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
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

    private int WHITE = Color.rgb(245, 250, 255);
    private int MUTED = Color.rgb(125, 145, 165);
    private int CYAN = Color.rgb(0, 200, 255);
    private int GREEN = Color.rgb(0, 245, 160);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);

        BottomNavigationView navigation =
                findViewById(R.id.bottomNav);

        deviceMonitor = new DeviceMonitor(this);
        networkMonitor = new NetworkMonitor(this);
        dpiManager = new DpiManager(this);
        settingsHelper = new SettingsHelper(this);
        gameLauncher = new GameLauncher(this);
        profileManager = new GameProfileManager(this);

        showHome();

        navigation.setSelectedItemId(R.id.nav_home);

        navigation.setOnItemSelectedListener(item -> {

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
    }

    private void clearScreen() {
        content.removeAllViews();
    }

    private ScrollView makeScroll() {

        ScrollView scroll = new ScrollView(this);

        scroll.setFillViewport(true);

        return scroll;
    }

    private LinearLayout makePage() {

        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setPadding(
                16,
                18,
                16,
                24
        );

        layout.setBackgroundColor(
                Color.rgb(3, 5, 10)
        );

        return layout;
    }

    private TextView heading(String value) {

        TextView text = new TextView(this);

        text.setText(value);

        text.setTextColor(WHITE);

        text.setTextSize(23);

        text.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        text.setPadding(
                4,
                5,
                4,
                12
        );

        return text;
    }

    private TextView normalText(
            String value,
            float size
    ) {

        TextView text = new TextView(this);

        text.setText(value);

        text.setTextColor(WHITE);

        text.setTextSize(size);

        text.setPadding(
                3,
                3,
                3,
                3
        );

        return text;
    }

    private TextView smallText(String value) {

        TextView text = normalText(
                value,
                12
        );

        text.setTextColor(MUTED);

        return text;
    }

    private LinearLayout box() {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setPadding(
                14,
                13,
                14,
                13
        );

        box.setBackgroundResource(
                R.drawable.bg_card
        );

        return box;
    }

    private void addGap(
            LinearLayout parent,
            int height
    ) {

        View gap = new View(this);

        parent.addView(
                gap,
                new LinearLayout.LayoutParams(
                        1,
                        height
                )
        );
    }

    private Button actionButton(
            String label
    ) {

        Button button =
                new Button(this);

        button.setText(label);

        button.setTextSize(14);

        button.setTextColor(
                Color.BLACK
        );

        button.setAllCaps(false);

        button.setGravity(
                Gravity.CENTER
        );

        button.setBackgroundResource(
                R.drawable.bg_primary
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        54
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

    private void addInfoCard(
            LinearLayout parent,
            String label,
            String value
    ) {

        LinearLayout card = box();

        TextView labelView =
                normalText(
                        label,
                        11
                );

        labelView.setTextColor(CYAN);

        TextView valueView =
                normalText(
                        value,
                        16
                );

        valueView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        card.addView(labelView);

        card.addView(valueView);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        params.setMargins(
                4,
                4,
                4,
                4
        );

        parent.addView(card, params);
    }

    private LinearLayout infoRow() {

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

    private void showHome() {

        clearScreen();

        LinearLayout page =
                makePage();

        page.addView(
                heading("GAME TURBO PRO")
        );

        page.addView(
                smallText(
                        "ULTIMATE GAMING CONTROL CENTER"
                )
        );

        addGap(page, 10);

        LinearLayout deviceCard = box();

        TextView device =
                normalText(
                        "📱  " + deviceMonitor.getModel(),
                        18
                );

        device.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        deviceCard.addView(device);

        deviceCard.addView(
                smallText(
                        deviceMonitor.getAndroidVersion()
                )
        );

        page.addView(deviceCard);

        addGap(page, 8);

        LinearLayout row1 = infoRow();

        addInfoCard(
                row1,
                "DPI / DENSITY",
                String.valueOf(
                        deviceMonitor.getDpi()
                )
        );

        addInfoCard(
                row1,
                "RESOLUTION",
                deviceMonitor.getResolution()
        );

        addInfoCard(
                row1,
                "REFRESH RATE",
                Math.round(
                        deviceMonitor.getRefreshRate()
                ) + " Hz"
        );

        page.addView(row1);

        LinearLayout row2 = infoRow();

        addInfoCard(
                row2,
                "🔋 BATTERY",
                deviceMonitor.getBatteryPercent()
                        + "%"
        );

        addInfoCard(
                row2,
                "🌡 TEMP",
                deviceMonitor.getBatteryTemperature()
                        + "°C"
        );

        addInfoCard(
                row2,
                "💾 RAM",
                deviceMonitor.getRamInfo()
        );

        page.addView(row2);

        LinearLayout row3 = infoRow();

        addInfoCard(
                row3,
                "📶 NETWORK",
                networkMonitor.getNetworkType()
        );

        addInfoCard(
                row3,
                "PING",
                getPingText()
        );

        page.addView(row3);

        addGap(page, 8);

        LinearLayout mode = box();

        TextView modeTitle =
                normalText(
                        "🎮 GAMING MODE",
                        15
                );

        modeTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        TextView modeStatus =
                normalText(
                        "ON  ●",
                        14
                );

        modeStatus.setTextColor(GREEN);

        mode.addView(modeTitle);

        mode.addView(modeStatus);

        page.addView(mode);

        addGap(page, 12);

        Button boost =
                actionButton(
                        "⚡  BOOST NOW  ⚡"
                );

        boost.setTextSize(19);

        boost.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        ScaleAnimation animation =
                new ScaleAnimation(
                        1f,
                        1.05f,
                        1f,
                        1.05f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                );

        animation.setDuration(850);

        animation.setRepeatMode(
                Animation.REVERSE
        );

        animation.setRepeatCount(
                Animation.INFINITE
        );

        boost.startAnimation(animation);

        boost.setOnClickListener(
                view -> showBoost()
        );

        page.addView(boost);

        page.addView(
                smallText(
                        "PLAY SMARTER  ⚡  NOT HARDER"
                )
        );

        ScrollView scroll = makeScroll();

        scroll.addView(page);

        content.addView(scroll);
    }

    private String getPingText() {

        int ping =
                networkMonitor.getPing();

        if (ping < 0) {
            return "N/A";
        }

        return ping + " ms";
    }

    private void showBoost() {

        clearScreen();

        LinearLayout page =
                makePage();

        page.addView(
                heading("⚡ BOOST MODE")
        );

        LinearLayout hero = box();

        hero.setGravity(
                Gravity.CENTER
        );

        TextView icon =
                normalText(
                        "🎮",
                        55
                );

        icon.setGravity(
                Gravity.CENTER
        );

        hero.addView(icon);

        TextView boost =
                normalText(
                        "BOOST MODE",
                        26
                );

        boost.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        boost.setGravity(
                Gravity.CENTER
        );

        hero.addView(boost);

        TextView sub =
                smallText(
                        "Optimized for Gaming"
                );

        sub.setGravity(
                Gravity.CENTER
        );

        hero.addView(sub);

        page.addView(hero);

        addGap(page, 8);

        addStatus(
                page,
                "🎮 Gaming Mode",
                "ON"
        );

        addStatus(
                page,
                "🔒 Keep Screen Awake",
                "ON"
        );

        addStatus(
                page,
                "⭕ Immersive Mode",
                "Supported where available"
        );

        addStatus(
                page,
                "🔕 Do Not Disturb",
                "OFF"
        );

        page.addView(
                normalText(
                        "QUICK SHORTCUTS",
                        17
                )
        );

        Button battery =
                actionButton(
                        "🔋 Battery Settings"
                );

        battery.setOnClickListener(
                view -> settingsHelper.openBattery()
        );

        page.addView(battery);

        Button display =
                actionButton(
                        "🖥 Display Settings"
                );

        display.setOnClickListener(
                view -> settingsHelper.openDisplay()
        );

        page.addView(display);

        Button refresh =
                actionButton(
                        "⚡ Refresh Rate Settings"
                );

        refresh.setOnClickListener(
                view -> settingsHelper.openDisplay()
        );

        page.addView(refresh);

        Button developer =
                actionButton(
                        "🛠 Developer Options"
                );

        developer.setOnClickListener(
                view -> settingsHelper.openDeveloper()
        );

        page.addView(developer);

        Button gameMode =
                actionButton(
                        "🎮 Android Game Mode"
                );

        gameMode.setOnClickListener(
                view -> settingsHelper.openGameMode()
        );

        page.addView(gameMode);

        ScrollView scroll = makeScroll();

        scroll.addView(page);

        content.addView(scroll);
    }

    private void addStatus(
            LinearLayout parent,
            String title,
            String status
    ) {

        LinearLayout item = box();

        TextView left =
                normalText(
                        title,
                        15
                );

        TextView right =
                normalText(
                        status,
                        13
                );

        right.setTextColor(GREEN);

        item.addView(left);

        item.addView(right);

        parent.addView(item);

        addGap(parent, 5);
    }

    private void showDpi() {

        clearScreen();

        LinearLayout page =
                makePage();

        page.addView(
                heading("◉ DPI MANAGER")
        );

        LinearLayout current = box();

        current.setGravity(
                Gravity.CENTER
        );

        TextView dpi =
                normalText(
                        String.valueOf(
                                deviceMonitor.getDpi()
                        ),
                        42
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
                smallText(
                        "CURRENT DPI / DENSITY"
                );

        label.setGravity(
                Gravity.CENTER
        );

        current.addView(label);

        page.addView(current);

        addGap(page, 10);

        page.addView(
                smallText(
                        "DPI PRESETS"
                )
        );

        int[] dpiValues = {
                360,
                420,
                480,
                540,
                600
        };

        for (int value : dpiValues) {

            Button preset =
                    actionButton(
                            "DPI  " + value
                    );

            final int selectedDpi = value;

            preset.setOnClickListener(
                    view -> dpiDialog(selectedDpi)
            );

            page.addView(preset);
        }

        Button developer =
                actionButton(
                        "⚙ OPEN DEVELOPER OPTIONS"
                );

        developer.setOnClickListener(
                view -> dpiManager.openDeveloperOptions()
        );

        page.addView(developer);

        LinearLayout note = box();

        note.addView(
                normalText(
                        "ⓘ DPI NOTE",
                        15
                )
        );

        note.addView(
                smallText(
                        "Standard Android apps cannot silently "
                                + "change system density on normal devices. "
                                + "Official Android settings are used."
                )
        );

        page.addView(note);

        ScrollView scroll = makeScroll();

        scroll.addView(page);

        content.addView(scroll);
    }

    private void dpiDialog(int dpi) {

        new AlertDialog.Builder(this)
                .setTitle(
                        "DPI " + dpi
                )
                .setMessage(
                        "Android may require Developer Options, "
                                + "ADB or OEM-specific permission to "
                                + "actually change system DPI."
                )
                .setPositiveButton(
                        "OPEN SETTINGS",
                        (dialog, which) ->
                                dpiManager.openDeveloperOptions()
                )
                .setNegativeButton(
                        "CANCEL",
                        null
                )
                .show();
    }

    private void showGames() {

        clearScreen();

        LinearLayout page =
                makePage();

        page.addView(
                heading("🎮 MY GAMES")
        );

        page.addView(
                smallText(
                        "DIRECT GAME LAUNCHER"
                )
        );

        addGap(page, 8);

        java.util.List<
                android.content.pm.ResolveInfo
                > apps =
                gameLauncher.getLaunchableApps();

        if (apps.isEmpty()) {

            LinearLayout empty = box();

            empty.addView(
                    normalText(
                            "No launchable apps found.",
                            16
                    )
            );

            empty.addView(
                    smallText(
                            "Installed apps with launcher activities "
                                    + "will appear here."
                    )
            );

            page.addView(empty);
        }

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

            addGameCard(
                    page,
                    name,
                    packageName
            );
        }

        LinearLayout add = box();

        TextView addTitle =
                normalText(
                        "＋  ADD / MANAGE GAMES",
                        16
                );

        addTitle.setTextColor(CYAN);

        add.addView(addTitle);

        add.addView(
                smallText
