package jorg.calendar; 

import java.time.Duration; 
import java.time.LocalDateTime; 

import jorg.calendar.reminder.Reminder; 

/**
 * @author rose
 */
public   class  Meeting  implements Comparable<Meeting> {
	

	private final String title, note, place;

	
	private final LocalDateTime date;

	
	private final Duration duration;

	
	private final StringBuilder reminderString = new StringBuilder();

	

	public Meeting(final String title, final String note, final String place, final LocalDateTime date,
					final Duration duration) {
		this.title = title;
		this.note = note;
		this.place = place;
		this.date = date;
		this.duration = duration;
	}

	

	public int compareTo(final Meeting m) {
		return date.compareTo(m.date);
	}

	

	public String title() {
		return title;
	}

	

	public String place() {
		return place;
	}

	

	public String note() {
		return note;
	}

	

	public Duration duration() {
		return duration;
	}

	

	public LocalDateTime date() {
		return date;
	}

	

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 37 * hash + (this.title != null ? this.title.hashCode() : 0);
		return hash;
	}

	

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Meeting other = (Meeting) obj;
		return !((this.title == null) ? (other.title != null) : !this.title.equals(other.title));
	}

	

	@Override
	public String toString() {
		mkString();
		String str = reminderString.toString();
		reminderString.delete(0, reminderString.length());
		return str;
	}

	

	 private void  mkString__wrappee__Calendar  () {
		reminderString.append(String.format("Title:       %s%n", title));
		reminderString.append(String.format("Description: %s%n", note));
		reminderString.append(String.format("Date:        %1$te. %1$tB %1$tY%n", date));
		reminderString.append(String.format("Start:       %tR%n", date.plus(duration)));
		reminderString.append(String.format("Place:       %s%n", place));
	}

	

    private void mkString() {
    	mkString__wrappee__Calendar();
    	if (reminder != null) {
			reminderString.append(String.format("Reminder:    %s%n", reminder));
        }
	}

	

    private Reminder reminder;

	

    public void changeReminder(final Reminder reminder) {
        this.reminder = reminder;
    }


}
