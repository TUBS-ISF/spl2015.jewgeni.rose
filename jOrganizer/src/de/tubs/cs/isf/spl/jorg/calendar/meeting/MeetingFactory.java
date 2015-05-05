package de.tubs.cs.isf.spl.jorg.calendar.meeting;

/**
 *
 * @author rose
 */
public interface MeetingFactory {

    Meeting from();

    String to(final Meeting m);
}
