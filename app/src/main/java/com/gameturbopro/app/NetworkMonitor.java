package com.gameturbopro.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import java.net.InetAddress;

public class NetworkMonitor {

    private final Context context;

    public NetworkMonitor(Context context) {
        this.context = context;
    }

    public String getNetworkType() {

        ConnectivityManager cm =
                (ConnectivityManager)
                        context.getSystemService(
                                Context.CONNECTIVITY_SERVICE
                        );

        if (cm == null) {
            return "Offline";
        }

        Network network =
                cm.getActiveNetwork();

        if (network == null) {
            return "Offline";
        }

        NetworkCapabilities caps =
                cm.getNetworkCapabilities(network);

        if (caps == null) {
            return "Unknown";
        }

        if (caps.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI)) {

            return "Wi-Fi";
        }

        if (caps.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR)) {

            return "Mobile";
        }

        return "Connected";
    }

    public int getPing() {

        try {

            long start =
                    System.currentTimeMillis();

            InetAddress address =
                    InetAddress.getByName(
                            "1.1.1.1"
                    );

            boolean reachable =
                    address.isReachable(2500);

            long end =
                    System.currentTimeMillis();

            if (reachable) {
                return (int) (end - start);
            }

        } catch (Exception ignored) {
        }

        return -1;
    }
}
