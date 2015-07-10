package jorg.app_features;

import jorg.App;
import jorg.BasicFeature;
import java.time.LocalDateTime;

public aspect Clock extends BasicFeature {
	pointcut clock(App app): target(app) && execution(void App.init());

	after(App app): clock(app) {
		app.addFeature(this);
	}

	public Clock() {
		super("clock", "show date and time");
	}

	@Override
	public void action() {
		App.print(String.format("%1$te. %1$tB %1$tY, %1$tR o'clock", LocalDateTime.now()));
	}
}
