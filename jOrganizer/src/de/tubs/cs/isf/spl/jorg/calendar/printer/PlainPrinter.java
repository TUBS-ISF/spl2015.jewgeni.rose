package de.tubs.cs.isf.spl.jorg.calendar.printer;

import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.calendar.Meeting;
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
    public void print(final Meeting m, final String path) {
        if (path == null) {
            app().println(format(m));
        } else {
            try {
                Files.write(Paths.get(path), format(m).getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (final IOException ex) {
                Logger.getLogger(PlainPrinter.class.getName()).log(Level.SEVERE, "Couldn't write into '" + path + "'",
                                                                   ex);
            }
        }
    }

    protected String format(final Meeting m) {

        return null;
    }
}
