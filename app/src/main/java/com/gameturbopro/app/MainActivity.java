package com.gameturbopro.app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

    private static final int BG = Color.rgb(3, 5, 10);
    private static final int WHITE = Color.rgb(245, 250, 255);
    private static final int MUTED = Color.rgb(125, 145, 165);
    private static final int GREEN = Color.rgb(0, 245, 160);
    private static final int CYAN = Color.rgb(0, 200, 255);
    private static final int RED = Color.rgb(255, 69, 103);

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

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {

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

        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    private void clearContent() {
        content.removeAllViews();
    }

    private LinearLayout createPage() {

        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);

        layout.setPadding(
                16,
                18,
                16,
                28
        );

        layout.setBackgroundColor(BG);

        return layout;
    }

    private ScrollView makeScroll(View view) {

        ScrollView scrollView = new ScrollView(this);

        scrollView.setFillViewport(true);

        scrollView.addView(view);

        return scrollView;
    }

    private TextView makeText(
            String value,
            float size
    ) {

        TextView textView = new TextView(this);

        textView.setText(value);
        textView.setTextColor(WHITE);
        textView.setTextSize(size);

        textView.setPadding(
                4,
                4,
                4,
                4
        );

        return textView;
    }

    private TextView makeTitle(String value) {

        TextView textView = makeText(
                value,
                25
        );

        textView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        textView.setPadding(
                4,
                6,
                4,
                8
        );

        return textView;
    }

    private TextView makeMuted(String value) {

        TextView textView = makeText(
                value,
                12
        );

        textView.setTextColor(MUTED);

        return textView;
    }

    private LinearLayout makeCard() {

        LinearLayout card = new LinearLayout(this);

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
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
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

    private Button makeButton(String label) {

        Button button = new Button(this);

        button.setText(label);
        button.setTextColor(Color.BLACK);
        button.setTextSize(14);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);

        button.setBackgroundResource(
                R.drawable.bg_primary
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
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

    private void addStatus(
            LinearLayout parent,
            String title,
            String value
    ) {

        LinearLayout card = makeCard();

        TextView name = makeText(
                title,
                14
        );

        TextView result = makeText(
                value,
                13
        );

        result.setTextColor(GREEN);

        card.addView(name);
        card.addView(result);

        parent.addView(card);
    }

    private void addInfoRow(
            LinearLayout parent,
            String first,
            String second,
            String third
    ) {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        addInfoBox(row, first);
        addInfoBox(row, second);
        addInfoBox(row, third);

        parent.addView(row);
    }

    private void addInfoBox(
            LinearLayout row,
            String value
    ) {

        LinearLayout box = makeCard();

        box.setGravity(
                Gravity.CENTER
        );

        TextView textView =
                makeText(
                        value,
                        12
                );

        textView.setGravity(
                Gravity.CENTER
        );

        box.addView(textView);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
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

        clearContent();

        LinearLayout page =
                createPage();

        page.addView(
                makeTitle(
                        "⚡ GAME TURBO PRO"
                )
        );

        page.addView(
                makeMuted(
                        "ULTIMATE GAMING CONTROL CENTER"
                )
        );

        LinearLayout device =
                makeCard();

        TextView model =
                makeText(
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
                makeMuted(
                        deviceMonitor.getAndroidVersion()
                )
        );

        page.addView(device);

        addInfoRow(
                page,
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
                battery < 0
                        ? "N/A"
                        : battery + "%";

        float temperature =
                deviceMonitor.getBatteryTemperature();

        String temperatureText =
                temperature < 0
                        ? "N/A"
                        : temperature + "°C";

        addInfoRow(
                page,
                "🔋 BATTERY\n" +
                        batteryText,

                "🌡 TEMP\n" +
                        temperatureText,

                "💾 RAM\n" +
                        deviceMonitor.getRamInfo()
        );

        addInfoRow(
                page,
                "📶 NETWORK\n" +
                        networkMonitor.getNetworkType(),

                "PING\n" +
                        getPingText(),

                "MODE\nACTIVE"
        );

        LinearLayout mode =
                makeCard();

        TextView modeTitle =
                makeText(
                        "🎮 GAMING MODE",
                        17
                );

        modeTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        mode.addView(modeTitle);

        TextView active =
                makeText(
                        "● ACTIVE",
                        14
                );

        active.setTextColor(GREEN);

        mode.addView(active);

        page.addView(mode);

        Button boost =
                makeButton(
                        "⚡  BOOST NOW  ⚡"
                );

        boost.setTextSize(19);

        boost.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        boost.setOnClickListener(
                v -> showBoost()
        );

        page.addView(boost);

        page.addView(
                makeMuted(
                        "PLAY SMARTER  ⚡  NOT HARDER"
                )
        );

        content.addView(
                makeScroll(page)
        );
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

        clearContent();

        LinearLayout page =
                createPage();

        page.addView(
                makeTitle(
                        "⚡ BOOST MODE"
                )
        );

        LinearLayout hero =
                makeCard();

        hero.setGravity(
                Gravity.CENTER
        );

        TextView icon =
                makeText(
                        "🎮",
                        48
                );

        icon.setGravity(
                Gravity.CENTER
        );

        hero.addView(icon);

        TextView heading =
                makeText(
                        "BOOST MODE",
                        25
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
                makeMuted(
                        "Gaming optimization center"
                )
        );

        page.addView(hero);

        addStatus(
                page,
                "🎮 Gaming Mode",
                "Available through Android settings"
        );

        addStatus(
                page,
                "🔒 Keep Screen Awake",
                "Supported during app use"
        );

        addStatus(
                page,
                "⭕ Immersive Mode",
                "Supported where available"
        );

        addStatus(
                page,
                "🔕 Do Not Disturb",
                "Use Android permission/settings"
        );

        page.addView(
                makeText(
                        "QUICK SETTINGS",
                        17
                )
        );

        Button display =
                makeButton(
                        "🖥 Display Settings"
                );

        display.setOnClickListener(
                v -> settingsHelper.openDisplay()
        );

        page.addView(display);

        Button battery =
                makeButton(
                        "🔋 Battery Settings"
                );

        battery.setOnClickListener(
                v -> settingsHelper.openBattery()
        );

        page.addView(battery);

        Button gameMode =
                makeButton(
                        "🎮 Android Game Mode"
                );

        gameMode.setOnClickListener(
                v -> settingsHelper.openGameMode()
        );

        page.addView(gameMode);

        Button dnd =
                makeButton(
                        "🔕 Do Not Disturb Settings"
                );

        dnd.setOnClickListener(
                v -> settingsHelper.openDnd()
        );

        page.addView(dnd);

        Button developer =
                makeButton(
                        "🛠 Developer Options"
                );

        developer.setOnClickListener(
                v -> settingsHelper.openDeveloper()
        );

        page.addView(developer);

        page.addView(
                makeMuted(
                        "GAME TURBO PRO uses official Android APIs."
                )
        );

        content.addView(
                makeScroll(page)
        );
    }

    private void showDpi() {

        clearContent();

        LinearLayout page =
                createPage();

        page.addView(
                makeTitle(
                        "◉ DPI MANAGER"
                )
        );

        LinearLayout current =
                makeCard();

        current.setGravity(
                Gravity.CENTER
        );

        TextView dpi =
                makeText(
                        String.valueOf(
                                deviceMonitor.getDpi()
                        ),
                        40
                );

        dpi.setGravity(
                Gravity.CENTER
        );

        dpi.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        current.addView(dpi);

        TextView label =
                makeMuted(
                        "CURRENT DPI / DENSITY"
                );

        label.setGravity(
                Gravity.CENTER
        );

        current.addView(label);

        page.addView(current);

        page.addView(
                makeMuted(
                        "DPI PRESETS"
                )
        );

        int[] presets = {
                360,
                420,
                480,
                540,
                600
        };

        for (int value : presets) {

            Button button =
                    makeButton(
                            "DPI  " + value
                    );

            final int selectedDpi =
                    value;

            button.setOnClickListener(
                    v -> showDpiDialog(
                            selectedDpi
                    )
            );

            page.addView(button);
        }

        Button developer =
                makeButton(
                        "⚙ OPEN DEVELOPER OPTIONS"
                );

        developer.setOnClickListener(
                v -> dpiManager.openDeveloperOptions()
        );

        page.addView(developer);

        LinearLayout note =
                makeCard();

        TextView noteTitle =
                makeText(
                        "ⓘ IMPORTANT",
                        15
                );

        noteTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        note.addView(noteTitle);

        note.addView(
                makeMuted(
                        "A normal Android application cannot "
                                + "silently change system DPI on "
                                + "standard devices. This app "
                                + "opens official Android controls."
                )
        );

        page.addView(note);

        content.addView(
                makeScroll(page)
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
                        "To change system density, Android "
                                + "may require Developer Options, "
                                + "ADB or OEM-specific controls."
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

        clearContent();

        LinearLayout page =
                createPage();

        page.addView(
                makeTitle(
                        "🎮 MY GAMES"
                )
        );

        page.addView(
                makeMuted(
                        "INSTALLED LAUNCHABLE APPS"
                )
        );

        List<android.content.pm.ResolveInfo> apps =
                gameLauncher.getLaunchableApps();

        if (apps == null || apps.isEmpty()) {

            LinearLayout empty =
                    makeCard();

            empty.addView(
                    makeText(
                            "No launchable apps found.",
                            16
                    )
            );

            empty.addView(
                    makeMuted(
                            "Install an app with a launcher "
                                    + "activity and try again."
                    )
            );

            page.addView(empty);
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

                addGameCard(
                        page,
                        name,
                        packageName
                );
            }
        }

        page.addView(
                makeMuted(
                        "Long press a game to open its profile."
                )
        );

        content.addView(
                makeScroll(page)
        );
    }

    private void addGameCard(
            LinearLayout parent,
            String name,
            String packageName
    ) {

        LinearLayout card =
                makeCard();

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
    
