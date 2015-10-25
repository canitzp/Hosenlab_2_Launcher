package de.canitzp.hosenlauncher.gui;

import de.canitzp.hosenlauncher.Hosenlauncher;
import de.canitzp.hosenlauncher.gui.exceptions.NoSuchControllerException;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ResourceBundle;

/**
 * @author AtomSponge
 */
@Getter
@Setter(AccessLevel.PACKAGE)
public abstract class GuiController {
    private Hosenlauncher launcher;
    private Gui gui;
    private Stage stage;

    private ResourceBundle formsBundle;
    private ResourceBundle messagesBundle;

    protected void initialize() {
    }

    protected final void unload() {
        try {
            getGui().unload(this);
        } catch (NoSuchControllerException e) {
            getLauncher().getLogger().error("Failed to unload controller", e);
        }
    }
}
