package jorg.calendar;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author rose
 */
public class Meeting implements Comparable<Meeting> {

	private final String title, note, place;
	private final LocalDateTime date;
	private final Duration duration;

	public Meeting(final String title, final String note, final String place, final LocalDateTime date,
					final Duration duration) {
		this.title = title;
		this.note = note;
		this.place = place;
		this.date = date;
		this.duration = duration;
	}

	@Override
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
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Title:       %s%n", title));
		sb.append(String.format("Description: %s%n", note));
		sb.append(String.format("Date:        %1$te. %1$tB %1$tY%n", date));
		sb.append(String.format("Start:       %tR%n", date.plus(duration)));
		sb.append(String.format("Place:       %s%n", place));
		return sb.toString();
	}
}
