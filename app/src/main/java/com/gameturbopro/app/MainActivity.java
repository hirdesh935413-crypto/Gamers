package com.gameturbopro.app;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout content;

    private DeviceMonitor device;
    private NetworkMonitor network;
    private DpiManager dpi;
    private SettingsHelper settings;
    private GameLauncher games;
    private GameProfileManager profiles;

    private final int BG = Color.rgb(3, 5, 10);
    private final int WHITE = Color.rgb(245, 250, 255);
    private final int MUTED = Color.rgb(130, 145, 165);
    private final int GREEN = Color.rgb(0, 245, 160);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);

        device = new DeviceMonitor(this);
        network = new NetworkMonitor(this);
        dpi = new DpiManager(this);
        settings = new SettingsHelper(this);
        games = new GameLauncher(this);
        profiles = new GameProfileManager(this);

        BottomNavigationView nav = findViewById(R.id.bottomNav);

        nav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                home();
                return true;
            }

            if (id == R.id.nav_boost) {
                boost();
                return true;
            }

            if (id == R.id.nav_dpi) {
                dpiPage();
                return true;
            }

            if (id == R.id.nav_games) {
                gamesPage();
                return true;
            }

            if (id == R.id.nav_settings) {
                settingsPage();
                return true;
            }

            return false;
        });

        nav.setSelectedItemId(R.id.nav_home);
    }

    private LinearLayout page() {
        LinearLayout p = new LinearLayout(this);
        p.setOrientation(LinearLayout.VERTICAL);
        p.setPadding(16, 18, 16, 28);
        p.setBackgroundColor(BG);
        return p;
    }

    private void show(LinearLayout p) {
        content.removeAllViews();

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(p);

        content.addView(scroll);
    }

    private TextView txt(String s, float size) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextColor(WHITE);
        t.setTextSize(size);
        t.setPadding(4, 4, 4, 4);
        return t;
    }

    private TextView title(String s) {
        TextView t = txt(s, 25);
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setPadding(4, 6, 4, 10);
        return t;
    }

    private TextView muted(String s) {
        TextView t = txt(s, 12);
        t.setTextColor(MUTED);
        return t;
    }

    private LinearLayout card() {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(14, 12, 14, 12);
        c.setBackgroundResource(R.drawable.bg_card);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(-1, -2);

        lp.setMargins(0, 5, 0, 5);
        c.setLayoutParams(lp);

        return c;
    }

    private Button btn(String s) {
        Button b = new Button(this);
        b.setText(s);
        b.setTextColor(Color.BLACK);
        b.setTextSize(14);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setBackgroundResource(R.drawable.bg_primary);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(-1, 56);

        lp.setMargins(0, 6, 0, 6);
        b.setLayoutParams(lp);

        return b;
    }

    private void info(
            LinearLayout p,
            String name,
            String value
    ) {
        LinearLayout c = card();

        c.addView(txt(name, 14));

        TextView v = txt(value, 14);
        v.setTextColor(GREEN);

        c.addView(v);
        p.addView(c);
    }

    private void home() {

        LinearLayout p = page();

        p.addView(title("⚡ GAME TURBO PRO"));
        p.addView(muted("ULTIMATE GAMING CONTROL CENTER"));

        LinearLayout deviceCard = card();

        TextView model =
                txt("📱 " + device.getModel(), 18);

        model.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        deviceCard.addView(model);
        deviceCard.addView(
                muted(device.getAndroidVersion())
        );

        p.addView(deviceCard);

        info(
                p,
                "◉ DPI",
                String.valueOf(device.getDpi())
        );

        info(
                p,
                "▣ RESOLUTION",
                device.getResolution()
        );

        info(
                p,
                "↻ REFRESH RATE",
                Math.round(device.getRefreshRate()) + " Hz"
        );

        int battery = device.getBatteryPercent();

        info(
                p,
                "🔋 BATTERY",
                battery < 0 ? "Not Available" : battery + "%"
        );

        float temp = device.getBatteryTemperature();

        info(
                p,
                "🌡 BATTERY TEMPERATURE",
                temp < 0 ? "Not Available" : temp + "°C"
        );

        info(
                p,
                "💾 RAM",
                device.getRamInfo()
        );

        info(
                p,
                "📶 NETWORK",
                network.getNetworkType()
        );

        int ping = network.getPing();

        info(
                p,
                "PING",
                ping < 0 ? "Not Available" : ping + " ms"
        );

        info(
                p,
                "🎮 GAMING MODE",
                "ACTIVE"
        );

        Button boost = btn("⚡ BOOST NOW ⚡");

        boost.setTextSize(18);

        boost.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        boost.setOnClickListener(v -> boost());

        p.addView(boost);

        p.addView(
                muted("PLAY SMARTER • NOT HARDER")
        );

        show(p);
    }

    private void boost() {

        LinearLayout p = page();

        p.addView(title("⚡ BOOST MODE"));

        LinearLayout hero = card();

        hero.setGravity(Gravity.CENTER);

        hero.addView(txt("🎮", 50));
        hero.addView(title("BOOST MODE"));
        hero.addView(
                muted("Official Android gaming controls")
        );

        p.addView(hero);

        info(
                p,
                "🎮 GAME MODE",
                "Available through Android settings"
        );

        info(
                p,
                "🔒 SCREEN AWAKE",
                "Supported during app use"
        );

        info(
                p,
                "⭕ IMMERSIVE",
                "Supported where available"
        );

        info(
                p,
                "🔕 DND",
                "Android settings"
        );

        Button display =
                btn("🖥 DISPLAY SETTINGS");

        display.setOnClickListener(
                v -> settings.openDisplay()
        );

        p.addView(display);

        Button battery =
                btn("🔋 BATTERY SETTINGS");

        battery.setOnClickListener(
                v -> settings.openBattery()
        );

        p.addView(battery);

        Button gameMode =
                btn("🎮 ANDROID GAME MODE");

        gameMode.setOnClickListener(
                v -> settings.openGameMode()
        );

        p.addView(gameMode);

        Button dnd =
                btn("🔕 DO NOT DISTURB");

        dnd.setOnClickListener(
                v -> settings.openDnd()
        );

        p.addView(dnd);

        Button developer =
                btn("🛠 DEVELOPER OPTIONS");

        developer.setOnClickListener(
                v -> settings.openDeveloper()
        );

        p.addView(developer);

        p.addView(
                muted("No fake FPS, RAM or ping improvement.")
        );

        show(p);
    }

    private void dpiPage() {

        LinearLayout p = page();

        p.addView(title("◉ DPI MANAGER"));

        LinearLayout current = card();

        current.setGravity(Gravity.CENTER);

        TextView value =
                txt(
                        String.valueOf(device.getDpi()),
                        40
                );

        value.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        current.addView(value);

        current.addView(
                muted("CURRENT DPI / DENSITY")
        );

        p.addView(current);

        p.addView(muted("DPI PRESETS"));

        int[] values = {
                360,
                420,
                480,
                540,
                600
        };

        for (int valueDpi : values) {

            Button b =
                    btn("DPI " + valueDpi);

            final int selected = valueDpi;

            b.setOnClickListener(
                    v -> dpiDialog(selected)
            );

            p.addView(b);
        }

        Button developer =
                btn("⚙ OPEN DEVELOPER OPTIONS");

        developer.setOnClickListener(
                v -> dpi.openDeveloperOptions()
        );

        p.addView(developer);

        LinearLayout note = card();

        note.addView(
                txt("ⓘ IMPORTANT", 15)
        );

        note.addView(
                muted(
                        "A normal Android app cannot silently "
                                + "change system DPI on standard devices."
                )
        );

        p.addView(note);

        show(p);
    }

    private void dpiDialog(int value) {

        new AlertDialog.Builder(this)
                .setTitle("DPI " + value)
                .setMessage(
                        "Android may require Developer Options, "
                                + "ADB or OEM-specific controls."
                )
                .setPositiveButton(
                        "OPEN SETTINGS",
                        (d, w) -> dpi.openDeveloperOptions()
                )
                .setNegativeButton(
                        "CANCEL",
                        null
                )
                .show();
    }

    private void gamesPage() {

        LinearLayout p = page();

        p.addView(title("🎮 MY GAMES"));

        p.addView(
                muted("INSTALLED LAUNCHABLE APPS")
        );

        List<android.content.pm.ResolveInfo> list =
                games.getLaunchableApps();

        if (list == null || list.isEmpty()) {

            LinearLayout empty = card();

            empty.addView(
                    txt(
                            "No launchable apps found.",
                            16
                    )
            );

            p.addView(empty);

        } else {

            for (
                    android.content.pm.ResolveInfo info :
                    list
            ) {

                String name =
                        info.loadLabel(
                                getPackageManager()
                        ).toString();

                String pkg =
                        info.activityInfo.packageName;

                gameCard(
                        p,
                        name,
                        pkg
                );
            }
        }

        p.addView(
                muted("Long press a game for its profile.")
        );

        show(p);
    }

    private void gameCard(
            LinearLayout parent,
            String name,
            String packageName
    ) {

        LinearLayout c = card();

        LinearLayout row =
                new LinearLayout(this);

        row.setGravity(
                Gravity.CENTER_VERTICAL
        );

        TextView nameView =
                txt(
                        "🎮 " + name,
                        16
                );

        nameView.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        row.addView(
                nameView,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        Button play = btn("PLAY");

        LinearLayout.LayoutParams playParams =
                new LinearLayout.LayoutParams(
                        95,
                        52
                );

        play.setLayoutParams(playParams);

        play.setOnClickListener(
                v -> games.launch(packageName)
        );

        row.addView(play);

        c.addView(row);

        c.setOnLongClickListener(v -> {

            profilePage(
                    packageName,
                    name
            );

            return true;
        });

        parent.addView(c);
    }

    private void profilePage(
            String packageName,
            String gameName
    ) {

        GameProfile profile =
                profiles.getProfile(packageName);

        LinearLayout p = page();

        p.addView(
                title(
                        "🎮 " +
                                gameName +
                                "\nGAME PROFILE"
                )
        );

        int savedDpi = profile.getDpi();

        info(
                p,
                "DPI",
                savedDpi == 0
                        ? device.getDpi() + " Default"
                        : String.valueOf(savedDpi)
        );

        info(
                p,
                "BRIGHTNESS",
                profile.getBrightness() + "%"
        );

        info(
                p,
                "SCREEN TIMEOUT",
                profile.getTimeout() + " Minutes"
        );

        info(
                p,
                "REFRESH RATE",
                Math.round(
                        profile.getRefreshRate()
                ) + " Hz"
        );

        info(
                p,
                "GAMING MODE",
                profile.isGamingMode()
                        ? "ON"
                        : "OFF"
        );

        info(
                p,
                "DND",
                profile.isDnd()
                        ? "ON"
                        : "OFF"
        );

        LinearLayout notes = card();

        notes.addView(
                txt("PROFILE NOTES", 15)
        );

        notes.addView(
                muted(
                        "Sensitivity: " +
                                profile.getSensitivity() +
                                "\nGraphics/FPS: " +
                                profile.getGraphics()
                )
        );

        p.addView(notes);

        Button save =
                btn("💾 SAVE PROFILE");

        save.setOnClickListener(v -> {

            profiles.saveProfile(profile);

            new AlertDialog.Builder(this)
                    .setTitle("Profile Saved")
                    .setMessage(
                            "Profile saved locally."
                    )
                    .setPositiveButton(
                            "OK",
                            null
                    )
                    .show();
        });

        p.addView(save);

        Button back =
                btn("← BACK TO GAMES");

        back.setOnClickListener(
                v -> gamesPage()
        );

        p.addView(back);

        show(p);
    }

    private void settingsPage() {

        LinearLayout p = page();

        p.addView(title("⚙ SETTINGS"));

        p.addView(
                muted("OFFICIAL ANDROID SHORTCUTS")
        );

        setting(
                p,
                "🖥 Display",
                "Display and brightness",
                () -> settings.openDisplay()
        );

        setting(
                p,
                "🔋 Battery",
                "Battery settings",
                () -> settings.openBattery()
        );

        setting(
                p,
                "🔕 Do Not Disturb",
                "Notification settings",
                () -> settings.openDnd()
        );

        setting(
                p,
                "🎮 Game Mode",
                "Android Game Mode",
                () -> settings.openGameMode()
        );

        setting(
                p,
                "🛠 Developer Options",
                "Advanced Android settings",
                () -> settings.openDeveloper()
        );

        setting(
                p,
                "📱 App Information",
                "GAME TURBO PRO",
                () -> settings.openAppInfo()
        );

        Button theme =
                btn("🎨 THEME / ACCENT");

        theme.setOnClickListener(
                v -> themeDialog()
        );

        p.addView(theme);

        LinearLayout privacy = card();

        privacy.addView(
                txt("🔐 PRIVACY", 15)
        );

        privacy.addView(
                muted(
                        "Game profiles are stored locally."
                )
        );

        p.addView(privacy);

        show(p);
    }

    private void setting(
            LinearLayout parent,
            String heading,
            String description,
            Runnable action
    ) {

        LinearLayout c = card();

        TextView h =
                txt(heading, 16);

        h.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        c.addView(h);
        c.addView(muted(description));

        c.setOnClickListener(
                v -> action.run()
        );

        parent.addView(c);
    }

    private void themeDialog() {

        String[] themes = {
                "Dark Default",
                "Neon Blue",
                "Neon Purple",
                "Neon Green",
                "Neon Orange"
        };

        new AlertDialog.Builder(this)
                .setTitle("🎨 CHOOSE THEME")
                .setItems(
                        themes,
                        (dialog, which) -> {

                            new AlertDialog.Builder(this)
                                    .setTitle("Theme Selected")
                                    .setMessage(
                                            themes[which]
                                    )
                                    .setPositiveButton(
                                            "OK",
                                            null
                                    )
                                    .show();
                        }
                )
                .show();
    }
                                 }
