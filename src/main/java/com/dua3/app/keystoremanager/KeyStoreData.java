package com.dua3.app.keystoremanager;

import java.nio.file.Path;
import java.security.KeyStore;

record KeyStoreData(KeyStore keyStore, String password, Path path) {}
