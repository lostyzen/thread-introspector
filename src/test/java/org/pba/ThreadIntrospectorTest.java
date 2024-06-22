package org.pba;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.management.MBeanServerConnection;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import static org.junit.Assert.assertTrue;

public class ThreadIntrospectorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    /*
    @Test
    public void testDisplayThreadInfo() {
        ThreadIntrospector.displayThreadInfo(MBeanServerConnection mbsc);
        String output = outContent.toString();
        assertTrue(output.contains("RUNNABLE:"));
        assertTrue(output.contains("BLOCKED:"));
        assertTrue(output.contains("WAITING:"));
        assertTrue(output.contains("TIMED_WAITING:"));
        assertTrue(output.contains("NEW:"));
        assertTrue(output.contains("TERMINATED:"));
    }
    */

    @Test
    public void testKillThread() {
        // Create a blocked thread for testing
        Thread blockedThread = new Thread(() -> {
            synchronized (this) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        blockedThread.start();

        // Ensure the thread is blocked
        synchronized (this) {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            long blockedThreadId = blockedThread.getId();

            // Simulate killing the blocked thread
            ThreadIntrospector.killThread(blockedThreadId);
            String output = outContent.toString();
            assertTrue(output.contains("Killing thread " + blockedThreadId + " (simulation)"));

            // Cleanup
            blockedThread.interrupt();
        }
    }
}
