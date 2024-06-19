package org.pba;

import com.sun.tools.attach.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JVMConnector {

    private static final Logger logger = LoggerFactory.getLogger(JVMConnector.class);

    public static void attachToJVM(String pid) throws IOException {
        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            try {
                vm.loadAgentLibrary("management-agent");
            } catch (Exception e) {
                vm.loadAgentLibrary("jmx.agent");
            }
            vm.detach();
            logger.info("Successfully attached to JVM with PID {}", pid);
        } catch (Exception e) {
            logger.error("Failed to attach to JVM with PID {}", pid, e);
            throw new IOException("Failed to attach to JVM", e);
        }
    }
}