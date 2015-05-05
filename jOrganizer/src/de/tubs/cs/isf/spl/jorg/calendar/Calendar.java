package de.tubs.cs.isf.spl.jorg.calendar;

import static java.lang.System.out;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import de.tubs.cs.isf.spl.jorg.calendar.meeting.Meeting;

/**
 *
 * @author rose
 */
public class Calendar {

    private final Set<Meeting> meetings;
    private final Scanner reader = new Scanner(System.in);
    private static final String NEW_MEETING = ""
        + "----------------------------------------------------"
        + "| Bitte füllen Sie nachfolgende Informationen aus  |"
        + "| Leere Felder überspringen Sie mit <ENTER>        |"
        + "| Der Titel und das Datum sind Pflichtangaben      |"
        + "----------------------------------------------------";
    private boolean firstAdd;

    public Calendar() {
        firstAdd = true;
		meetings = new TreeSet<Meeting>();
    }

    public void addNewMeeting() {
        if (firstAdd) {
            out.println(NEW_MEETING);
            out.println();
            firstAdd = false;
        }
        out.print("Titel: ");
        final String title = reader.nextLine();

        out.print("Beschreibung: ");
        final String desc = reader.nextLine();

        out.print("Ort: ");
        final String place = reader.nextLine();

        out.print("Datum [2015-04-30]: ");
        final String dateStr = reader.nextLine();

        out.print("Beginn: ");
        final String beginStr = reader.nextLine();

        out.print("Dauer [Min]: ");
        final Duration duration = Duration.ofMinutes(reader.nextLong());

        final LocalDateTime date = LocalDateTime.parse(dateStr + "T" + beginStr + ":00",
                                                       DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        final Meeting m = new Meeting(title, desc, place, date, duration, null);
        meetings.add(m);
    }

    public void changeMeeting() {
        out.print("Welcher Termin soll geändert werden?: ");
        String title = reader.nextLine();
        final Meeting m = get(title);

        // TODO
    }

    private Meeting get(final String title) {
        for (final Meeting m : meetings) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        return null;
    }
}
