package jorg.app_features;

import jorg.BasicFeature;
import groovy.lang.GroovyShell;

/**
 *
 * @author rose
 */
public class Calculator extends BasicFeature {

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
