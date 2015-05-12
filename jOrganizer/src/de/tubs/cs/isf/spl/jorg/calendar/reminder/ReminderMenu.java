package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import de.tubs.cs.isf.spl.jorg.App;
import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.PROMPT_CLEAR;
import static de.tubs.cs.isf.spl.jorg.App.app;
import de.tubs.cs.isf.spl.jorg.Feature;
import de.tubs.cs.isf.spl.jorg.calendar.Meeting;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rose
 */
public final class ReminderMenu extends Feature {

    private static final String SOUND_REMINDER = "sound";
    private static final String ADD_REMINDER = "add";
    private static final String REMOVE_REMINDER = "remove";

    private final Map<String, Reminder> reminders;
    private final String menuString;
    private final boolean withSound;

    public ReminderMenu(final String key) {
        this(key, key);
    }

    public ReminderMenu(final String key, final String reminder) {
        super(key, key);
        this.reminders = new HashMap<String, Reminder>();
        withSound = SOUND_REMINDER.equals(reminder);

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "reminder menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - exits reminder menu\n", "[" + EXIT + "]"));
        sb.append(String.format("%10s - add a reminder\n", "[" + ADD_REMINDER + "]"));
        sb.append(String.format("%10s - remove a reminder\n", "[" + REMOVE_REMINDER + "]"));
        menuString = sb.toString();
    }

    @Override
    public void action() {
        String input;
        final String promptKey = super.key + "/" + key;
        while (true) {
            app().println(PROMPT_CLEAR);
            app().println(menuString, promptKey);
            input = app().readLine();
            if (EXIT.equals(input)) {
                break;
            } else if (ADD_REMINDER.equals(input)) {
                app().print("Meeting: ", promptKey);
                final String meeting = app().readLine();

                app().print("Time in min: ", promptKey);
                final Duration duration = Duration.ofMinutes(Long.parseLong(app().readLine()));

                if (add(meeting, duration)) {
                    app().println("Reminder successfully added to meeting", promptKey);
                } else {
                    app().println("There is no such meeting as '" + meeting + "'!", promptKey);
                }
            } else if (REMOVE_REMINDER.equals(input)) {
                app().print("Meeting: ", promptKey);
                final String meeting = app().readLine();

                if (remove(meeting)) {
                    app().println("Reminder successfully removed from meeting", promptKey);
                } else {
                    app().println("There is no such meeting as '" + meeting + "'!", promptKey);
                }
            } else {
                app().printErr("Invalid option", promptKey);
            }
        }
    }

    private boolean add(final String meeting, final Duration before) {
        final Meeting m = app().calendar().findMeeting(meeting);
        if (m != null) {
            Reminder r = new MuteReminder(m, before);
            if (withSound) {
                r = new SoundReminder(r);
            }
            r.run();
            m.changeReminder(r);
            reminders.put(meeting, r);
            return true;
        }
        return false;
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
