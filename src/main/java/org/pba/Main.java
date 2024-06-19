package org.pba;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar thread-introspector.jar <pid> <interval-in-seconds>");
            System.exit(1);
        }

        String pid = args[0];
        int interval = Integer.parseInt(args[1]);

        try {
            JVMConnector.attachToJVM(pid);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        ThreadIntrospector.displayThreadInfo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, interval * 1000);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter thread ID to kill or 'exit' to quit:");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    timer.cancel();
                    break;
                }
                try {
                    long threadId = Long.parseLong(input);
                    ThreadIntrospector.killThread(threadId);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid thread ID");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
