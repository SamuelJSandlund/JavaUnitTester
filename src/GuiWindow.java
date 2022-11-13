import javax.swing.*;

public class GuiWindow {
    private JFrame frame;
    private JTextField classInput;
    private JTextArea outputWindow;

    public GuiWindow(){
        frame = new JFrame("Unit Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        classInput = new JTextField();
        outputWindow = new JTextArea();
        frame.add(classInput);
        frame.add(outputWindow);
        frame.pack();
    }

    public void Show(){
        frame.setVisible(true);
    }
}
