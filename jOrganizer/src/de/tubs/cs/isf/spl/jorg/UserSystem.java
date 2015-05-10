package de.tubs.cs.isf.spl.jorg;

import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.calendar.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rose
 */
public class UserSystem extends Feature {

    private final Set<User> users;
    private User currentUser;

    public UserSystem(final String key) {
        this(key, key);
    }

    public UserSystem(final String key, final String desc) {
        super(key, desc);
        users = new HashSet<User>();
    }

    @Override
    public void action() {
        while (true) {

            break;
        }
    }

    public User current() {
        return currentUser;
    }

    public void add() {
        app().print("Enter your name: ");
        final String name = app().readLine();
        currentUser = new User(name, new Calendar(App.FEATURE_CALENDAR));
        users.add(currentUser);
    }

    public void remove() {
        app().print("Enter the name of user, who should be removed: ");
        final String name = app().readLine();
        final User dummy = new User(name, null);
        if (users.contains(dummy)) {
            app().println("User '" + name + "' was removed from the database");
            users.remove(dummy);
        } else {
            app().println("There is no user with the name '" + name + "' in the database");
        }
    }

    public void list() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Currently the database contains following users: \n");
        for (final User user : users) {
            sb.append(" - ").append(user).append("\n");
        }
        app().println(sb);
    }
}
