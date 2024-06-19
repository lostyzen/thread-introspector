package org.pba;

import com.sun.tools.attach.VirtualMachine;
import java.io.IOException;

public class JVMConnector {

    public static void attachToJVM(String pid) throws IOException {
        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            try {
                vm.loadAgentLibrary("management-agent");
            } catch (Exception e) {
                vm.loadAgentLibrary("jmx.agent");
            }
            vm.detach();
        } catch (Exception e) {
            throw new IOException("Failed to attach to JVM", e);
        }
    }
}
