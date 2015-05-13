package de.tubs.cs.isf.spl.jorg;

import de.tubs.cs.isf.spl.jorg.app_features.AlarmFactory;
import de.tubs.cs.isf.spl.jorg.app_features.Calculator;
import de.tubs.cs.isf.spl.jorg.app_features.Clock;
import de.tubs.cs.isf.spl.jorg.app_features.Notes;
import de.tubs.cs.isf.spl.jorg.calendar.Calendar;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rose
 */
public class App {

    /*
     * Prompt formatter strings.
     */
    public static final String PROMPT_ERROR = "\033[47m\033[31m";
    public static final String PROMPT_BOLD_BLUE = "\033[1m\033[34m";
    public static final String PROMPT_BOLD_WHITE = "\033[1m\033[37m";
    public static final String PROMPT_WHITE = "\033[37m";
    public static final String PROMPT_BOLD = "\033[1m";
    public static final String PROMPT_NORMAL = "\033[0m";
    public static final String PROMPT_CLEAR = "\0033\0143";

    /*
     * first level features.
     */
    static final String FEATURE_CALENDAR = "calendar";
    static final String FEATURE_QUIT = "exit";
    static final String FEATURE_ALARM = "alarm";
    static final String FEATURE_NOTES = "notes";
    static final String FEATURE_CALCULATOR = "calc";
    static final String FEATURE_CLOCK = "clock";
    static final String FEATURE_MULTI_USER = "users";

    /*
     * Global flags and config.
     */
    public static final Properties CONFIG = new Properties();
    public static final String EXIT = "exit";

    /*
     * Singleton
     */
    private static App INSTANCE = null;

    private final Scanner reader;
    private final List<Feature> features;
    private final Properties properties;
    private String menuString;
    private UserMenu userSystem;

    public App(final String path) {
        Locale.setDefault(Locale.ENGLISH);
        Properties p = new Properties();
        Reader r = null;
        try {
            if (path == null) {
                r = new InputStreamReader(getClass().getClassLoader().getResource("resources/default.properties").
                    openStream());
            } else {
                r = new FileReader(path);
            }
            p.load(r);
            CONFIG.putAll(p);
        } catch (final IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Couldn't find properties at '" + path + "'", ex);
        } finally {
            try {
                if (r != null) {
                    r.close();
                }
            } catch (final IOException ignore) {
            }
        }
        reader = new Scanner(System.in);
        this.properties = p;
        features = new ArrayList<Feature>();
    }

    public void init() {
        // non-optional feature
        final Calendar cal = new Calendar(FEATURE_CALENDAR, properties.getProperty(FEATURE_CALENDAR));
        final String name = readLine("Enter your name: ");
        final User user = new User(name, cal);
        userSystem = new UserMenu(FEATURE_MULTI_USER, FEATURE_MULTI_USER);
        userSystem.init(user);
        features.add(cal);

        for (final String key : properties.stringPropertyNames()) {
            if (FEATURE_ALARM.equals(key)) {
                features.add(new AlarmFactory(key, properties.getProperty(key)));
            } else if (FEATURE_CALCULATOR.equals(key)) {
                features.add(new Calculator(key, properties.getProperty(key)));
            } else if (FEATURE_CLOCK.equals(key)) {
                features.add(new Clock(key, properties.getProperty(key)));
            } else if (FEATURE_NOTES.equals(key)) {
                features.add(new Notes(key, properties.getProperty(key)));
            } else if (FEATURE_MULTI_USER.equals(key)) {
                final UserMenu sys = new UserMenu(key, properties.getProperty(key), userSystem, features);
                userSystem = sys;
                features.add(sys);
            }
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(PROMPT_BOLD + "main menu:\n" + PROMPT_NORMAL);
        sb.append(String.format("%10s - exits the application\n", "[" + EXIT + "]"));
        for (final Feature f : features) {
            String keyStr = String.format("%10s - ", "[" + f.menuKey() + "]");
            sb.append(keyStr).append(f.description()).append("\n");
        }
        menuString = sb.toString();
    }

    public User currentUser() {
        if (userSystem == null) {
            return null;
        }
        return userSystem.current();
    }

    public void choose(final String key) {
        for (final Feature feature : features) {
            if (feature.menuKey().equals(key)) {
                feature.action();
                return;
            }
        }
        printErr("Invalid option!");
    }

    public Calendar calendar() {
        if (currentUser() == null) {
            return null;
        }
        return currentUser().getCalendar();
    }

    public void run() {
        init();
        String input;
        while (true) {
            clear();
            println(menuString);
            input = readLine();
            if (EXIT.equals(input)) {
                System.exit(0);
            }
            choose(input);
            sleep();
        }
    }

    private String prompt(final String subFeature) {
        final String feat;
        if (subFeature != null && !subFeature.isEmpty()) {
            feat = "@jOrg/" + subFeature + "> ";
        } else {
            feat = "@jOrg> ";
        }
        return PROMPT_BOLD_BLUE + currentUser() + PROMPT_BOLD_WHITE + feat + PROMPT_NORMAL;
    }

    private void write(final Object obj, final String key, boolean error) {
        if (obj == null || obj.toString().isEmpty()) {
            System.out.print(prompt(key));
        } else {
            for (final String str : obj.toString().split("\n")) {
                System.out.print(prompt(key));
                if (error) {
                    System.out.println(PROMPT_ERROR + str + PROMPT_NORMAL);
                } else {
                    System.out.println(str);
                }
            }
        }
    }

    private String read(final String prompt, final String key) {
        System.out.print(prompt(key));
        System.out.print(prompt);
        return reader.nextLine();
    }

    public static void print(final Object obj, final String key) {
        app().write(obj, key, false);
    }

    public static void print(final Object obj) {
        print(obj, "");
    }

    public static void println(final Object obj, final String key) {
        print(obj.toString() + "\n", key);
    }

    public static void println(final Object obj) {
        println(obj, "");
    }

    public static void printErr(final Object obj, final String key) {
        app().write(obj.toString(), key, true);
    }

    public static void printErr(final Object obj) {
        printErr(obj, null);
    }

    public static void clear() {
        System.out.println(PROMPT_CLEAR);
    }

    public static String readLine(final String prompt, final String key) {
        return app().read(prompt, key);
    }

    public static String readLine(final String prompt) {
        return readLine(prompt, "");
    }

    public static String readLine() {
        return readLine("");
    }

    public static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static App app() {
        return INSTANCE;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        if (args.length > 1) {
            System.err.println("usage: java -jar jOrganizer <pathToConfigFile>");
            System.exit(0);
        }
        final String config = args.length == 1 ? args[0] : null;
        INSTANCE = new App(config);
        INSTANCE.run();
    }
}
