package de.tubs.cs.isf.spl.jorg;

import de.tubs.cs.isf.spl.jorg.soft_features.Alarm;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author rose
 */
public class App {

    public static final Clock CLOCK = Clock.systemUTC();
    public static final Properties CONFIG = new Properties();

    private final Set<User> users;
    private final Menu menu;

    public App() {
        users = new HashSet<User>();
        menu = new Menu();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        Alarm alarm = new Alarm(LocalDateTime.now().plus(10, ChronoUnit.SECONDS));
        alarm.run();

    }

}
