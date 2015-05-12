package de.tubs.cs.isf.spl.jorg;

import de.tubs.cs.isf.spl.jorg.calendar.Calendar;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
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
    static final String FEATURE_QUIT = "quit";
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

    private final AppMenu menu;
    private final Scanner reader;

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
        menu = new AppMenu(p);
    }

    public User currentUser() {
        if (menu == null) {
            return null;
        }
        return menu.currentUser();
    }

    public Calendar calendar() {
        return currentUser().getCalendar();
    }

    public void run() {
        menu.init();
        String input;
        while (true) {
            println(PROMPT_CLEAR);
            println(menu);
            input = readLine();
            menu.choose(input);
        }
    }

    private String prompt(final String subFeature) {
        final String feat;
        if (subFeature != null) {
            feat = "@jOrg/" + subFeature + "> ";
        } else {
            feat = "@jOrg> ";
        }
        return PROMPT_BOLD_BLUE + currentUser() + PROMPT_BOLD_WHITE + feat + PROMPT_NORMAL;
    }

    public App println(final Object obj, final String key) {
        return print(obj.toString() + "\n", key);
    }

    public App print(final Object obj, final String key) {
        if (obj == null || obj.toString().isEmpty()) {
            System.out.print(prompt(key));
        } else {
            for (final String str : Arrays.asList(obj.toString().split("\n"))) {
                System.out.print(prompt(key));
                System.out.println(str);
            }
        }
        return this;
    }

    public App printErr(final Object obj, final String key) {
        if (obj == null) {
            System.out.println(prompt(key));
        } else {
            for (final String str : Arrays.asList(obj.toString().split("\n"))) {
                System.out.print(prompt(key));
                System.out.println(PROMPT_ERROR + str + PROMPT_NORMAL);
            }
        }
        return this;
    }

    public App printErr(final Object obj) {
        return printErr(obj, null);
    }

    public App println(final Object obj) {
        return println(obj, null);
    }

    public App println() {
        return println("", null);
    }

    public App print(final Object obj) {
        return print(obj, null);
    }

    public String readLine() {
        return reader.nextLine();
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
