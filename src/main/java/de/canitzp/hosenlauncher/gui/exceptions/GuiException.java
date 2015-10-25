package de.canitzp.hosenlauncher.gui.exceptions;

/**
 * @author AtomSponge
 */
public abstract class GuiException extends Exception {
    public GuiException(String message) {
        super(message);
    }

    public GuiException(String message, Throwable cause) {
        super(message, cause);
    }
}
