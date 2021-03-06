package cello.jtablet.installer.platforms;

import cello.jtablet.installer.JTabletInstaller;


/**
 * @author marcello
 *
 */
public class MacOSXInstaller extends OSInstaller {

	public MacOSXInstaller(JTabletInstaller installer) {
		super(installer);
	}

	@Override
	public boolean isCompatible() {
		return System.getProperty("os.name").toLowerCase().startsWith("mac os x");
	}

	@Override
	public void install() {
		
		String javaVersion = System.getProperty("java.version");
		String javaVendor = System.getProperty("java.vendor");
		addLogMessage("Detected Java "+javaVersion+" ("+javaVendor+")");

	}

}
