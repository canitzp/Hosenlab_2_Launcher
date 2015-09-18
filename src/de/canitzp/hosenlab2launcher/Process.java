package de.canitzp.hosenlab2launcher;

public class Process extends Thread {

    public Process(boolean start){
        if(start){
            this.setName("Process");
            this.setDaemon(true);
            this.start();
        }
    }

    public void runProcess(String command){
        try {
            java.lang.Process pro = Runtime.getRuntime().exec(command);
            de.canitzp.console.Main.setProcess(pro);
            System.out.println(command);
        } catch (Throwable t) {
            System.out.println(command);
            t.printStackTrace();
        }
    }

}
