import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GuiWindow gui = new GuiWindow();
            TestUnitController controller = new TestUnitController(gui);
            gui.show();
        });
    }
}