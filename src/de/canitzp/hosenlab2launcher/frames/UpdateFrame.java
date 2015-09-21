package de.canitzp.hosenlab2launcher.frames;

import de.canitzp.hosenlab2launcher.Modpacks;
import de.canitzp.hosenlab2launcher.Update;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowEvent;

public class UpdateFrame extends JFrame{

    public static JTextArea textArea;
    public JButton yes, no;

    public UpdateFrame(Modpacks modpack){
        textArea = new JTextArea(5, 20);
        yes = new JButton("Yes");
        no = new JButton("No");

        textArea.append("== Update Manager Log ==");

        setResizable(false);
        setTitle("Update");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new FormPane());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        yes.addActionListener(e -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            dispose();
            new Update().update(modpack);
        });
        no.addActionListener(e -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            dispose(); new Update().launch(modpack);
        });
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

            PackUpdatePane packUpdatePane = new PackUpdatePane();
            packUpdatePane.setBorder(new CompoundBorder(new TitledBorder("Modpack Update"), new EmptyBorder(4, 4, 4, 4)));
            add(packUpdatePane, gbc);
        }

    }
    public class PackUpdatePane extends JPanel{
        public PackUpdatePane(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(new JScrollPane(textArea), gbc);
            gbc.gridy++;
            add(new TwoButtons(), gbc);
        }
    }
    public class TwoButtons extends JPanel{
        public TwoButtons(){
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(yes, gbc);
            gbc.weightx = 1.0d;
            gbc.weighty = 1.0d;
            gbc.gridx ++;
            add(no, gbc);
        }
    }

}
