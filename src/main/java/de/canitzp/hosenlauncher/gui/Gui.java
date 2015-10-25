package de.canitzp.hosenlauncher.gui;

import de.canitzp.hosenlauncher.Hosenlauncher;
import de.canitzp.hosenlauncher.Variables;
import de.canitzp.hosenlauncher.gui.exceptions.InvalidFxmlException;
import de.canitzp.hosenlauncher.gui.exceptions.NoSuchControllerException;
import de.canitzp.hosenlauncher.gui.exceptions.ControllerLoadException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AtomSponge
 */
public class Gui extends Application {
    private static final String[] FONTS = {"OpenSans-Bold.ttf", "OpenSans-BoldItalic.ttf",
            "OpenSans-ExtraBold.ttf", "OpenSans-ExtraBoldItalic.ttf",
            "OpenSans-Italic.ttf", "OpenSans-Light.ttf",
            "OpenSans-LightItalic.ttf", "OpenSans-Regular.ttf",
            "OpenSans-Semibold.ttf", "OpenSans-SemiboldItalic.ttf"};

    private Hosenlauncher launcher;
    private final Map<String, GuiController> controllers = new HashMap<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        load(Controllers.LOGIN, "/views/login.fxml", "title.launcher");
    }

    @Override
    public void init() throws Exception {
        launcher = Hosenlauncher.getInstance();
        launcher.setGui(this);
        loadFonts();
    }

    public static void initialize(String[] args) {
        launch(args);
    }

    public GuiController load(String controllerKey, String fxml, String title) throws InvalidFxmlException, ControllerLoadException {
        return load(controllerKey, fxml, title, false);
    }

    public GuiController load(String controllerKey, String fxml, String title, boolean resizeable) throws InvalidFxmlException, ControllerLoadException {
        return load(controllerKey, fxml, title, resizeable, true);
    }

    public GuiController load(String controllerKey, String fxml, String title, boolean resizeable, boolean autoShow) throws InvalidFxmlException, ControllerLoadException {
        controllerKey = controllerKey.toLowerCase();
        if (controllers.containsKey(controllerKey)) {
            throw new ControllerLoadException(String.format("Controller key %s already exists", controllerKey));
        }

        GuiController controller = null;
        try {
            controller = loadStage(fxml, title, resizeable, autoShow);
        } catch (IOException e) {
            throw new ControllerLoadException("Failed to load stage", e);
        }

        controllers.put(controllerKey, controller);
        return controller;
    }

    @SuppressWarnings("unchecked")
    public <T extends GuiController> T get(String controllerKey) {
        return (T) controllers.get(controllerKey.toLowerCase());
    }

    public void unload(String controllerKey) throws NoSuchControllerException {
        controllerKey = controllerKey.toLowerCase();
        if (controllers.containsKey(controllerKey)) {
            throw new NoSuchControllerException("Found no controller for key " + controllerKey);
        }

        GuiController controller = controllers.get(controllerKey);
        launcher.getLogger().info("Unloading controller {}", controller.getClass().getSimpleName());
        controller.getStage().close();
        controllers.remove(controllerKey);
    }

    public void unload(GuiController controller) throws NoSuchControllerException {
        if (!controllers.containsValue(controller)) {
            throw new NoSuchControllerException("Controller has not been registered");
        }

        launcher.getLogger().info("Unloading controller {}", controller.getClass().getSimpleName());
        controller.getStage().close();
        controllers.values().remove(controller);
    }

    private GuiController loadStage(String fxml, String title, boolean resizeable, boolean autoShow) throws IOException, InvalidFxmlException {
        launcher.getLogger().debug("Attempting to load stage " + fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml), launcher.getFormsBundle());

        Parent parent = fxmlLoader.<Parent>load();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(getClass().getResource("/assets/stylesheets/launcher.css").toExternalForm());
        Stage stage = new Stage();
        stage.setTitle(launcher.getFormsBundle().getString(title));
        stage.setResizable(resizeable);
        stage.setScene(scene);

        GuiController controller = null;
        try {
            launcher.getLogger().info("Initializing controller for " + fxml);
            controller = fxmlLoader.<GuiController>getController();
            controller.setGui(this);
            controller.setLauncher(launcher);
            controller.setStage(stage);
            controller.setFormsBundle(launcher.getFormsBundle());
            controller.setMessagesBundle(launcher.getMessagesBundle());
            controller.initialize();
        } catch (NullPointerException e) {
            throw new InvalidFxmlException("You have to set a controller class in the fxml file", e);
        }

        if (autoShow) {
            stage.show();
        }
        return controller;
    }

    private void loadFonts() {
        launcher.getLogger().info("Loading fonts");
        for (String font : FONTS) {
            launcher.getLogger().debug("Loading font {}", font);
            Font.loadFont(getClass().getResource("/assets/fonts/" + font).toExternalForm(), 13);
        }
    }
}
