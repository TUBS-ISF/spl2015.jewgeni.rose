package jorg.app_features;

import jorg.App;
import jorg.BasicFeature;
import java.time.LocalDateTime;

/**
 *
 * @author rose
 */
public class Clock extends BasicFeature {

    public Clock() {
        super("clock", "show date and time");
    }

    @Override
    public void action() {
        App.print(String.format("%1$te. %1$tB %1$tY, %1$tR o'clock", LocalDateTime.now()));
    }
}
