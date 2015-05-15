package de.tubs.cs.isf.spl.jorg.calendar;

import de.tubs.cs.isf.spl.jorg.App;
import static de.tubs.cs.isf.spl.jorg.App.CONFIG;
import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.clear;
import de.tubs.cs.isf.spl.jorg.Feature;
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
        sb.append(String.format("%10s - Show all meetings\n", "[" + LIST_MEETINGS + "]"));
        for (final Feature f : features) {
            String keyStr = String.format("%10s - ", "[" + f.menuKey() + "]");
            sb.append(keyStr).append(f.description()).append("\n");
        }
        menuString = sb.toString();
    }

    @Override
    public void action() {
        clear();
        println(menuString);

        String input;
        while (true) {
            input = readLine();
            if (EXIT.equals(input)) {
                break;
            } else if (ADD_MEETING.equals(input)) {
                addNewMeeting();
            } else if (REMOVE_MEETING.equals(input)) {
                deleteMeeting();
            } else if (LIST_MEETINGS.equals(input)) {
                list();
            } else {
                chooseFeature(input);
                clear();
                println(menuString);
                App.sleep();
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
        printErr("Invalid option!");
    }

    public void addNewMeeting() {
        if (firstAdd) {
            println(NEW_MEETING);
            firstAdd = false;
        }
        final String title = readLine("Title: ");
        final String note = readLine("Description: ");
        final String place = readLine("Place: ");
        final String dateStr = readLine("Date [2015-04-30]: ");

        String beginStr = readLine("Start [08:00]: ");
        final Duration duration;

        if (beginStr.isEmpty()) {
            beginStr = "00:00";
            duration = Duration.ofHours(23).plus(Duration.ofMinutes(59));
        } else {
            final String minStr = readLine("Time in min: ");
            if (minStr.isEmpty()) {
                duration = Duration.ofMinutes(90);
            } else {
                duration = Duration.ofMinutes(Long.parseLong(minStr));
            }
        }

        final LocalDateTime date = LocalDateTime.parse(dateStr + "T" + beginStr + ":00",
                                                       DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        final Meeting m = new Meeting(title, note, place, date, duration);

        println("Meeting '" + title + "' added");
        meetings.add(m);
    }

    public void deleteMeeting() {
        final String title = readLine("Enter the meeting to delete: ");
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
        println(sb);
    }
}
