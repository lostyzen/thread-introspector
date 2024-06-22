package org.pba;

import com.sun.tools.attach.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JVMConnector {

    private static final Logger logger = LoggerFactory.getLogger(JVMConnector.class);

    public static VirtualMachine attachToJVM(String pid) throws IOException {
        try {
            // Attach to the target JVM
            logger.info("Attaching to JVM with PID: {}", pid);
            VirtualMachine vm = VirtualMachine.attach(pid);
            logger.info("Successfully attached to JVM with PID {}", pid);
            return vm;
        } catch (Exception e) {
            logger.error("Failed to attach to JVM with PID {}", pid, e);
            throw new IOException("Failed to attach to JVM", e);
        }
    }

    public static void detachFromJVM (VirtualMachine vm) throws IOException {
        try {
            vm.detach();
        } catch (IOException e) {
            logger.error("Failed dettach from JVM");
            throw new IOException("Failed dettach from JVM", e);
        }
    }
}