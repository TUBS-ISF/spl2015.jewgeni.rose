package de.tubs.cs.isf.spl.jorg.soft_features;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author rose
 */
public class DateTime {

    public String currentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String currentTime() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
