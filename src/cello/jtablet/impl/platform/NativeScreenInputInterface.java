package cello.jtablet.impl.platform;

import cello.jtablet.impl.ScreenInputInterface;
import jpen.provider.NativeLibraryLoader;
import jpen.provider.NativeLibraryLoader.LoadException;

/**
 * @author marcello
 *
 */
public abstract class NativeScreenInputInterface extends ScreenInputInterface implements NativeCursorDevice {

	private static final NativeLibraryLoader LIB_LOADER=new NativeLibraryLoader();
	private boolean loaded = false;
	
	public void load() throws NativeDeviceException {
		try {
			LIB_LOADER.load();
			loaded = true;
		} catch (LoadException ex) {
			throw new NativeDeviceException(ex);
		}
	}
	public boolean overridesMouseListener() {
		return false;
	}
	@Override
	public boolean isDeviceAvailable() {
		return loaded;
	}
}