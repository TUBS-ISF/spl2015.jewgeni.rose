package jorg.calendar.reminder;

import java.time.Duration;

import jorg.calendar.Meeting;

public aspect ReminderWithSound {

	AbstractReminder around(ReminderMenu menu, final Meeting meeting, final Duration bef): 
		target(menu) && execution(* ReminderMenu.build(Meeting, Duration)) && args(meeting, bef) {
		return new SoundReminder(meeting, bef);
	}
}