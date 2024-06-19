package org.pba;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class JVMConnectorTest {

    @Test
    public void testAttachToJVM() {
        String fakePid = "1234";
        try {
            JVMConnector.attachToJVM(fakePid);
            fail("Expected IOException");
        } catch (IOException e) {
            assertTrue(e.getMessage().contains("Failed to attach to JVM"));
        }
    }
}
