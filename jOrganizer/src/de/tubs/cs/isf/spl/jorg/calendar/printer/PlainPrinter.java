package de.tubs.cs.isf.spl.jorg.calendar.printer;

import de.tubs.cs.isf.spl.jorg.calendar.Calendar;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rose
 */
public class PlainPrinter extends Printer {

    @Override
    public void print(final Calendar cal, final String path) {
        if (path == null) {
            System.out.println(format(cal));
        } else {
            try {
                Files.write(Paths.get(path), format(cal).getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (final IOException ex) {
                Logger.getLogger(PlainPrinter.class.getName()).log(Level.SEVERE, "Couldn't write into '" + path + "'",
                                                                   ex);
            }
        }
    }

    protected String format(final Calendar cal) {
        // TODO
        return null;
    }
}
