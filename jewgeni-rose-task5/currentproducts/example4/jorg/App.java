package jorg; 

import jorg.calendar.Calendar; 

import java.util.ArrayList; 
import java.util.List; 
import java.util.Scanner; 

import jorg.app_features.Notes; 

import jorg.app_features.AlarmMenu; 

/**
 * @author rose
 */
public final   class  App  implements Runnable {
	

	/*
	 * Prompt formatter strings.
	 */
	public static final String PROMPT_ERROR = "\033[47m\033[31m";

	
	public static final String PROMPT_BOLD_BLUE = "\033[1m\033[34m";

	
	public static final String PROMPT_BOLD_WHITE = "\033[1m\033[37m";

	
	public static final String PROMPT_BOLD = "\033[1m";

	
	public static final String PROMPT_NORMAL = "\033[0m";

	
	public static final String PROMPT_CLEAR = "\033[H\033[2J";

	
	public static final String EXIT = "exit";

	

	/*
	 * Singleton
	 */
	private static App INSTANCE = null;

	

	private final Scanner reader;

	
	private final List<Feature> features;

	
	private final StringBuilder menuString;

	
	private UserMenu userSystem;

	

	public App() {
		reader = new Scanner(System.in);
		features = new ArrayList<Feature>();
		menuString = new StringBuilder();
	}

	

	 private void  init__wrappee__Base  () {
		menuString.append(PROMPT_BOLD + "main menu:\n" + PROMPT_NORMAL);
		menuString.append(String.format("%10s - exits the application\n", "[" + EXIT + "]"));
		
		// non-optional feature
		final Calendar cal = new Calendar();
		final String name = readLine("Enter your name: ");
		final User user = new User(name, cal);
		userSystem = new UserMenu();
		userSystem.init(user);
		addFeature(cal);
	}

	

	 private void  init__wrappee__Notes  () {
		init__wrappee__Base();
		addFeature(new Notes());
	}

	

	private void init() {
		init__wrappee__Notes();
		addFeature(new AlarmMenu());
	}

	
	
	private void addFeature(final Feature f) {
		features.add(f);
		
		String keyStr = String.format("%10s - ", "[" + f.menuKey() + "]");
		menuString.append(keyStr).append(f.description()).append("\n");
	}

	
	
	public User currentUser() {
		if (userSystem == null) {
			return null;
		}
		return userSystem.current();
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

	

	private void choose(final String key) {
		for (final Feature feature : features) {
			if (feature.menuKey().equals(key)) {
				feature.action();
				return;
			}
		}
		printErr("Invalid option!");
	}

	

	///////////////////////////////////////////////////////////////////
	// Utility functions for I/O operations on standard input/output //
	///////////////////////////////////////////////////////////////////

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

	

	private String read(final String prompt, final String key) {
		System.out.print(prompt(key));
		System.out.print(prompt);
		return reader.nextLine();
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
	 * @param args
	 *            the command line arguments
	 */
	public static void main(final String[] args) {
		INSTANCE = new App();
		SwingUtilities.invokeLater(INSTANCE);
	}


}
