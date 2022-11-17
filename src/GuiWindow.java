import javax.swing.*;
import java.awt.*;

public class GuiWindow {

    private JFrame frame;
    private JPanel upperPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private JTextField inputField;
    private JButton startButton;
    private JButton clearButton;
    private JScrollPane scrollArea;
    private JTextArea outputArea;


    public GuiWindow() {
        frame = new JFrame("My Unit Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(480, 360);
        upperPanel = createUpperPanel();
        middlePanel = createMiddlePanel();
        lowerPanel = createLowerPanel();
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
        startButton.addActionListener(a -> saySomething());
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
        scrollArea = new JScrollPane(outputArea);
        scrollArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollArea, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLowerPanel(){
        JPanel panel = new JPanel();
        clearButton = new JButton("Clear");
        clearButton.addActionListener(a -> outputArea.setText(null));
        panel.add(clearButton);
        return panel;
    }

    private void saySomething(){
        outputArea.append("Hola!\n");
    }
}
