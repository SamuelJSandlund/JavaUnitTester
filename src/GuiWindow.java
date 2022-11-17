import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GuiWindow {

    private final JFrame frame;
    private JTextField inputField;
    private JButton startButton;
    private JTextArea outputArea;


    public GuiWindow() {
        frame = new JFrame("My Unit Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(480, 360);
        JPanel upperPanel = createUpperPanel();
        JPanel middlePanel = createMiddlePanel();
        JPanel lowerPanel = createLowerPanel();
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);
    }

    /**
     * Makes this frame visible
     */
    public void show(){
        frame.setVisible(true);
    }
    public void output(String s){
        outputArea.append(s);
    }
    public void listenForInput(ActionListener startButtonListener){
        startButton.addActionListener(startButtonListener);
    }
    public String getInputField(){
        return inputField.getText();
    }


    /**
     * Creates a new JPanel with a text field for input and a button
     * @return JPanel
     */
    private JPanel createUpperPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        inputField = new JTextField("");
        panel.add(inputField, BorderLayout.CENTER);
        startButton = new JButton("Start Test");
        panel.add(startButton, BorderLayout.EAST);
        return panel;
    }

    /**
     * Creates a new JPanel with a scrollable and non-editable text area.
     * @return JPanel
     */
    private JPanel createMiddlePanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane scrollArea = new JScrollPane(outputArea);
        scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollArea, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLowerPanel(){
        JPanel panel = new JPanel();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(a -> outputArea.setText(null));
        panel.add(clearButton);
        return panel;
    }

    //TEST METHODS
    private void saySomething(){
        outputArea.append("Hola!\n");
    }

}
