package com.dua3.app.keystoremanager;

import com.dua3.utility.fx.FxLauncher;

public class Main {
    public static void main(String[] args) {
        FxLauncher.launchApplication(
                "com.dua3.app.keystoremanager.KeyStoreManager",
                args,
                "Keystore Manager",
                "0.0.1",
                "©2025 Axel Howind",
                "axh@dua3.com",
                "A simple Keystore Management Tool."
        );
    }
}
