module keystoremanager {
    exports com.dua3.app.keystoremanager;

    requires com.dua3.utility;
    requires com.dua3.utility.fx;
    requires com.dua3.utility.fx.controls;
    requires org.apache.logging.log4j;
    requires java.prefs;
    requires org.jspecify;
    requires java.desktop;
    requires org.bouncycastle.provider;
    requires javafx.graphics;
    requires javafx.controls;
    requires atlantafx.base;
}