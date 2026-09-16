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

    private final int WHITE = Color.rgb(245, 250, 255);
    private final int MUTED = Color.rgb(125, 145, 165);
    private final int CYAN = Color.rgb(0, 200, 255);
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
        layout.setPadding(16, 18, 16, 24);
        layout.setBackgroundColor(Color.rgb(3, 5, 10));
        return layout;
    }

    private ScrollView scroll(View view) {
        ScrollView s = new ScrollView(this);
        s.setFillViewport(true);
        s.addView(view);
        return s;
    }

    private TextView text(String value, float size) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextColor(WHITE);
        t.setTextSize(size);
        t.setPadding(3, 4, 3, 4);
        return t;
    }

    private TextView title(String value) {
        TextView t = text(value, 24);
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setPadding(4, 5, 4, 12);
        return t;
    }

    private TextView muted(String value) {
        TextView t = text(value, 12);
        t.setTextColor(MUTED);
        return t;
    }

    private LinearLayout card() {
        LinearLayout c = new LinearLayout(this);
        c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(14, 12, 14, 12);
        c.setBackgroundResource(R.drawable.bg_card);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        p.setMargins(0, 5, 0, 5);
        c.setLayoutParams(p);

        return c;
    }

    private Button button(String label) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextColor(Color.BLACK);
        b.setTextSize(14);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setBackgroundResource(R.drawable.bg_primary);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(-1, 54);

        p.setMargins(0, 6, 0, 6);
        b.setLayoutParams(p);

        return b;
    }

    private void status(
            LinearLayout parent,
            String name,
            String value
    ) {
        LinearLayout c = card();

        TextView a = text(name, 14);
        TextView b = text(value, 13);
        b.setTextColor(GREEN);

        c.addView(a);
        c.addView(b);

        parent.addView(c);
    }

    private void infoRow(
            LinearLayout parent,
            String a,
            String b,
            String c
    ) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        addInfo(row, a);
        addInfo(row, b);
        addInfo(row, c);

        parent.addView(row);
    }

    private void addInfo(
            LinearLayout row,
            String value
    ) {
        LinearLayout c = card();

        TextView t = text(value, 13);
        t.setGravity(Gravity.CENTER);

        c.addView(t);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(0, -2, 1);

        p.setMargins(3, 3, 3, 3);

        c.setLayoutParams(p);

        row.addView(c);
    }

    private void showHome() {
        clear();

        LinearLayout p = page();

        p.addView(title("⚡ GAME TURBO PRO"));
        p.addView(muted("ULTIMATE GAMING CONTROL CENTER"));

        LinearLayout device = card();

        TextView model =
                text(
                        "📱 " + deviceMonitor.getModel(),
                        18
                );

        model.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        device.addView(model);
        device.addView(
                muted(deviceMonitor.getAndroidVersion())
        );

        p.addView(device);

        infoRow(
                p,
                "DPI\n" + deviceMonitor.getDpi(),
                "RESOLUTION\n" + deviceMonitor.getResolution(),
                "REFRESH\n"
                        + Math.round(
                        deviceMonitor.getRefreshRate()
                )
                        + " Hz"
        );

        infoRow(
                p,
                "🔋 BATTERY\n"
                        + deviceMonitor.getBatteryPercent()
                        + "%",
                "🌡 TEMP\n"
                        + deviceMonitor.getBatteryTemperature()
                        + "°C",
                "💾 RAM\n"
                        + deviceMonitor.getRamInfo()
        );

        infoRow(
                p,
                "📶 NETWORK\n"
                        + networkMonitor.getNetworkType(),
                "PING\n"
                        + ping(),
                "MODE\nON"
        );

        LinearLayout mode = card();

        TextView modeTitle =
                text("🎮 GAMING MODE", 17);

        modeTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        mode.addView(modeTitle);

        TextView active =
                text("● ACTIVE", 14);

        active.setTextColor(GREEN);

        mode.addView(active);

        p.addView(mode);

        Button boost = button(
                "⚡  BOOST NOW  ⚡"
        );

        boost.setTextSize(19);
        boost.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        boost.setOnClickListener(v -> showBoost());

        p.addView(boost);

        p.addView(
                muted("PLAY SMARTER  ⚡  NOT HARDER")
        );

        content.addView(scroll(p));
    }

    private String ping() {
        int value = networkMonitor.getPing();

        if (value < 0) {
            return "N/A";
        }

        return value + " ms";
    }

    private void showBoost() {
        clear();

        LinearLayout p = page();

        p.addView(title("⚡ BOOST MODE"));

        LinearLayout hero = card();
        hero.setGravity(Gravity.CENTER);

        TextView icon = text("🎮", 50);
        icon.setGravity(Gravity.CENTER);

        hero.addView(icon);

        TextView h = text(
                "BOOST MODE",
                25
        );

        h.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        h.setGravity(Gravity.CENTER);

        hero.addView(h);
        hero.addView(
                muted("Gaming optimization center")
        );

        p.addView(hero);

        status(
                p,
                "🎮 Gaming Mode",
                "ON"
        );

        status(
                p,
                "🔒 Keep Screen Awake",
                "ON"
        );

        status(
                p,
                "⭕ Immersive Mode",
                "Supported where available"
        );

        status(
                p,
                "🔕 Do Not Disturb",
                "OFF"
        );

        p.addView(
                text("QUICK SETTINGS", 17)
        );

        Button display =
                button("🖥 Display Settings");

        display.setOnClickListener(
                v -> settingsHelper.openDisplay()
        );

        p.addView(display);

        Button battery =
                button("🔋 Battery Settings");

        battery.setOnClickListener(
                v -> settingsHelper.openBattery()
        );

        p.addView(battery);

        Button gameMode =
                button("🎮 Android Game Mode");

        gameMode.setOnClickListener(
                v -> settingsHelper.openGameMode()
        );

        p.addView(gameMode);

        Button dnd =
                button("🔕 Do Not Disturb Settings");

        dnd.setOnClickListener(
                v -> settingsHelper.openDnd()
        );

        p.addView(dnd);

        Button developer =
                button("🛠 Developer Options");

        developer.setOnClickListener(
                v -> settingsHelper.openDeveloper()
        );

        p.addView(developer);

        p.addView(
                muted(
                        "Only official Android capabilities are used."
                )
        );

        content.addView(scroll(p));
    }

    private void showDpi() {
        clear();

        LinearLayout p = page();

        p.addView(title("◉ DPI MANAGER"));

        LinearLayout current = card();
        current.setGravity(Gravity.CENTER);

        TextView value =
                text(
                        String.valueOf(
                                deviceMonitor.getDpi()
                        ),
                        40
                );

        value.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        value.setGravity(Gravity.CENTER);

        current.addView(value);

        TextView label =
                muted("CURRENT DPI / DENSITY");

        label.setGravity(Gravity.CENTER);

        current.addView(label);

        p.addView(current);

        p.addView(
                muted("DPI PRESETS")
        );

        int[] values = {
                360,
                420,
                480,
                540,
                600
        };

        for (int dpi : values) {

            Button b =
                    button("DPI  " + dpi);

            int selected = dpi;

            b.setOnClickListener(
                    v -> dpiDialog(selected)
            );

            p.addView(b);
        }

        Button dev =
                button(
                        "⚙ OPEN DEVELOPER OPTIONS"
                );

        dev.setOnClickListener(
                v -> dpiManager.openDeveloperOptions()
        );

        p.addView(dev);

        LinearLayout note = card();

        note.addView(
                text("ⓘ IMPORTANT", 15)
        );

        note.addView(
                muted(
                        "Normal Android applications cannot "
                                + "silently change system DPI on "
                                + "standard devices."
                )
        );

        p.addView(note);

        content.addView(scroll(p));
    }

    private void dpiDialog(int dpi) {

        new AlertDialog.Builder(this)
                .setTitle("DPI " + dpi)
                .setMessage(
                        "Android may require Developer Options, "
                                + "ADB or OEM-specific permissions "
                                + "to actually change system density."
                )
                .setPositiveButton(
                        "OPEN SETTINGS",
                        (d, w) ->
                                dpiManager.openDeveloperOptions()
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

        p.addView(title("🎮 MY GAMES"));

        p.addView(
                muted("INSTALLED LAUNCHABLE APPS")
        );

        List<
                android.content.pm.ResolveInfo
                > apps =
                gameLauncher.getLaunchableApps();

        if (apps.isEmpty()) {

            LinearLayout empty = card();

            empty.addView(
                    text(
                            "No launchable apps found.",
                            16
                    )
            );

            p.addView(empty);
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

            gameCard(
                    p,
                    name,
                    packageName
            );
        }

        p.addView(
                muted(
                        "Long press a game for its profile."
                )
        );

        content.addView(scroll(p));
    }

    private void gameCard(
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

        TextView title =
                text(
                        "🎮  " + name,
                        16
                );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        row.addView(
                title,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        Button play = button("PLAY");

        play.setTextSize(11);

        play.setOnClickListener(
                v -> gameLauncher.launch(packageName)
        );

        row.addView(
                play,
                new LinearLayout.LayoutParams(
                        95,
                        52
                )
        );

        c.addView(row);

        c.setOnLongClickListener(v -> {

            showProfile(
                    packageName,
                    name
            );

            return true;
        });

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
                        "🎮 " + gameName
                                + "\nGAME PROFILE"
                )
        );

        status(
                p,
                "DPI",
                profile.getDpi() == 0
                        ? deviceMonitor.getDpi()
                                + " Default"
                        : String.valueOf(
                                profile.getDpi()
                        )
        );

        status(
                p,
                "Brightness",
                profile.getBrightness() + "%"
        );

        status(
                p,
                "Screen Timeout",
                profile.getTimeout() + " Minutes"
        );

        status(
                p,
                "Refresh Rate",
                Math.round(
                        profile.getRefreshRate()
                ) + " Hz"
        );

        status(
                p,
                "Gaming Mode",
                profile.isGamingMode()
                        ? "ON"
                        : "OFF"
        );

        status(
                p,
                "DND",
                profile.isDnd()
                        ? "ON"
                        : "OFF"
        );

        LinearLayout notes = card();

        notes.addView(
                text("PROFILE NOTES", 15)
        );

        notes.addView(
                muted(
                        "Sensitivity: "
                                + profile.getSensitivity()
                                + "\nGraphics/FPS: "
                                + profile.getGraphics()
                )
        );

        p.addView(notes);

        Button save =
                button("SAVE PROFILE");

        save.setOnClickListener(v -> {

            profileManager.saveProfile(
                    profile
            );

            new AlertDialog.Builder(this)
                    .setTitle("Profile Saved")
                    .setMessage(
                            "Game profile saved locally."
                    )
                    .setPositiveButton(
                            "OK",
                            null
                    )
                    .show();
        });

        p.addView(save);

        Button back =
                button("← BACK TO GAMES");

        back.setOnClickListener(
                v -> showGames()
        );

        p.addView(back);

        content.addView(scroll(p));
    }

    private void showSettings() {
        clear();

        LinearLayout p = page();

        p.addView(title("⚙ SETTINGS"));

        p.addView(
                muted(
                        "OFFICIAL ANDROID SHORTCUTS"
                )
        );

        setting(
                p,
                "🖥 Display",
                "Brightness and display controls",
                () -> settingsHelper.openDisplay()
        );

        setting(
                p,
                "🔋 Battery",
                "Battery and battery saver",
                () -> settingsHelper.openBattery()
        );

        setting(
                p,
                "🔕 Do Not Disturb",
                "Notification policy access",
                () -> settingsHelper.openDnd()
        );

        setting(
                p,
                "🎮 Game Mode",
                "Android Game Mode",
                () -> settingsHelper.openGameMode()
        );

        setting(
                p,
                "🛠 Developer Options",
                "Advanced Android controls",
                () -> settingsHelper.openDeveloper()
        );

        setting(
                p,
                "📱 App Information",
                "GAME TURBO PRO app information",
                () -> settingsHelper.openAppInfo()
        );

        Button theme =
                button("🎨 THEME / ACCENT");

        theme
