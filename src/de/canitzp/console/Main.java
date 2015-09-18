package de.canitzp.console;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Process;

public class Main {

    public static String launcherPath = de.canitzp.hosenlab2launcher.Main.launcherPath.getAbsolutePath();
    public static Process process;
    private static JTextArea textArea;
    private static JFrame frame;

    public static void main(String[] strings){
        new Main().frame();
        addToConsole("test");
        try {
            while (process.isAlive()){
                process.waitFor();
                InputStream is = process.getInputStream();
                String s = IOUtils.toString(is);
                addToConsole(s);
            }
            process.destroy();
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            frame.dispose();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setProcess(Process p){
        process = p;
    }

    private void frame(){
        frame = new JFrame("Console");
        frame.setSize(new Dimension(800, 400));
        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea));
        frame.setVisible(true);
    }

    private static void addToConsole(String s){
        textArea.append(s + "\n");
    }

}
