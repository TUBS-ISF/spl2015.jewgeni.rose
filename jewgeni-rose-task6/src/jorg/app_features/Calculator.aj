package jorg.app_features;

import jorg.App;
import jorg.BasicFeature;
import groovy.lang.GroovyShell;

public aspect Calculator extends BasicFeature {
	
	pointcut calc(App app): target(app) && execution(void App.init());

	after(App app): calc(app) {
		app.addFeature(this);
	}

	private final GroovyShell shell;

	public Calculator() {
		super("calc", "start basic calculator");
		shell = new GroovyShell();
	}

	@Override
	public void action() {
		String input;
		Object result;
		while (true) {
			input = readLine();
			if ("exit".equals(input)) {
				break;
			} else if (input.matches("^([-+/*]\\d+(\\.\\d+)?)*")) {
				printErr("Illegal expression!");
			} else {
				try {
					result = shell.evaluate(input);
					println(result);
				} catch (Exception ex) {
					printErr("Illegal expression!");
				}
			}
		}
	}
}
