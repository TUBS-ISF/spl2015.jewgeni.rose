package de.tubs.cs.isf.spl.jorg.soft_features;

import java.util.Scanner;

import org.python.core.PyObject;
import org.python.util.InteractiveInterpreter;
import org.python.util.PythonInterpreter;

import de.tubs.cs.isf.spl.jorg.Feature;

/**
 *
 * @author rose
 */
public class Calc extends Feature {

	private final PythonInterpreter interpreter;

	public Calc() {
		interpreter = new InteractiveInterpreter();
	}

	@Override
    public void action() {
        Scanner sc = null;
        try {
        	sc = new Scanner(System.in);
        	String input;
            PyObject result;
            while (true) {
                System.out.print("calc> ");
                input = sc.nextLine();
                if ("q".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
                    interpreter.cleanup();
                    break;
                } else if (!input.matches("[0-9\\+\\-*(),\\.%^!]")) {
                    System.out.println("Not a statement!");
                    continue;
                }
                result = interpreter.eval(input);
                System.out.println(result);
            }
		} finally {
			if (sc != null)
				sc.close();
		}
    }

	@Override
	public String menuKey() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String title() {
		return "Calculator";
	}
}
