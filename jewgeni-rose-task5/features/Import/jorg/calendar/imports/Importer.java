package jorg.calendar.imports;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rose
 * @date 24.05.15.
 */
public abstract class Importer {

    protected final String name, ext;

    protected Importer(final String name) {
        this.name = name;
        this.ext = name;
    }

    public String name() {
        return name;
    }

    protected abstract void writeIntoCal(final List<String> data);

    public void loadFromFile(final String path) {
        try {
            if (path.endsWith(ext)) {
                writeIntoCal(Files.readAllLines(Paths.get(path)));
            }
        } catch (final IOException ex) {
            Logger.getLogger(PlainExporter.class.getName()).log(Level.SEVERE, "Couldn't read from '" + path + "'", ex);
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Importer) {
            final Importer other = (Importer) obj;
            return name.equals(other.name);
        }
        return false;
    }
}
