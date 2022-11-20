import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles interaction between GuiWindow and TestRunners
 *
 * @author Samuel Sandlund
 * @version 1.0
 * @since 2022-11-21
 */
public class TestUnitController {
    private final GuiWindow view;

    public TestUnitController(GuiWindow view){
        this.view = view;
        view.listenForInput(new runTestButtonListener());
    }

    /**
     * Internal class used to try to run tests from the class given as user input
     */
    private class runTestButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try {
                Class<?> c = Class.forName(view.getInputField());
                TestRunner tester = new TestRunner(c, view);
                tester.execute();
            }
            catch (ClassNotFoundException ex)
            {
                view.output("ERROR: Entered class not found\n");
            }
        }
    }
}
