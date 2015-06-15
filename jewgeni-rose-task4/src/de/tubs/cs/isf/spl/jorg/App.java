package de.tubs.cs.isf.spl.jorg;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;

import de.tubs.cs.isf.spl.jorg.calendar.Calendar;

/**
 * @author rose
 */
public final class App {

    /*
     * Prompt formatter strings.
     */
    public static final String PROMPT_ERROR = "\033[47m\033[31m";
    public static final String PROMPT_BOLD_BLUE = "\033[1m\033[34m";
    public static final String PROMPT_BOLD_WHITE = "\033[1m\033[37m";
    public static final String PROMPT_BOLD = "\033[1m";
    public static final String PROMPT_NORMAL = "\033[0m";
    public static final String PROMPT_CLEAR = "\033[H\033[2J";

	private static final String DEFAULT_CONFIG = "default.config";
    public static final String EXIT = "exit";

    /*
     * Singleton
     */
    private static App INSTANCE = null;

    private final Scanner reader;
    private final List<Feature> features;
	private final Set<String> featureConfiguration;
    private String menuString;
    private UserMenu userSystem;

	public App(Path path) {
        Locale.setDefault(Locale.ENGLISH);
		featureConfiguration = new HashSet<String>();
        try {
            if (path == null) {
				path = Paths.get(getClass().getClassLoader().getResource(DEFAULT_CONFIG).toURI());
            }
			featureConfiguration.addAll(Files.readAllLines(path));
		} catch (URISyntaxException e) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Couldn't find properties at '" + path + "'");
		} catch (IOException e) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Couldn't find properties at '" + path + "'");
		}
        reader = new Scanner(System.in);
        features = new ArrayList<Feature>();
    }

    public void init() {
        // non-optional feature
		final Calendar cal = new Calendar();
        final String name = readLine("Enter your name: ");
        final User user = new User(name, cal);
		userSystem = new UserMenu();
        userSystem.init(user);
        features.add(cal);

		for (final String featureClass : featureConfiguration) {
			try {
				features.add((Feature) Class.forName(featureClass).newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
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

	public Set<String> featureConfig() {
		return featureConfiguration;
	}

	public List<Feature> features() {
		return features;
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
            Thread.sleep(Timer.ONE_SECOND * 2);
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
		final Path config = args.length == 1 ? Paths.get(args[0]) : null;
        INSTANCE = new App(config);
        INSTANCE.run();
    }
}
