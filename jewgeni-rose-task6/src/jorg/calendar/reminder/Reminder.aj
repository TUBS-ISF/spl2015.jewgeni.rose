package jorg.calendar.reminder;

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