module view {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires com.google.gson;
    requires java.xml;
    requires javafx.base;

    opens view to javafx.fxml;
    exports view;
}
