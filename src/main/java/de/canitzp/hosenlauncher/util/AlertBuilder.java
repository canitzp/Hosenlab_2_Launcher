package de.canitzp.hosenlauncher.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.ResourceBundle;

/**
 * @author AtomSponge
 */
public class AlertBuilder {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("lang.forms");

    public static AlertBuilder newBuilder(Alert.AlertType alertType, ButtonType... buttons) {
        return new AlertBuilder(alertType, buttons);
    }

    private final Alert alert;

    private AlertBuilder(Alert.AlertType alertType, ButtonType... buttons) {
        alert = new Alert(alertType, "", buttons);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setHeaderText(null);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/assets/stylesheets/launcher.css").toExternalForm());
    }

    public AlertBuilder owner(Window window) {
        alert.initOwner(window);
        return this;
    }

    public AlertBuilder style(StageStyle style) {
        alert.initStyle(style);
        return this;
    }

    public AlertBuilder modality(Modality modality) {
        alert.initModality(modality);
        return this;
    }

    public AlertBuilder contentText(String text) {
        contentText(text, true);
        return this;
    }

    public AlertBuilder contentText(String text, boolean localized) {
        alert.setContentText(localized ? BUNDLE.getString(text) : text);
        return this;
    }

    public AlertBuilder headerText(String text) {
        headerText(text, true);
        return this;
    }

    public AlertBuilder headerText(String text, boolean localized) {
        alert.setContentText(localized ? BUNDLE.getString(text) : text);
        return this;
    }

    public AlertBuilder title(String text) {
        title(text, true);
        return this;
    }

    public AlertBuilder title(String text, boolean localized) {
        alert.setContentText(localized ? BUNDLE.getString(text) : text);
        return this;
    }

    public AlertBuilder show() {
        alert.show();
        return this;
    }

    public AlertBuilder showAndWait() {
        alert.showAndWait();
        return this;
    }

    public Alert getAlert() {
        return alert;
    }
}
