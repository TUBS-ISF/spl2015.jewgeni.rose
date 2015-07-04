package jorg.calendar;

import jorg.calendar.reminder.Reminder;

/**
 * @author rose
 */
public class Meeting implements Comparable<Meeting> {

    private Reminder reminder;

    public void changeReminder(final Reminder reminder) {
        this.reminder = reminder;
    }

    private void mkString() {
    	original();
    	if (reminder != null) {
			reminderString.append(String.format("Reminder:    %s%n", reminder));
        }
	}
}
