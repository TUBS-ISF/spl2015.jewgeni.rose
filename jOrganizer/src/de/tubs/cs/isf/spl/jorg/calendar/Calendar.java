package de.tubs.cs.isf.spl.jorg.calendar;

import de.tubs.cs.isf.spl.jorg.App;
import static de.tubs.cs.isf.spl.jorg.App.CONFIG;
import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.PROMPT_CLEAR;
import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.Feature;
import de.tubs.cs.isf.spl.jorg.User;
import de.tubs.cs.isf.spl.jorg.calendar.printer.PrintMenu;
import de.tubs.cs.isf.spl.jorg.calendar.reminder.ReminderMenu;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author rose
 */
public final class Calendar extends Feature {

    private static final String CALENDAR_FEATURE_PRINT = "print";
    private static final String CALENDAR_FEATURE_REMIND = "reminder";
    private static final String CALENDAR_FEATURE_IM_EXPORT = "im-export";
    private static final String CALENDAR_FEATURE_SHARE = "share";

    private static final String ADD_MEETING = "add";
    private static final String REMOVE_MEETING = "remove";
    private static final String CHANGE_MEETING = "change";
    private static final String LIST_MEETINGS = "list";

    private final List<Feature> features;
    private final Set<Meeting> meetings;
    private final String menuString;
    private static final String NEW_MEETING = ""
        + "-----------------------------------------------------------\n"
        + "|       Please fill in the following information          |\n"
        + "|  You can leave out optional fields by pressing <ENTER>  |\n"
        + "|        You have to enter the title and date!            |\n"
        + "-----------------------------------------------------------";
    private boolean firstAdd;

    public Calendar(final String key) {
        this(key, key);
    }

    public Calendar(final String key, final String desc) {
        super(key, desc);
        this.meetings = new TreeSet<Meeting>();
        this.firstAdd = true;
        features = new ArrayList<Feature>();
        for (final String feature : CONFIG.stringPropertyNames()) {
            if (CALENDAR_FEATURE_PRINT.equals(feature)) {
                features.add(new PrintMenu(feature, CONFIG.getProperty(feature)));
            } else if (CALENDAR_FEATURE_REMIND.equals(feature)) {
                features.add(new ReminderMenu(feature, CONFIG.getProperty(feature)));
            }
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "calendar menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - Exits calendar menu\n", "[" + App.EXIT + "]"));
        sb.append(String.format("%10s - Add a new meeting\n", "[" + ADD_MEETING + "]"));
        sb.append(String.format("%10s - Remove an existing meeting\n", "[" + REMOVE_MEETING + "]"));
        sb.append(String.format("%10s - Change an existing meeting\n", "[" + CHANGE_MEETING + "]"));
        sb.append(String.format("%10s - Show all meetings\n", "[" + LIST_MEETINGS + "]"));
        for (final Feature f : features) {
            String keyStr = String.format("%10s - ", "[" + f.menuKey() + "]");
            sb.append(keyStr).append(f.description()).append("\n");
        }
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
            } else if (ADD_MEETING.equals(input)) {
                addNewMeeting();
            } else if (REMOVE_MEETING.equals(input)) {
                deleteMeeting();
            } else if (CHANGE_MEETING.equals(input)) {
                changeMeeting();
            } else if (LIST_MEETINGS.equals(input)) {
                list();
            } else {
                chooseFeature(input);
            }
        }
    }

    private void chooseFeature(final String key) {
        for (final Feature feature : features) {
            if (feature.menuKey().equals(key)) {
                feature.action();
                return;
            }
        }
        app().printErr("Invalid option!");
    }

    public void addNewMeeting() {
        if (firstAdd) {
            app().println(NEW_MEETING, key);
            app().println("\n", key);
            firstAdd = false;
        }
        app().print("Title: ");
        final String title = app().readLine();

        app().print("Description: ");
        final String note = app().readLine();

        app().print("Place: ");
        final String place = app().readLine();

        app().print("Date [2015-04-30]: ");
        final String dateStr = app().readLine();

        app().print("Start [08:00]: ");
        String beginStr = app().readLine();
        final Duration duration;

        if (beginStr.isEmpty()) {
            beginStr = "00:00";
            duration = Duration.ofHours(23).plus(Duration.ofMinutes(59));
        } else {
            app().print("Time in min: ");
            final String minStr = app().readLine();
            if (minStr.isEmpty()) {
                duration = Duration.ofMinutes(90);
            } else {
                duration = Duration.ofMinutes(Long.parseLong(minStr));
            }
        }

        final LocalDateTime date = LocalDateTime.parse(dateStr + "T" + beginStr + ":00",
                                                       DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        final Meeting m = new Meeting(title, note, place, date, duration);
        meetings.add(m);
    }

    public void changeMeeting() {
        app().print("Enter the meeting to change: ");
        final String title = app().readLine();
        final Meeting m = findMeeting(title);
        meetings.remove(m);
        addNewMeeting();
    }

    public void deleteMeeting() {
        app().print("Enter the meeting to delete: ");
        final String title = app().readLine();
        final Meeting m = findMeeting(title);
        meetings.remove(m);
    }

    public Meeting findMeeting(final String title) {
        for (final Meeting m : meetings) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        return null;
    }

    private void list() {
        final StringBuilder sb = new StringBuilder();
        sb.append("-------------------------").append("\n");
        for (final Meeting meeting : meetings) {
            sb.append(meeting);
            sb.append("-------------------------").append("\n");
        }
        app().println(sb, key);
    }
}
