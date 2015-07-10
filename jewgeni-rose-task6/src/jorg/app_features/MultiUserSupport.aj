package jorg.app_features;

import jorg.App;
import jorg.UserMenu;

public aspect MultiUserSupport {
	pointcut users(App app): target(app) && execution(void App.init());

	after(App app): users(app) {
		final UserMenu sys = new UserMenu(app.userSystem, app.features);
		app.userSystem = sys;
		app.addFeature(sys);
	}
}
