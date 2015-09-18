package de.canitzp.hosenlab2launcher;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Window extends JFrame{
    
    private int width, height;
    private String title;
    private JComboBox comboBox;
    private JTextArea textArea;
    private JButton button;
    private Container container;
    private DefaultCaret caret;

    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
        initWindow();
    }

    private void initWindow(){
        initParts();
        setResizable(false);
        setTitle(title);
        setSize(new Dimension(width, height));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible();
    }

    private void initParts(){
        /**
         * JComboBox:
         */
        comboBox = new JComboBox();
        comboBox.addItem("Hosenlab 2");
        /**
         * JTextArea & ScrollPane:
         */
        textArea = new JTextArea();
        textArea.setEditable(false);
        caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        /**
         * JButton Launch:
         */
        button = new JButton("Launch Game");
        button.addActionListener(e -> {
            if(Main.username != null && Main.password != null){
                UpdateChecker.checkForUpdate(Main.username, Main.password);
            } else {
                JFrame frame = new JFrame("Username and Password");
                JTextField textField = new JTextField();
                JPasswordField textField1 = new JPasswordField();
                JButton okay = new JButton("OK");
                Container c = frame.getContentPane();
                c.setLayout(new BorderLayout());
                c.add(textField, BorderLayout.NORTH);
                c.add(textField1, BorderLayout.CENTER);
                c.add(okay, BorderLayout.SOUTH);
                frame.setSize(new Dimension(400, 105));
                frame.setVisible(true);
                frame.setResizable(false);
                frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                okay.addActionListener(e1 -> {
                    frame.dispose();
                    Main.username = textField.getText();
                    Main.password = new String(textField1.getPassword());
                    UpdateChecker.checkForUpdate(Main.username, Main.password);
                });
            }

        });
        /**
         * Container:
         */
        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(comboBox, BorderLayout.BEFORE_FIRST_LINE);
        container.add(new JScrollPane(textArea), BorderLayout.CENTER);
        container.add(button, BorderLayout.AFTER_LAST_LINE);
    }


    public void setInvisible(){
        setVisible(false);
    }
    public void setVisible(){
        setVisible(true);
    }
    public void addToTextArea(String string){
        textArea.append(string + "\n");
        update();
    }
    public void update(){
        textArea.update(textArea.getGraphics());
        caret.paint(textArea.getGraphics());
        container.update(container.getGraphics());
        revalidate();
        repaint();
    }
    
}
