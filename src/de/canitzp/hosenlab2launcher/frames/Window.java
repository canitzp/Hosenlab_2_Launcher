package de.canitzp.hosenlab2launcher.frames;

import de.canitzp.hosenlab2launcher.Main;
import de.canitzp.hosenlab2launcher.Modpacks;
import de.canitzp.hosenlab2launcher.UpdateChecker;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Window extends JFrame{
    
    private int width, height;
    private String title;
    private JComboBox comboBox;
    private JTextArea textArea;
    private JButton button, settings;
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
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible();
    }

    private void initParts(){
        /**
         * JComboBox:
         */
        comboBox = new JComboBox();
        comboBox.addItem(Modpacks.HOSENLAB2.getDisplayName());
        comboBox.addItem(Modpacks.TECHNICUNIVERSE.getDisplayName());
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
            if (Main.username != null && Main.password != null) {
                UpdateChecker.checkForUpdate((String) comboBox.getSelectedItem());
            } else {
                new Settings();
                UpdateChecker.checkForUpdate((String) comboBox.getSelectedItem());
            }

        });
        /**
         * Settings Button:
         */
        settings = new JButton("Settings");
        settings.addActionListener(e -> {
            new Settings();
        });
        /**
         * Exit-Button:
         */
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> {
            Main.stopAndSave();
        });
        /**
         * Center-Panel:
         */
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel.setLayout(new BorderLayout());
        panel1.setLayout(new BorderLayout());
        panel2.setLayout(new BorderLayout());
        panel2.add(button, BorderLayout.CENTER);
        panel2.add(settings, BorderLayout.SOUTH);
        panel1.add(panel2, BorderLayout.CENTER);
        panel1.add(exit, BorderLayout.SOUTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(panel1, BorderLayout.SOUTH);
        /**
         * Container:
         */
        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(comboBox, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);
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
    public void clear(){
        textArea.setText("===== Hosenlab 2 Launcher-Log =====\n");
    }
    
}
