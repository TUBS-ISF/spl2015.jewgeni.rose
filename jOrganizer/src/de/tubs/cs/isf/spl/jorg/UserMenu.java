package de.tubs.cs.isf.spl.jorg;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.PROMPT_CLEAR;
import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.calendar.Calendar;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author rose
 */
public class UserMenu extends Feature {

    private static final String ADD_USER = "add";
    private static final String REMOVE_USER = "remove";
    private static final String WHOIS_USER = "whois";
    private static final String LIST_USERS = "list";

    private final Stack<User> users;
    private final List<Feature> features;
    private final String menuString;


    public UserMenu(final String key) {
        this(key, key);
    }

    public UserMenu(final String key, final String desc) {
        this(key, desc, null, null);
    }

    public UserMenu(final String key, final String desc, final UserMenu system, final List<Feature> features) {
        super(key, desc);
        if (system == null) {
            this.users = new Stack<User>();
        } else {
            this.users = system.users;
        }
        this.features = features;

        final StringBuilder sb = new StringBuilder();
        sb.append("user menu:\n");
        sb.append(String.format("%10s - exits user menu\n", "[" + EXIT + "]"));
        sb.append(String.format("%10s - add a new user\n", "[" + ADD_USER + "]"));
        sb.append(String.format("%10s - remove a user\n", "[" + REMOVE_USER + "]"));
        sb.append(String.format("%10s - list all users\n", "[" + LIST_USERS + "]"));
        sb.append(String.format("%10s - who is the current user?\n", "[" + WHOIS_USER + "]"));
        menuString = sb.toString();
    }

    @Override
    public void action() {
        String input;
        while (true) {
            app().println(PROMPT_CLEAR);
            app().println(menuString, key);
            input = app().readLine();
            if (EXIT.equals(input)) {
                break;
            } else if (ADD_USER.equals(input)) {
                add();
            } else if (REMOVE_USER.equals(input)) {
                remove();
            } else if (LIST_USERS.equals(input)) {
                list();
            } else if (WHOIS_USER.equals(input)) {
                whois();
            } else {
                app().printErr("Invalid option", key);
            }
        }
    }

    public User current() {
        return users.peek();
    }

    private void whois() {
        app().println(current().toString(), key);
    }

    private void add() {
        app().print("Enter your name: ", key);
        final String name = app().readLine();
        final Calendar cal = new Calendar(App.FEATURE_CALENDAR);
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
            app().printErr(
                "The system contains only one user. You should add a new one, before you try to remove this one!", key);
        } else {
            app().print("Enter the name of user, who should be removed: ", key);
            final String name = app().readLine();
            final User dummy = new User(name, null);
            if (users.contains(dummy)) {
                app().println("User '" + name + "' was removed from the database", key);
                users.remove(dummy);
            } else {
                app().println("There is no user with the name '" + name + "' in the database", key);
            }
        }
    }

    private void list() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Currently the database contains following users: \n");
        for (final User user : users) {
            sb.append(" - ").append(user).append("\n");
        }
        app().println(sb, key);
    }
}
