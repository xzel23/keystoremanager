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
package com.dua3.app.keystoremanager.dialogs;

import com.dua3.app.keystoremanager.KeyStoreData;
import com.dua3.utility.crypt.KeyStoreType;
import com.dua3.utility.fx.controls.Dialogs;
import com.dua3.utility.fx.controls.WizardDialogBuilder;
import com.dua3.utility.lang.LangUtil;
import javafx.collections.FXCollections;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ExportDialogs {
    private static final Logger LOG = LogManager.getLogger(ExportDialogs.class);
    private static final String ID_KEYSTORE_FOLDER = "KEYSTORE_FOLDER";
    private static final String ID_KEYSTORE_NAME = "KEYSTORE_NAME";
    private static final String ID_KEYSTORE_TYPE = "KEYSTORE_TYPE";
    private static final String ID_KEYSTORE_PASSWORD = "KEYSTORE_PASSWORD";
    private static final String SELECTED_ALIASES = "SELECTED_ALIASES";
    private static final String ITEMS_TO_EXPORT = "Select Items to export";
    private static final String KEYSTORE_SETTINGS = "Select target kKeystore location";

    public record ExportSettings(Path path, KeyStoreType type, String password, Map<String, KeyStoreExportSelctionInput.ExportChoice> selection) {}

    private ExportDialogs() {}

    public static Optional<ExportSettings> showExportDialog(
            Window owner,
            KeyStoreData keystore
    ) throws GeneralSecurityException {
        LOG.debug("Showing new export dialog.");

        // Create the dialog
        WizardDialogBuilder builder = Dialogs.wizard(owner)
                .title("Export to new Keystore");

        // Add a table showing the entries to let the user select what to export
        KeyStoreExportSelctionInput keyStoreExportSelectionInput = new KeyStoreExportSelctionInput(keystore);

        builder.page(ITEMS_TO_EXPORT, Dialogs.inputPane()
                .header("WARNING: Do not export sensitive keys or certificates unless you know exactly what you are doing!")
                .addInput(SELECTED_ALIASES, Map.class, FXCollections::observableHashMap, keyStoreExportSelectionInput)
        );

        // where to write the keystore to
        builder.page(KEYSTORE_SETTINGS, Dialogs.inputPane()
                .header("Select where to store the new keystore and provide a password for it.")
                .inputComboBox(ID_KEYSTORE_TYPE, "Type", () -> KeyStoreType.PKCS12, KeyStoreType.class, List.of(KeyStoreType.values()))
                .inputFolder(ID_KEYSTORE_FOLDER, "Target Folder", keystore.path()::getParent, true)
                .inputString(ID_KEYSTORE_NAME, "Keystore name", () -> null,
                        v -> v != null && !v.isBlank()
                                ? Optional.empty()
                                : Optional.of("A keystore name is required.")
                )
                .inputPasswordWithVerification(ID_KEYSTORE_PASSWORD, "Password", "Repeat Password")
        );

        // show the wizard
        return builder.showAndWait()
                .map(result -> {
                    // extract general keystore settings
                    Map<String, Object> settings = (Map<String, Object>) LangUtil.getOrThrow(result, KEYSTORE_SETTINGS);

                    KeyStoreType type = (KeyStoreType) LangUtil.getOrThrow(settings, ID_KEYSTORE_TYPE);
                    String password = (String) LangUtil.getOrThrow(settings, ID_KEYSTORE_PASSWORD);
                    Path folder = (Path) LangUtil.getOrThrow(settings, ID_KEYSTORE_FOLDER);
                    String name = (String) LangUtil.getOrThrow(settings, ID_KEYSTORE_NAME);
                    Path path = folder.resolve(name + "." + type.getExtension());

                    // extract selected entries
                    Map<String, KeyStoreExportSelctionInput.ExportChoice> selectedAliases =
                            (Map<String, KeyStoreExportSelctionInput.ExportChoice>) LangUtil.getOrThrow(
                                    (Map<String, Object>) LangUtil.getOrThrow(result, ITEMS_TO_EXPORT),
                                    SELECTED_ALIASES
                            );

                    return new ExportSettings(path, type, password, selectedAliases);
                });
    }

}