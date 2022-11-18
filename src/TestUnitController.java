import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestUnitController {
    private final GuiWindow view;
    public TestUnitController(GuiWindow view){
        this.view = view;
        view.listenForInput(new runTestButtonListener());
    }
    class runTestButtonListener implements ActionListener {
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
