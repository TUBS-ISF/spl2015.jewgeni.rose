// #condition IcsImport
//@package de.tubs.cs.isf.spl.jorg.calendar.imports;
//@
//@import static de.tubs.cs.isf.spl.jorg.App.app;
//@
//@import java.text.DateFormat;
//@import java.text.ParseException;
//@import java.text.SimpleDateFormat;
//@import java.time.Duration;
//@import java.time.Instant;
//@import java.time.LocalDateTime;
//@import java.util.List;
//@import java.util.Set;
//@
//@import de.tubs.cs.isf.spl.jorg.calendar.Meeting;
//@
//@/**
//@ * @author rose
//@ * @date 24.05.15.
//@ */
//@public class IcsImporter extends Importer {
//@
//@	private final DateFormat dateFormat;
//@
//@	protected IcsImporter(final String name) {
//@		super(name);
//@		dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
//@	}
//@
//@	@Override
//@	public void writeIntoCal(final List<String> data) {
//@		final Set<Meeting> meetings = app().calendar().meetings();
//@
//@		// remove header data
//@		for (final String line : data) {
//@			if (!line.equalsIgnoreCase("BEGIN:VEVENT")) {
//@				data.remove(line);
//@			} else {
//@				break;
//@			}
//@		}
//@
//@		String title = "", place = "", note = "";
//@		Duration duration = Duration.ofMinutes(5);
//@		LocalDateTime date = LocalDateTime.now().minus(duration);
//@		for (final String line : data) {
//@			if (line.startsWith("SUMMARY:")) {
//@				title = line.substring("SUMMARY:".length()).trim();
//@			} else if (line.startsWith("LOCATION:")) {
//@				place = line.substring("LOCATION:".length()).trim();
//@			} else if (line.startsWith("DESCRIPTION:")) {
//@				note = line.substring("DESCRIPTION:".length()).trim();
//@			} else if (line.startsWith("DTSTART:")) {
//@				try {
//@					date = LocalDateTime.from(dateFormat.parse(line.substring("DTSTART:".length()).trim()).toInstant());
//@				} catch (ParseException e) {
//@				}
//@			} else if (line.startsWith("DTEND:")) {
//@				try {
//@					Instant end = dateFormat.parse(line.substring("DTEND:".length()).trim()).toInstant();
//@					duration = Duration.between(date, end);
//@				} catch (ParseException e) {
//@				}
//@			} else if (line.equalsIgnoreCase("END:VEVENT")) {
//@				if (!title.isEmpty() && date.isAfter(LocalDateTime.now())) {
//@					meetings.add(new Meeting(title, note, place, date, duration));
//@				}
//@				continue;
//@			}
//@		}
//@	}
//@}
