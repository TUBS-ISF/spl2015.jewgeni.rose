// #condition CALCULATOR
//@package de.tubs.cs.isf.spl.jorg.app_features;
//@
//@import groovy.lang.GroovyShell;
//@import de.tubs.cs.isf.spl.jorg.Feature;
//@
//@/**
//@ *
//@ * @author rose
//@ */
//@public class Calculator extends Feature {
//@
//@	private final GroovyShell shell;
//@
//@	public Calculator(final String key) {
//@		this(key, key);
//@	}
//@
//@	public Calculator(final String key, final String desc) {
//@		super(key, desc);
//@		shell = new GroovyShell();
//@	}
//@
//@	@Override
//@	public void action() {
//@		String input;
//@		Object result;
//@		while (true) {
//@			input = readLine();
//@			if ("exit".equals(input)) {
//@				break;
//@			} else if (input.matches("^([-+/*]\\d+(\\.\\d+)?)*")) {
//@				printErr("Illegal expression!");
//@			} else {
//@				try {
//@					result = shell.evaluate(input);
//@					println(result);
//@				} catch (Exception ex) {
//@					printErr("Illegal expression!");
//@				}
//@			}
//@		}
//@	}
//@}
