/*
 * Created by JFormDesigner on Sun Sep 20 21:25:56 CEST 2015
 */

package de.canitzp.hosenlab2launcher;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

/**
 * @author canitzp
 */
public class Settings extends JFrame {

    private static JTextField usernameField, maxRamField, javaField, minRamField;
    private static JPasswordField passwordField;
    private static JButton button;
    private static JCheckBox debug;

    public Settings(){
        usernameField = new JTextField(20);
        maxRamField = new JTextField(5);
        passwordField = new JPasswordField(20);
        javaField = new JTextField(20);
        minRamField = new JTextField(20);
        debug = new JCheckBox();
        button = new JButton("Save");

        usernameField.setText(Main.username);
        passwordField.setText(Main.password);
        maxRamField.setText(Integer.toString(Main.maxRam));
        minRamField.setText(Integer.toString(Main.minRam));
        javaField.setText(Main.java);
        button.addActionListener(e -> {
            try {
                if(!Objects.equals(usernameField.getText(), "")){
                    Main.username = usernameField.getText();
                } else return;
                if(!Objects.equals(new String(passwordField.getPassword()), "")){
                    Main.password = new String(passwordField.getPassword());
                } else return;
                if(maxRamField.getText() != null){
                    Main.maxRam = Integer.parseInt(maxRamField.getText());
                } else return;
                if(minRamField.getText() != null){
                    Main.minRam = Integer.parseInt(minRamField.getText());
                } else return;
                if(!Objects.equals(javaField.getText(), "")){
                    Main.java = javaField.getText();
                } else return;
            } catch (NumberFormatException nfe) {
                maxRamField.setText(Integer.toString(Main.maxRam));
                nfe.printStackTrace();
                return;
            }
            System.gc();
            this.dispose();
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        setResizable(false);
        setTitle("Settings");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new FormPane());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public class FormPane extends JPanel{
        public FormPane() {
            setBorder(new EmptyBorder(8, 8, 8, 8));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;

            NamePane namePane = new NamePane();
            namePane.setBorder(new CompoundBorder(new TitledBorder("Account"), new EmptyBorder(4, 4, 4, 4)));
            add(namePane, gbc);
            gbc.gridy++;
            JavaSettings javaSettings = new JavaSettings();
            javaSettings.setBorder(new CompoundBorder(new TitledBorder("Java Settings"), new EmptyBorder(4, 4, 4, 4)));
            add(javaSettings, gbc);
            gbc.gridy++;
            add(button, gbc);
        }

    }
    public class NamePane extends JPanel {

        public NamePane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(new JLabel("Username: "), gbc);
            gbc.gridy++;
            gbc.gridx = 0;
            add(new JLabel("Password: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0.5;
            add(usernameField, gbc);
            gbc.gridy++;
            add(passwordField, gbc);
        }
    }

    public class JavaSettings extends JPanel{

        public JavaSettings(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(new JLabel("Java (Default:'java'): "), gbc);
            gbc.gridy++;
            add(new JLabel("Max-Ram (-Xmx): "), gbc);
            gbc.gridy++;
            add(new JLabel("Min-Ram (-Xmn): "), gbc);
            gbc.gridy++;
            add(new JLabel("Java-Debug Mode: "), gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0.5;
            add(javaField, gbc);
            gbc.gridy++;
            add(maxRamField, gbc);
            gbc.gridy++;
            add(minRamField, gbc);
            gbc.gridy++;
            gbc.fill = GridBagConstraints.NONE;
            add(debug, gbc);
        }
    }

}
