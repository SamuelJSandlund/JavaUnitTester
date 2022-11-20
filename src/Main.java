import javax.swing.*;

/**
 * A simple implementation of a unit tester similar to JUnit 3
 *
 * @author Samuel Sandlund
 * @version 1.0
 * @since 2022-11-21
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuiWindow gui = new GuiWindow();
            TestUnitController controller = new TestUnitController(gui);
            gui.show();
        });
    }
}