package it.infinity.application;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Memory {
	private static final String[] ATTRIBUTES = { "FreePhysicalMemorySize", "TotalPhysicalMemorySize" };

	private Thread thread;
	private Map<String, String> attributes;

	public Memory() {
		thread = new Thread(() -> run());
		attributes = new HashMap<>();
		for ( String attribute : ATTRIBUTES )
			attributes.put(attribute, "");
	}

	public void start() {
		thread.start();
	}

	public void stop() {
		thread.interrupt();
	}

	private void run() {
		while (true) {
			try {
				OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
				MBeanServer server = ManagementFactory.getPlatformMBeanServer();
				ObjectName objectName = operatingSystem.getObjectName();
				
				List<Attribute> currentAttributes = server.getAttributes(objectName, ATTRIBUTES).asList();
				for (Attribute currentAttribute : currentAttributes) {
					String attributeName = currentAttribute.getName();

					String previousValue = attributes.get(attributeName);
					String currentValue = currentAttribute.getValue().toString();

					if (!previousValue.equals(currentValue)) {
						attributes.put(attributeName, currentValue);
						System.out.println(attributeName + ": " + currentValue);
					}
				}

				Thread.sleep(1000);
			} catch (Exception exception) { }
		}
	}

}
