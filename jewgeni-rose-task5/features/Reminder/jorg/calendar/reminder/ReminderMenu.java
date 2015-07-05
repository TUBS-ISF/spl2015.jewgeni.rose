package jorg.calendar.reminder;

import jorg.App;
import jorg.BasicFeature;
import jorg.calendar.Meeting;

import javax.swing.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static jorg.App.EXIT;
import static jorg.App.app;
import static jorg.App.clear;

/**
 *
 * @author rose
 */
public final class ReminderMenu extends BasicFeature {

    private static final String ADD_REMINDER = "add";
    private static final String REMOVE_REMINDER = "remove";

    private final Duration DEFAULT_REMINDER_TIME = Duration.ofMinutes(30);
    private final Map<String, Reminder> reminders;
    private final StringBuilder menuString;

    public ReminderMenu() {
        super("reminder", "reminder for a meeting");
        this.reminders = new HashMap<String, Reminder>();

        menuString = new StringBuilder();
        menuString.append(App.PROMPT_BOLD + "reminder menu:\n" + App.PROMPT_NORMAL);
        menuString.append(String.format("%10s - exits reminder menu\n", "[" + EXIT + "]"));
        menuString.append(String.format("%10s - add a reminder\n", "[" + ADD_REMINDER + "]"));
        menuString.append(String.format("%10s - remove a reminder\n", "[" + REMOVE_REMINDER + "]"));
    }

    @Override
    public void println(final Object obj) {
        final String subKey = "calendar/" + key;
        App.println(obj, subKey);
    }

    @Override
    public void printErr(final Object obj) {
        final String subKey = "calendar/" + key;
        App.printErr(obj, subKey);
    }

    @Override
    public String readLine(final String prompt) {
        final String subKey = "calendar/" + key;
        return App.readLine(prompt, subKey);
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
            } else if (ADD_REMINDER.equals(input)) {
                final String meeting = readLine("Meeting: ");
                final String time = readLine("Time [30 min]: ");
                final Duration duration;
                if (!time.isEmpty()) {
                    duration = Duration.ofMinutes(Long.parseLong(time));
                } else {
                    duration = DEFAULT_REMINDER_TIME;
                }

                if (add(meeting, duration)) {
                    println("Reminder successfully added to meeting");
                } else {
                    println("There is no such meeting as '" + meeting + "'!");
                }
            } else if (REMOVE_REMINDER.equals(input)) {
                final String meeting = readLine("Meeting: ");

                if (remove(meeting)) {
                    println("Reminder successfully removed from meeting");
                } else {
                    println("There is no such meeting as '" + meeting + "'!");
                }
            } else {
                printErr("Invalid option");
            }
        }
    }

    private boolean add(final String meeting, final Duration before) {
        final Meeting m = app().calendar().findMeeting(meeting);
        if (m != null) {
            final Reminder r = build(m, before);
            m.changeReminder(r);
            reminders.put(meeting, r);
            SwingUtilities.invokeLater(r);
            return true;
        }
        return false;
    }
    
    private Reminder build(final Meeting meeting, final Duration before) {    	
    	return new MuteReminder(meeting, before);
    }

    private boolean remove(final String meeting) {
        final Reminder r = reminders.get(meeting);
        if (r != null) {
            final Meeting m = r.meeting;
            r.stop();
            m.changeReminder(null);
            reminders.remove(meeting);
            return true;
        }
        return false;
    }
}
