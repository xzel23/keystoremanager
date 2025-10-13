package com.dua3.app.keystoremanager;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.security.KeyStore;

public class KeyStoreManager extends Application {

    @Override
    public void start(Stage primaryStage) {
        setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        TabPane tabPane = new TabPane();

        addKeyStoreTab(tabPane, "No Keystore", null);
        addKeyStoreTab(tabPane, "Also no Keystore", null);

        // Observe the tabs list
        ObservableList<Tab> tabs = tabPane.getTabs();
        tabs.addListener((javafx.collections.ListChangeListener<Tab>) change -> {
            // Defer until next pulse to ensure the skin exists
            Platform.runLater(() -> {
                boolean show = tabs.size() > 1;
                tabPane.lookupAll(".tab-header-area").forEach(header -> {
                    header.setVisible(show);
                    header.setManaged(show);
                });
            });
        });

        // Initial setup after skin creation
        tabPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> {
                    boolean show = tabs.size() > 1;
                    tabPane.lookupAll(".tab-header-area").forEach(header -> {
                        header.setVisible(show);
                        header.setManaged(show);
                    });
                });
            }
        });

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Keystore Manager");
        primaryStage.show();
    }

    private static void addKeyStoreTab(TabPane tabPane, String title, KeyStoreData keyStore) {
        KeyStorePane content = new KeyStorePane(keyStore);
        Tab tab = new Tab(title, content);
        content.prefWidthProperty().bind(tabPane.widthProperty());
        content.prefHeightProperty().bind(tabPane.heightProperty());
        tabPane.getTabs().add(tab);
    }
}
