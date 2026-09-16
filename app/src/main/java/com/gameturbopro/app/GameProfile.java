package com.gameturbopro.app;

public class GameProfile {

    private String packageName;
    private int dpi;
    private int brightness;
    private int timeout;
    private float refreshRate;

    private String sensitivity;
    private String graphics;

    private boolean gamingMode;
    private boolean dnd;

    public GameProfile(String packageName) {
        this.packageName = packageName;

        dpi = 0;
        brightness = 70;
        timeout = 10;
        refreshRate = 120f;

        sensitivity = "High";
        graphics = "Smooth + Extreme";

        gamingMode = true;
        dnd = false;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public float getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(float refreshRate) {
        this.refreshRate = refreshRate;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public boolean isGamingMode() {
        return gamingMode;
    }

    public void setGamingMode(boolean gamingMode) {
        this.gamingMode = gamingMode;
    }

    public boolean isDnd() {
        return dnd;
    }

    public void setDnd(boolean dnd) {
        this.dnd = dnd;
    }
}
