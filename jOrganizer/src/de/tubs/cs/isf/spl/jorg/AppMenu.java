package de.tubs.cs.isf.spl.jorg;

import de.tubs.cs.isf.spl.jorg.app_features.Quit;
import static de.tubs.cs.isf.spl.jorg.App.*;
import de.tubs.cs.isf.spl.jorg.calendar.Calendar;
import de.tubs.cs.isf.spl.jorg.app_features.AlarmFactory;
import de.tubs.cs.isf.spl.jorg.app_features.Calculator;
import de.tubs.cs.isf.spl.jorg.app_features.Clock;
import de.tubs.cs.isf.spl.jorg.app_features.Notes;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author rose
 */
public class AppMenu {

    private final List<Feature> features;
    private final String menuString;

    public AppMenu(final Properties props) {
        features = new ArrayList<Feature>();
        for (final String key : props.stringPropertyNames()) {
            if (FEATURE_ALARM.equals(key)) {
                features.add(new AlarmFactory(key, props.getProperty(key)));
            } else if (FEATURE_CALCULATOR.equals(key)) {
                features.add(new Calculator(key, props.getProperty(key)));
            } else if (FEATURE_CLOCK.equals(key)) {
                features.add(new Clock(key, props.getProperty(key)));
            } else if (FEATURE_NOTES.equals(key)) {
                features.add(new Notes(key, props.getProperty(key)));
            } else if (FEATURE_MULTI_USER.equals(key)) {
                features.add(new UserSystem(key, props.getProperty(key)));
            } else if (FEATURE_QUIT.equals(key)) {
                features.add(new Quit(key, props.getProperty(key)));
            } else if (FEATURE_CALENDAR.equals(key)) {
                features.add(new Calendar(key, props.getProperty(key)));
            }
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("main menu:\n");
        for (final Feature f : features) {
            String keyStr = String.format("%10s - ", "[" + f.menuKey() + "]");
            sb.append(keyStr).append(f.description()).append("\n");
        }
        menuString = sb.toString();
    }

    public void choose(final String key) {
        for (final Feature feature : features) {
            if (feature.menuKey().equals(key)) {
                feature.action();
                return;
            }
        }
        app().printErr("Invalid option!");
    }

    @Override
    public String toString() {
        return menuString;
    }
}
