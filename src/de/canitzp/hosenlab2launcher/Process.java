package de.canitzp.hosenlab2launcher;

import java.io.File;
import java.io.IOException;

public class Process extends Thread {

    public Process(boolean start){
        if(start){
            this.setName("Process");
            this.setDaemon(true);
            this.start();
        }
    }

    public void runProcess(String command, String dir) throws IOException{
            System.gc();
            java.lang.Process pro = Runtime.getRuntime().exec(command, null, new File(dir));
            System.out.println(command);
    }

}
