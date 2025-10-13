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

import com.dua3.utility.crypt.SymmetricAlgorithm;
import com.dua3.utility.fx.controls.Dialogs;
import com.dua3.utility.fx.controls.InputDialogBuilder;
import com.dua3.utility.fx.controls.InputResult;
import com.dua3.utility.fx.controls.InputValidatorFactory;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.KeyStore;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A utility class providing secret key related dialogs.
 */
public final class SecretKeyDialogs {
    private static final Logger LOG = LogManager.getLogger(SecretKeyDialogs.class);

    private static final String IS_REQUIRED = " is required.";


    private SecretKeyDialogs() {}

    /**
     * The Fields enumeration defines a set of standard certificate fields used for
     * creating or managing certificates. Each field contains metadata, such as its
     * identifier, display label, data type, and whether the field is required.
     */
    public enum Fields {
        /**
         * Represents the alias field in a certificate.
         */
        ALIAS(ID_ALIAS, "Alias", String.class, true, "Unique identifier for the key"),
        /**
         * Represents the algorithm to use.
         */
        ALGORITHM(ID_ALGORITHM, "Algorithm", SymmetricAlgorithm.class, true, "Cryptographic algorithm to use for key generation"),;

        /**
         * Represents the ID for the certificate field.
         */
        public final String id;
        /**
         * Represents the label text for the certificate field.
         */
        public final String label;
        /**
         * Represents the data type of the certificate field.
         */
        public final Class<?> type;
        /**
         * Indicates that the field is required in a certificate.
         */
        public final boolean isRequired;
        /**
         * Represents the description text for the certificate field.
         */
        public final String description;

        /**
         * Returns the description text for this field.
         *
         * @return the description text
         */
        public String description() {
            return description;
        }

        Fields(String id, String label, Class<?> type, boolean required, String descripttion) {
            this.id = id;
            this.label = label;
            this.type = type;
            this.isRequired = required;
            this.description = descripttion;
        }
    }

    /**
     * ID for the parent key input.
     */
    public static final String ID_ALIAS = "ALIAS";
    /**
     * ID for the algorithm input.
     */
    public static final String ID_ALGORITHM = "ALGORITHM";

    /**
     * Builds and shows the input dialog for creating a new certificate.
     * The dialog includes options for selecting parent certificates,
     * choosing cryptographic algorithms, specifying key sizes, enabling certificate authority,
     * and setting validity duration. Additionally, standard certificate fields can be filled
     * in the dialog as required or optional inputs.
     *
     * @param owner           the owner window
     * @param ks              the KeyStore containing existing certificates, used to populate parent certificate options
     * @param vf              the factory for creating input validators to validate user inputs in the dialog
     * @return an Optional containing the result map or an empty Optional if the dialog was cancelled
     */
    public static Optional<InputResult> showNewSecretKeyDialog(Window owner, KeyStore ks, InputValidatorFactory vf) {
        LOG.debug("Showing new private key dialog.");

        InputDialogBuilder builder = Dialogs.input(owner)
                .title("New Secret Key")
                .header("Enter secret key details.");

        // add standard fields
        Stream.of(Fields.values())
                .forEach(field -> {
                    switch (field.type.getSimpleName()) {
                        case "String" -> builder.inputString(
                                field.id,
                                field.label,
                                () -> "",
                                field.isRequired ? vf.nonBlank(field.label + IS_REQUIRED) : vf.noCheck()
                        );
                        case "Integer" -> builder.inputInteger(
                                field.id,
                                field.label,
                                () -> null,
                                field.isRequired ? vf.nonNull(field.label + IS_REQUIRED) : vf.noCheck()
                        );
                        default -> {
                            if (field.type.isEnum()) {
                                //noinspection unchecked
                                builder.inputComboBox(
                                        field.id,
                                        field.label,
                                        () -> null,
                                        (Class<Enum>) field.type,
                                        vf.nonNull(field.label + IS_REQUIRED)
                                );
                            } else {
                                throw new IllegalStateException("Unexpected type: " + field.type.getSimpleName());
                            }
                        }
                    }
                });

        return builder.showAndWait();
    }
}
