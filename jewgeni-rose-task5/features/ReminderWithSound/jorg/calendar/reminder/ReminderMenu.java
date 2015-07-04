package jorg.calendar.reminder;

/**
 *
 * @author rose
 */
public final class ReminderMenu extends BasicFeature {

    private Reminder build(final Meeting meeting, final Duration before) {
    	return new SoundReminder(meeting, before);
    }
}
