package de.tubs.cs.isf.spl.jorg;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.clear;

import java.util.List;
import java.util.Stack;

import de.tubs.cs.isf.spl.jorg.calendar.Calendar;

/**
 *
 * @author rose
 */
public class UserMenu extends Feature {

	private static final String FEATURE_KEY = "users";
	private static final String FEATURE_DESC = "advanced user management";
    private static final String ADD_USER = "add";
    private static final String REMOVE_USER = "remove";
    private static final String LIST_USERS = "list";

    private final Stack<User> users;
    private final List<Feature> features;
    private final String menuString;

	public UserMenu() {
		super(FEATURE_KEY, FEATURE_DESC);
		this.users = new Stack<User>();

		// TODO load multiuser config
		this.features = App.app().features();

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "user menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - exits user menu\n", "[" + EXIT + "]"));
        sb.append(String.format("%10s - add a new user\n", "[" + ADD_USER + "]"));
        sb.append(String.format("%10s - remove a user\n", "[" + REMOVE_USER + "]"));
        sb.append(String.format("%10s - list all users\n", "[" + LIST_USERS + "]"));
        menuString = sb.toString();
    }

    @Override
    public void action() {
        String input;
        clear();
        println(menuString);
        while (true) {
            input = readLine();
            if (EXIT.equals(input)) {
                break;
            } else if (ADD_USER.equals(input)) {
                add();
            } else if (REMOVE_USER.equals(input)) {
                remove();
            } else if (LIST_USERS.equals(input)) {
                list();
            } else {
                printErr("Invalid option");
            }
        }
    }

    public User current() {
        return users.peek();
    }

    private void add() {
        final String name = readLine("Enter your name: ");
		final Calendar cal = new Calendar();
        final User newUser = new User(name, cal);
        // remove old calendar from menu ...
        features.remove(current().getCalendar());
        // and init the new one
        features.add(cal);
        users.push(newUser);
    }

    public void init(final User user) {
        if (users.empty()) {
            users.push(user);
        }
    }

    private void remove() {
        if (users.size() < 2) {
            printErr("The system contains only one user. You should add a new one, before you try to remove this one!");
        } else {
            println("Enter the name of user, who should be removed: ");
            final String name = readLine();
            final User dummy = new User(name, null);
            if (users.contains(dummy)) {
                println("User '" + name + "' was removed from the database");
                users.remove(dummy);
            } else {
                println("There is no user with the name '" + name + "' in the database");
            }
        }
    }

    private void list() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Currently the database contains following users: \n");
        for (final User user : users) {
            sb.append(" - ").append(user).append("\n");
        }
        println(sb);
    }
}
