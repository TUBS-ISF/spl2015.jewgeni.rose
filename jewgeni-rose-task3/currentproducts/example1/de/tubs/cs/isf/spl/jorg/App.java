package de.tubs.cs.isf.spl.jorg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;

import de.tubs.cs.isf.spl.jorg.app_features.AlarmMenu;
import de.tubs.cs.isf.spl.jorg.app_features.Calculator;
import de.tubs.cs.isf.spl.jorg.app_features.Clock;
import de.tubs.cs.isf.spl.jorg.app_features.Notes;
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

	private static final Map<String, String> KEY_DESCRIPTION = new HashMap<String, String>();

	/*
	 * first level features.
	 */
	static final String CALENDAR = "calendar";

	// #ifdef Alarm
	// #define ALARM="alarm"
	// #expand private static final String ALARM = "%ALARM%";
//@	private static final String ALARM = "%ALARM%";
//@	static {
//@		KEY_DESCRIPTION.put(ALARM, "set up an alarm");
//@	}
	// #endif

	// #ifdef Notes
	// #define NOTES="notes"
	// #expand private static final String NOTES = "%NOTES%";	
//@	private static final String NOTES = "%NOTES%";	
//@	static {
//@		KEY_DESCRIPTION.put(NOTES, "write down some notes");
//@	}
	// #endif

	// #ifdef Calculator
	// #define CALCULATOR="calc"
	// #expand private static final String CALCULATOR = "%CALCULATOR%";
	private static final String CALCULATOR = "calc";
	static {
		KEY_DESCRIPTION.put(CALCULATOR, "start basic calculator");
	}
	// #endif

	// #ifdef Clock
	// #define CLOCK="clock"
	// #expand private static final String CLOCK = "%CLOCK%";
	private static final String CLOCK = "clock";
	static {
		KEY_DESCRIPTION.put(CLOCK, "show date and time");
	}
	// #endif

	// #ifdef MultiUserSupport
	// #define MULTI_USER="users"
	// #expand private static final String MULTI_USER = "%MULTI_USER%";
//@	private static final String MULTI_USER = "%MULTI_USER%";
//@	static {
//@		KEY_DESCRIPTION.put(MULTI_USER, "advanced user management");
//@	}
	// #endif

	static {
		KEY_DESCRIPTION.put(CALENDAR, "calendar function");
	}

	/*
	 * Global flag.
	 */
	public static final String EXIT = "exit";

	/*
	 * Singleton
	 */
	private static App INSTANCE = null;

	private final Scanner reader;
	private final List<Feature> features;
	private String menuString;
	private UserMenu userSystem;

	public App() {
		Locale.setDefault(Locale.ENGLISH);
		reader = new Scanner(System.in);
		features = new ArrayList<Feature>();
	}

	public void init() {
		// non-optional feature
		final Calendar cal = new Calendar(CALENDAR, descriptionOf(CALENDAR));
		final String name = readLine("Enter your name: ");
		final User user = new User(name, cal);
		userSystem = new UserMenu(MULTI_USER, MULTI_USER);
		userSystem.init(user);
		features.add(cal);

		// #ifdef Clock
		features.add(new Clock(CLOCK, descriptionOf(CLOCK)));
		// #endif

		// #ifdef Calculator
		features.add(new Calculator(CALCULATOR, descriptionOf(CALCULATOR)));
		// #endif

		// #ifdef Alarm
//@		features.add(new AlarmMenu(ALARM, descriptionOf(ALARM)));
		// #endif

		// #ifdef Notes
//@		features.add(new Notes(NOTES, descriptionOf(NOTES)));
		// #endif

		// #ifdef MultiUserSupport
//@		final UserMenu sys = new UserMenu(MULTI_USER, descriptionOf(MULTI_USER), userSystem, features);
//@		userSystem = sys;
//@		features.add(sys);
		// #endif

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
			Thread.sleep(Timer.ONE_SECOND * 2);
		} catch (InterruptedException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static App app() {
		return INSTANCE;
	}

	public static String descriptionOf(final String feature) {
		return KEY_DESCRIPTION.getOrDefault(feature, "");
	}

	public static void main(final String[] args) {
		INSTANCE = new App();
		INSTANCE.run();
	}
}
