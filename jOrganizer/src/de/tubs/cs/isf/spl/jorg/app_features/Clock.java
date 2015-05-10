package de.tubs.cs.isf.spl.jorg.app_features;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author rose
 */
public class Clock extends Feature {

    public Clock(final String key) {
        this(key, key);
    }

    public Clock(final String key, final String desc) {
        super(key, desc);
    }

    @Override
    public void action() {
        App.app().println("Today is " + currentDate() + " and it's " + currentTime() + " o'clock");
    }

    private String currentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private String currentTime() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_TIME).substring(0, 5);
    }
}
