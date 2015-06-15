package de.tubs.cs.isf.spl.jorg.app_features;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;
import java.time.LocalDateTime;

/**
 *
 * @author rose
 */
public class Clock extends Feature {

    public Clock(final String key, final String desc) {
        super(key, desc);
    }

    @Override
    public void action() {
        App.print(String.format("%1$te. %1$tB %1$tY, %1$tR o'clock", LocalDateTime.now()));
    }
}
