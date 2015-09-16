package de.canitzp.hosenlab2launcher;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    
    private int width, height;
    private String title;
    private JComboBox comboBox;
    private JTextArea textArea;
    private JButton button;
    
    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
        initWindow();
    }

    private void initWindow(){
        initParts();
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
         * JTextArea:
         */
        textArea = new JTextArea();
        textArea.setEditable(false);
        /**
         * JButton Launch:
         */
        button = new JButton("Launch Game");
        button.addActionListener(e -> {
            UpdateChecker.updatePack();
        });
        /**
         * Container:
         */
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(comboBox, BorderLayout.BEFORE_FIRST_LINE);
        container.add(textArea, BorderLayout.CENTER);
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
    }
    
}
