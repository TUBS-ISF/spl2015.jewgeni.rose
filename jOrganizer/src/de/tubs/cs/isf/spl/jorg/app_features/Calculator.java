package de.tubs.cs.isf.spl.jorg.app_features;

import de.tubs.cs.isf.spl.jorg.Feature;
import org.python.core.PyObject;
import org.python.util.InteractiveInterpreter;
import org.python.util.PythonInterpreter;

/**
 *
 * @author rose
 */
public class Calculator extends Feature {

    private final PythonInterpreter interpreter;

    public Calculator(final String key) {
        this(key, key);
    }

    public Calculator(final String key, final String desc) {
        super(key, desc);
        interpreter = new InteractiveInterpreter();
    }

    @Override
    public void action() {
        String input;
        PyObject result;
        while (true) {
            input = readLine();
            if ("q".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
                interpreter.cleanup();
                break;
            } else if (input.matches("^([-+/*]\\d+(\\.\\d+)?)*")) {
                printErr("Illegal expression!");
            } else {
                result = interpreter.eval(input);
                println(result);
            }
        }
    }
}
