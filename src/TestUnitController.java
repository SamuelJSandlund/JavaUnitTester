import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Handles interactions with the GuiWindow class
 *
 * @author Samuel Sandlund
 * @version 2.0
 * @since 2022-11-16
 */
public class TestUnitController {
    private GuiWindow view;

    public TestUnitController(){
        SwingUtilities.invokeLater(() ->{
            view = new GuiWindow();
            view.listenForInput(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new TestRunnerWorker(view.getInputField()).execute();
                }
            });
            view.show();
        });
    }

    /**
     * Runs tests on a background thread and prints results to GUI on EDT
     */
    private class TestRunnerWorker extends SwingWorker<Void, String>{
        private String input;
        private String res;

        private TestRunnerWorker(String input){
            this.input = input;
            res = "";
        }

        @Override
        protected Void doInBackground(){
            try{
                Class<?> c = Class.forName(input);
                TestRunnerModel testRunner = new TestRunnerModel();
                res = testRunner.runTestClass(c);
            }
            catch (ClassNotFoundException e) {
                res = "ERROR: Entered class not found";
            }
            return null;
        }

        @Override
        protected void done(){
            view.output(res);
        }
    }
}
