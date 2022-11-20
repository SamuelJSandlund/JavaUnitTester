import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Defines and creates a GUI for MyUnitTester
 *
 * @author Samuel Sandlund
 * @version 1.0
 * @since 2022-11-21
 */
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

    /**
     * Prints a message to the output area of this GuiWindow
     * @param s String: The message to be printed
     */
    public void output(String s){
        SwingUtilities.invokeLater(() -> outputArea.append(s));
    }

    /**
     * Sets up a listener for the "Start Test" button in this GUI
     * @param startButtonListener ActionListener that defines action performed when the button is clicked
     */
    public void listenForInput(ActionListener startButtonListener){
        startButton.addActionListener(startButtonListener);
    }

    /**
     * Returns a string with the current content of the input field in the GUI
     * Can for example be used by an action listener
     * @return String containing user input
     */
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

    /**
     * Creates a new JPanel with a button to clear the output area.
     * @return JPanel
     */
    private JPanel createLowerPanel(){
        JPanel panel = new JPanel();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(a -> SwingUtilities.invokeLater(()->outputArea.setText(null)));
        panel.add(clearButton);
        return panel;
    }
}
