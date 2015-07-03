package jorg;

import jorg.UserMenu;

/**
 * @author rose
 */
public final class App implements Runnable {

	private void init() {
		original();
		
		final UserMenu sys = new UserMenu(userSystem, features);
		userSystem = sys;
		addFeature(sys);
	}
}
