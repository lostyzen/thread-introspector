package org.pba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadIntrospector {

    private static final Logger logger = LoggerFactory.getLogger(ThreadIntrospector.class);

    public static void displayThreadInfo(MBeanServerConnection mbsc) throws IOException {
        // Get the ThreadMXBean
        logger.info("Retrieving ThreadMXBean...");
        ThreadMXBean threadMXBean = ManagementFactory.newPlatformMXBeanProxy(
                mbsc, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
        logger.info("ThreadMXBean retrieved successfully.");

        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);

        int runnableCount = 0;
        int blockedCount = 0;
        int waitingCount = 0;
        int timedWaitingCount = 0;
        int newCount = 0;
        int terminatedCount = 0;

        logger.info("Thread information summary:");
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

            // Log thread CPU time and user time
            long threadId = threadInfo.getThreadId();
            String threadName = threadInfo.getThreadName();
            long cpuTime = threadMXBean.getThreadCpuTime(threadId);
            long userTime = threadMXBean.getThreadUserTime(threadId);
            logger.debug("Thread ID: {}, Name: {}, State: {}, CPU time: {} ns, User time: {} ns",
                    threadId, threadName, threadInfo.getThreadState(), cpuTime, userTime);
        }

        logger.info("RUNNABLE: {}, BLOCKED: {}, WAITING: {}, TIMED_WAITING: {}, NEW: {}, TERMINATED: {}",
                runnableCount, blockedCount, waitingCount, timedWaitingCount, newCount, terminatedCount);
    }

    public static void killThread(long threadId) {
        try {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);

            if (threadInfo != null && threadInfo.getThreadState() == Thread.State.BLOCKED) {
                // This is just a simulation of killing a thread. In reality, you can't directly kill a thread.
                logger.info("Killing thread {} (simulation)", threadId);
                // Add your thread killing logic here if applicable
            } else {
                logger.warn("Thread {} is not in a BLOCKED state or doesn't exist.", threadId);
            }
        } catch (Exception e) {
            logger.error("Failed to kill thread {}", threadId, e);
        }
    }
}