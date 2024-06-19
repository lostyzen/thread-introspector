package org.pba;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadIntrospector {

    public static void displayThreadInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        int runnableCount = 0;
        int blockedCount = 0;
        int waitingCount = 0;
        int timedWaitingCount = 0;
        int newCount = 0;
        int terminatedCount = 0;

        for (ThreadInfo threadInfo : threadInfos) {
            switch (threadInfo.getThreadState()) {
                case RUNNABLE:
                    runnableCount++;
                    break;
                case BLOCKED:
                    blockedCount++;
                    break;
                case WAITING:
                    waitingCount++;
                    break;
                case TIMED_WAITING:
                    timedWaitingCount++;
                    break;
                case NEW:
                    newCount++;
                    break;
                case TERMINATED:
                    terminatedCount++;
                    break;
            }
        }

        System.out.printf("RUNNABLE: %d, BLOCKED: %d, WAITING: %d, TIMED_WAITING: %d, NEW: %d, TERMINATED: %d%n",
                runnableCount, blockedCount, waitingCount, timedWaitingCount, newCount, terminatedCount);
    }

    public static void killThread(long threadId) {
        try {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);

            if (threadInfo != null && threadInfo.getThreadState() == Thread.State.BLOCKED) {
                // This is just a simulation of killing a thread. In reality, you can't directly kill a thread.
                System.out.println("Killing thread " + threadId + " (simulation)");
                // Add your thread killing logic here if applicable
            } else {
                System.out.println("Thread " + threadId + " is not in a BLOCKED state or doesn't exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
