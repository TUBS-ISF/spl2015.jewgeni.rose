// #condition Reminder
package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.app;
import static de.tubs.cs.isf.spl.jorg.App.clear;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingUtilities;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;
import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

/**
 *
 * @author rose
 */
public final class ReminderMenu extends Feature {

	private static final String ADD_REMINDER = "add";
	private static final String REMOVE_REMINDER = "remove";

	private final Duration DEFAULT_REMINDER_TIME = Duration.ofMinutes(30);
	private final Map<String, Reminder> reminders;
	private final String menuString;

	public ReminderMenu(final String key) {
		this(key, key);
	}

	public ReminderMenu(final String key, final String desc) {
		super(key, desc);
		this.reminders = new HashMap<String, Reminder>();

		final StringBuilder sb = new StringBuilder();
		sb.append(App.PROMPT_BOLD + "reminder menu:\n" + App.PROMPT_NORMAL);
		sb.append(String.format("%10s - exits reminder menu\n", "[" + EXIT + "]"));
		sb.append(String.format("%10s - add a reminder\n", "[" + ADD_REMINDER + "]"));
		sb.append(String.format("%10s - remove a reminder\n", "[" + REMOVE_REMINDER + "]"));
		menuString = sb.toString();
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
			// #ifdef ReminderWithSound
			final Reminder r = new SoundReminder(m, before);
			// #else
//@			final Reminder r = new MuteReminder(m, before);
			// #endif
			m.changeReminder(r);
			reminders.put(meeting, r);
			SwingUtilities.invokeLater(r);
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
