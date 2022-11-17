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
            String s = view.getInputField();
            view.output(s.toUpperCase() + "\n");
        }
    }
}
