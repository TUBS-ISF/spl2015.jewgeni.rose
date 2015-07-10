package jorg.calendar.reminder;

import static jorg.App.EXIT;
import static jorg.App.app;
import static jorg.App.clear;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import jorg.App;
import jorg.BasicFeature;
import jorg.calendar.Calendar;
import jorg.calendar.Meeting;

public aspect Reminder {

	private AbstractReminder Meeting.reminder;

	public void Meeting.changeReminder(final AbstractReminder reminder) {
		this.reminder = reminder;
	}

	String around(Meeting m): target(m) && execution(String Meeting.toString()) {
		String s = proceed(m);
		return s + String.format("Reminder:    %s%n", m.reminder);
	}

	after(Calendar cal): target(cal) && execution(Calendar.new(String, String)) {
		cal.addFeature(new ReminderMenu());
	}
}

final class ReminderMenu extends BasicFeature {

	private static final String ADD_REMINDER = "add";
	private static final String REMOVE_REMINDER = "remove";

	private final Duration DEFAULT_REMINDER_TIME = Duration.ofMinutes(30);
	private final Map<String, AbstractReminder> reminders;
	private final StringBuilder menuString;

	public ReminderMenu() {
		super("reminder", "reminder for a meeting");
		this.reminders = new HashMap<String, AbstractReminder>();

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

	private boolean add(final String meeting, final Duration bef) {
		final Meeting m = app().calendar().findMeeting(meeting);
		if (m != null) {
			final AbstractReminder r = build(m, bef);
			m.changeReminder(r);
			reminders.put(meeting, r);
			SwingUtilities.invokeLater(r);
			return true;
		}
		return false;
	}

	private AbstractReminder build(final Meeting meeting, final Duration bef) {
		return new MuteReminder(meeting, bef);
	}

	private boolean remove(final String meeting) {
		final AbstractReminder r = reminders.get(meeting);
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

abstract class AbstractReminder implements Runnable {

	protected final Meeting meeting;
	protected final Duration bef;
	protected final LocalDateTime time;
	protected volatile boolean shutdown;

	protected AbstractReminder(final Meeting meeting, final Duration bef) {
		this.meeting = meeting;
		this.bef = bef;
		this.time = meeting.date().minus(bef);
		this.shutdown = false;
	}

	protected abstract void stop();
}

class MuteReminder extends AbstractReminder {

	MuteReminder(final Meeting meeting, final Duration bef) {
		super(meeting, bef);
	}

	protected void play() {
		JOptionPane.showMessageDialog(null,
						"Your meeting '" + meeting.title() + "' is about to begin in " + bef.toHours() + " hours",
						"Reminder", JOptionPane.OK_OPTION);
	}

	public void run() {
		LocalDateTime now;
		while (!shutdown) {
			now = LocalDateTime.now();
			if (now.equals(time) || now.isAfter(time)) {
				play();
				break;
			}
			try {
				Thread.sleep(Duration.ofMinutes(1).toMillis()); // sleep for a minute and check again
			} catch (InterruptedException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	protected synchronized void stop() {
		shutdown = true;
	}

	@Override
	public String toString() {
		return "Reminder set for " + time.format(DateTimeFormatter.ISO_LOCAL_DATE) + " at "
						+ time.format(DateTimeFormatter.ISO_LOCAL_TIME) + " clock.";
	}
}
