package jorg;

import jorg.app_features.Clock;

/**
 * @author rose
 */
public final class App implements Runnable {

	private void init() {
		original();
		addFeature(new Clock());
	}
}
