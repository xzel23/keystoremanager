package com.dua3.app.keystoremanager;

import com.dua3.utility.crypt.KeyStoreType;
import com.dua3.utility.crypt.KeyStoreUtil;
import com.dua3.utility.crypt.PasswordUtil;
import com.dua3.utility.fx.FxImage;
import com.dua3.utility.fx.FxImageUtil;
import com.dua3.utility.fx.controls.Controls;
import com.dua3.utility.fx.controls.Dialogs;
import com.dua3.utility.fx.controls.FileDialogMode;
import com.dua3.utility.fx.controls.InputValidatorFactory;
import com.dua3.utility.lang.LangUtil;
import com.dua3.utility.text.MessageFormatter;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class KeyStorePane extends Pane {

    private static final String LOGO_PATH = "/com/dua3/app/keystoremanager/logo-256.png";
    private static final FxImage LOGO;

    static {
        try {
            LOGO = (FxImage) FxImageUtil.getInstance().load(LangUtil.getResourceURL(KeyStorePane.class, LOGO_PATH));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private @Nullable KeyStoreData keyStore;

    public KeyStorePane(@Nullable KeyStoreData keyStore) {
        this.keyStore = keyStore;
        updateContent();
    }

    private void setKeyStore(@Nullable KeyStoreData keyStore) {
        this.keyStore = keyStore;
        updateContent();
    }

    private void updateContent() {
        if (keyStore == null) {
            setContentEmpty();
        } else {
            setContent(keyStore);
        }
    }

    private void setContent(@Nullable KeyStoreData keyStore) {
        getChildren().clear();
    }

    private void setContentEmpty() {
        ImageView logo = new ImageView(LOGO.fxImage());

        double buttonWidth = logo.getFitWidth() * 0.45;
        HBox buttons = new HBox(
                Controls.button()
                        .text("Create Keystore")
                        .prefWidth(buttonWidth)
                        .action(this::createKeyStore)
                        .build(),
                Controls.spacer(),
                Controls.button().text("Load Keystore").prefWidth(buttonWidth).build()
        );
        buttons.setMaxWidth(LOGO.width());

        VBox vBox = new VBox(
                logo,
                buttons
        );
        vBox.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane(vBox);

        stackPane.prefWidthProperty().bind(widthProperty());
        stackPane.prefHeightProperty().bind(heightProperty());

        minWidthProperty().bind(stackPane.minWidthProperty());
        minHeightProperty().bind(stackPane.minHeightProperty());

        getChildren().setAll(stackPane);
    }

    private void createKeyStore() {
        InputValidatorFactory vf = new InputValidatorFactory(MessageFormatter.standard());
        AtomicReference<String> passwordRef = new AtomicReference<>(null);
        Supplier<Path> initialFolder = () -> Paths.get(".").toAbsolutePath();

        String keyFolder = "folder";
        String keyName = "name";
        String keyType = "type";
        String keyPassword = "password";
        String keyPasswordRepeat = "password_repeat";

        Dialogs.input(getScene().getWindow())
                .title("Create Keystore")
                .chooseFile(keyFolder, "Folder", initialFolder, FileDialogMode.DIRECTORY, true, Collections.emptyList(), vf.isDirectory("Select an existing folder"))
                .string(keyName, "Name", () -> "", vf.nonEmpty("Name is required"))
                .comboBox(keyType, "Type", () -> KeyStoreType.PKCS12, KeyStoreType.class, List.of(KeyStoreType.values()))
                .string(keyPassword, "Password", () -> "", s -> {
                    passwordRef.set(s);
                    PasswordUtil.PasswordStrength strength = PasswordUtil.evaluatePasswordStrength(s.toCharArray());
                    if (strength.strengthLevel().compareTo(PasswordUtil.StrengthLevel.MODERATE) < 0) {
                        return Optional.of("Password is too weak: " + strength.strengthLevel());
                    } else {
                        return Optional.empty();
                    }
                })
                .string(keyPasswordRepeat, "Repeat Password", () -> "",
                        s -> Objects.equals(s, passwordRef.get())
                                ? Optional.empty()
                                : Optional.of("Passwords do not match.")
                )
                .showAndWait()
                .ifPresent(map -> {
                    try {
                        Path folder = (Path) map.get(keyFolder);
                        String name = (String) map.get(keyName);
                        KeyStoreType type = (KeyStoreType) map.get(keyType);
                        String password = (String) map.get(keyPassword);

                        Path keyStorePath = folder.resolve(name + '.' + type.getExtension());

                        if (Files.exists(keyStorePath)) {
                            throw new IOException("File already exists: " + keyStorePath);
                        }

                        KeyStore instance = KeyStoreUtil.createKeyStore(type, password.toCharArray());
                        KeyStoreUtil.saveKeyStoreToFile(instance, keyStorePath, password.toCharArray());

                        setKeyStore(new KeyStoreData(instance, password, keyStorePath));

                        Dialogs.alert(getScene().getWindow(), Alert.AlertType.INFORMATION)
                                .title("New KeyStore Created")
                                .header("The new keystore has successfully been created and saved.")
                                .showAndWait();
                    } catch (GeneralSecurityException e) {
                        Dialogs.alert(getScene().getWindow(), Alert.AlertType.ERROR)
                                .title("Error creating KeyStore")
                                .header("The new keystore could not be created.")
                                .text(e.getMessage())
                                .showAndWait();
                    } catch (IOException e) {
                        Dialogs.alert(getScene().getWindow(), Alert.AlertType.ERROR)
                                .title("Error creating KeyStore")
                                .header("The new keystore could not be saved.")
                                .text(e.getMessage())
                                .showAndWait();
                    }
                });
    }
}
