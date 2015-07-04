package jorg.calendar.exports;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.tubs.cs.isf.spl.jorg.App.app;

/**
 * @author rose
 */
public abstract class Exporter {

    protected final String name, ext;

    protected Exporter(final String name) {
        this.name = name;
        this.ext = name;
    }

    public String name() {
        return name;
    }

    protected abstract String format();

    public void toFile() {
        final String path = app().currentUser().toString() + "_calendar." + ext;
        try {
            Files.write(Paths.get(path), format().getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (final IOException ex) {
            Logger.getLogger(PlainExporter.class.getName()).log(Level.SEVERE, "Couldn't write into '" + path + "'",
                    ex);
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Exporter) {
            final Exporter other = (Exporter) obj;
            return name.equals(other.name);
        }
        return false;
    }
}
