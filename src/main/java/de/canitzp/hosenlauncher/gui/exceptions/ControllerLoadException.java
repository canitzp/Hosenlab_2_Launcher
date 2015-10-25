package de.canitzp.hosenlauncher.gui.exceptions;

/**
 * @author AtomSponge
 */
public class ControllerLoadException extends GuiException {
    public ControllerLoadException(String message) {
        super(message);
    }

    public ControllerLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
