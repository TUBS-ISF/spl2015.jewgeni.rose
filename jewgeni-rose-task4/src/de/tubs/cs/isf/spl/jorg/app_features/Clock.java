package de.tubs.cs.isf.spl.jorg.app_features;

import java.time.LocalDateTime;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;

/**
 *
 * @author rose
 */
public class Clock extends Feature {

	private static final String FEATURE_KEY = "clock";
	private static final String FEATURE_DESC = "show date and time";

	public Clock() {
		super(FEATURE_KEY, FEATURE_DESC);
    }

    @Override
    public void action() {
        App.print(String.format("%1$te. %1$tB %1$tY, %1$tR o'clock", LocalDateTime.now()));
    }
}
