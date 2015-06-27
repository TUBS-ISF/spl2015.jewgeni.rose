package de.tubs.cs.isf.spl.jorg;

import de.tubs.cs.isf.spl.jorg.calendar.Calendar;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.app;
import static de.tubs.cs.isf.spl.jorg.App.clear;

/**
 * @author rose
 */
public class UserMenu extends BasicFeature {

    private static final String ADD_USER = "add";
    private static final String LOGOUT_USER = "logout";
    private static final String LOGIN_USER = "login";
    private static final String REMOVE_USER = "remove";
    private static final String LIST_USERS = "list";

    private final Deque<User> users;
    private final List<Feature> features;
    private final String menuString;

    public UserMenu(final String key, final String desc) {
        this(key, desc, null, null);
    }

    public UserMenu(final String key, final String desc, final UserMenu system, final List<Feature> features) {
        super(key, desc);
        if (system == null) {
            this.users = new ArrayDeque<User>();
        } else {
            this.users = system.users;
        }
        this.features = features;

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "user menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - exits user menu\n", "[" + EXIT + "]"));
        sb.append(String.format("%10s - add a new user\n", "[" + ADD_USER + "]"));
        sb.append(String.format("%10s - remove a user\n", "[" + REMOVE_USER + "]"));
        sb.append(String.format("%10s - logout current user\n", "[" + LOGOUT_USER + "]"));
        sb.append(String.format("%10s - login another registered user\n", "[" + LOGIN_USER + "]"));
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
            } else if (LOGOUT_USER.equals(input)) {
                logout();
            } else if (LOGIN_USER.equals(input)) {
                login();
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
        final Calendar cal = new Calendar(App.FEATURE_CALENDAR);
        final User newUser = new User(name, cal);
        // remove old calendar from menu ...
        features.remove(current().getCalendar());
        // and init the new one
        features.add(cal);
        users.push(newUser);
    }

    private void logout() {
        if (users.size() < 2) {
            printErr("You can't logout, as you are currently the only registered user");
        } else {
            users.add(users.pop());
        }
    }

    private void login() {
        final String name = readLine("Enter your name: ");
        for (final User user : users) {
            if (user.name().equals(name)) {
                users.push(user);
                return;
            }
        }
        printErr("There is no user with the specified name");
    }

    public void init(final User user) {
        if (users.isEmpty()) {
            users.push(user);
        }
    }

    private void remove() {
        if (users.size() < 2) {
            printErr("The system contains only one user. You should add a new one, before you try to remove this one!");
        } else {
            final String name = readLine("Enter the name of user, who should be removed: ");
            for (final User user : users) {
                if (user.name().equals(name)) {
                    println("User '" + name + "' was removed from the database");
                    if (app().currentUser().equals(user)) {
                        features.remove(user.getCalendar());
                        users.remove(user);
                        features.add(current().getCalendar());
                    } else {
                        users.remove(user);
                    }
                    return;
                }
            }
            println("There is no user with the name '" + name + "' in the database");
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
