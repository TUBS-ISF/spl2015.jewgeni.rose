package de.tubs.cs.isf.spl.jorg.calendar;

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
public final class MeetingUtils {

    public static String read(final String path) {
        String str = null;
        try {
            str = new String(Files.readAllBytes(Paths.get(path)));
        } catch (final IOException ex) {
            Logger.getLogger(MeetingUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Couldn't read data from '" + path + "'.");
        }
        return str;
    }

    public static void write(final String data, final String path) {
        try {
            Files.write(Paths.get(path), data.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException ex) {
            Logger.getLogger(MeetingUtils.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Couldn't write data to '" + path + "'.");
        }
    }
}
