package jorg;

import jorg.app_features.AlarmMenu;

/**
 * @author rose
 */
public final class App implements Runnable {

	private void init() {
		original();
		addFeature(new AlarmMenu());
	}
}
