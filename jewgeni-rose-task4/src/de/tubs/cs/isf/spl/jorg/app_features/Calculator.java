package de.tubs.cs.isf.spl.jorg.app_features;

import groovy.lang.GroovyShell;
import de.tubs.cs.isf.spl.jorg.Feature;

/**
 *
 * @author rose
 */
public class Calculator extends Feature {

	private static final String FEATURE_KEY = "calc";
	private static final String FEATURE_DESC = "start basic calculator";
    private final GroovyShell shell;

	public Calculator() {
		super(FEATURE_KEY, FEATURE_DESC);
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
