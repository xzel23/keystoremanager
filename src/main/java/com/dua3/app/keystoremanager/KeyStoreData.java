/*
 * This file is part of Keystore Manager.
 *
 * Keystore Manager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published
 * by the Free Software Foundation.
 *
 * Keystore Manager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Keystore Manager. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-only
 * Copyright (c) 2025 Axel Howind
 */
package com.dua3.app.keystoremanager;

import java.nio.file.Path;
import java.security.KeyStore;

/**
 * Represents a container for KeyStore data.
 * This record encapsulates the KeyStore, its associated password, and the file path to the KeyStore.
 *
 * @param keyStore the KeyStore instance containing cryptographic keys and certificates
 * @param password the password required to access the KeyStore
 * @param path the file system path of the KeyStore
 */
public record KeyStoreData(KeyStore keyStore, String password, Path path) {

    /**
     * Returns a string representation of the {@code KeyStoreData} instance.
     * The representation includes the {@code keyStore} and {@code path} fields,
     * while the {@code password} field is represented as "[hidden]" for security reasons.
     *
     * @return a string describing the {@code KeyStoreData} instance
     */
    @Override
    public String toString() {
        return "KeyStoreData{" +
                "keyStore=" + keyStore +
                ", password=[hidden]" +
                ", path=" + path +
                '}';
    }
}
