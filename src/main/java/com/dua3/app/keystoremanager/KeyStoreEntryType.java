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

/**
 * Enum representing the type of entry in a KeyStore.
 * Each entry type is associated with a descriptive text label.
 */
public enum KeyStoreEntryType {
    /**
     * Represents an unknown type of entry in a KeyStore.
     */
    UNKNOWN("Unknown"),
    /**
     * Represents an entry type in the KeyStore for private keys.
     */
    PRIVATE_KEY("Private Key"),
    /**
     * Represents an entry type in a KeyStore that corresponds to a secret key.
     */
    SECRET_KEY("Secret Key"),
    /**
     * Represents a general key entry type in a KeyStore.
     */
    KEY("Key"),
    /**
     * Enum constant representing a certificate entry in a KeyStore.
     */
    CERTIFICTE("Certificate");

    private final String text;

    KeyStoreEntryType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
