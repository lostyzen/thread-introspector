package org.pba;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Options options = new Options();

        Option pidOption = new Option("p", "pid", true, "Process ID of the target JVM");
        pidOption.setRequired(true);
        options.addOption(pidOption);

        Option intervalOption = new Option("i", "interval", true, "Interval in seconds to display thread info");
        intervalOption.setRequired(true);
        options.addOption(intervalOption);

        Option logLevelOption = new Option("l", "logLevel", true, "Log level (DEBUG, INFO, WARN, ERROR)");
        logLevelOption.setRequired(false);
        options.addOption(logLevelOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            logger.error("Failed to parse command line options", e);
            formatter.printHelp("thread-introspector", options);
            System.exit(1);
            return;
        }

        String pid = cmd.getOptionValue("pid");
        int interval = Integer.parseInt(cmd.getOptionValue("interval"));
        String logLevel = cmd.getOptionValue("logLevel", "DEBUG");

        // Set log level
        setLogLevel(logLevel);

        try {
            JVMConnector.attachToJVM(pid);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        ThreadIntrospector.displayThreadInfo();
                    } catch (Exception e) {
                        logger.error("Failed to display thread info", e);
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
                    logger.error("Invalid thread ID", e);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to attach to JVM", e);
        }
    }

    private static void setLogLevel(String logLevel) {
        ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        switch (logLevel.toUpperCase()) {
            case "DEBUG":
                rootLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);
                break;
            case "INFO":
                rootLogger.setLevel(ch.qos.logback.classic.Level.INFO);
                break;
            case "WARN":
                rootLogger.setLevel(ch.qos.logback.classic.Level.WARN);
                break;
            case "ERROR":
                rootLogger.setLevel(ch.qos.logback.classic.Level.ERROR);
                break;
            default:
                rootLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);
                break;
        }
    }
}