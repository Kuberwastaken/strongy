package strongy.gui.components;

/**
 * Marker interface for themed JavaFX components.
 * In the JavaFX version, styling is handled by CSS, so this interface
 * is kept for backward compatibility but the methods are now no-ops.
 */
public interface ThemedComponent {
	default void updateColors() {}
	default void updateSize(Object styleManager) {}
}
