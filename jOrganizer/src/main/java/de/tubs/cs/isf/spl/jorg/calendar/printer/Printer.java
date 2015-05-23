package de.tubs.cs.isf.spl.jorg.calendar.printer;

import static de.tubs.cs.isf.spl.jorg.App.app;
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
public abstract class Printer {

    protected final String name, ext;

    protected Printer(final String name) {
        this.name = name;
        this.ext = name;
    }

    public String name() {
        return name;
    }

    public String ext() {
        return ext;
    }

    public abstract String print();

    public void toFile() {
        final String path = app().currentUser().toString() + "_calendar." + ext;
        try {
            Files.write(Paths.get(path), print().getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (final IOException ex) {
            Logger.getLogger(PlainPrinter.class.getName()).log(Level.SEVERE, "Couldn't write into '" + path + "'",
                                                               ex);
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Printer) {
            final Printer other = (Printer) obj;
            return name.equals(other.name);
        }
        return false;
    }
}
