package de.tubs.cs.isf.spl.jorg.calendar.printer;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import de.tubs.cs.isf.spl.jorg.calendar.meeting.Meeting;

/**
 *
 * @author rose
 */
public class MarkdownPrinter extends Printer {

    private PrintStream writer;

    public MarkdownPrinter() {
        writer = System.out;
    }

    @Override
	public void print(final Meeting m, final String pathToFile) {
        if (pathToFile != null) {
            try {
                writer = new PrintStream(pathToFile);
            } catch (final FileNotFoundException ex) {
                System.err.println("Couldn't find the file '" + pathToFile + "'");
            }
        }
    }
}
