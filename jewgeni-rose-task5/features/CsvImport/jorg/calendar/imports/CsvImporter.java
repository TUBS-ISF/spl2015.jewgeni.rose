package de.tubs.cs.isf.spl.jorg.calendar.imports;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static de.tubs.cs.isf.spl.jorg.App.app;

/**
 * @author rose
 * @date 24.05.15.
 */
public class CsvImporter extends Importer {

    protected CsvImporter(final String name) {
        super(name);
    }

    @Override
    public void writeIntoCal(final List<String> data) {
        // empty data?
        if (data.size() < 2) {
            return;
        }

        final Set<Meeting> meetings = app().calendar().meetings();
        // read the the header
        final String[] header = data.get(0).split(";");

        try {
            String[] meetingData;
            String title = "", place = "", note = "";
            Duration duration = Duration.ofMinutes(5);
            LocalDateTime date = LocalDateTime.now().minus(duration);

            for (int i = 1; i < data.size(); ++i) {
                meetingData = data.get(i).split(";");

                for (int j = 0; j < meetingData.length; ++j) {
                    if ("title".equalsIgnoreCase(header[j])) {
                        title = meetingData[j];
                        if (!title.isEmpty()) {
                            title = title.substring(1, title.length() - 1);
                        }
                    } else if ("place".equalsIgnoreCase(header[j])) {
                        place = meetingData[j];
                        if (!place.isEmpty()) {
                            place = place.substring(1, place.length() - 1);
                        }
                    } else if ("note".equalsIgnoreCase(header[j])) {
                        note = meetingData[j];
                        if (!note.isEmpty()) {
                            note = note.substring(1, note.length() - 1);
                        }
                    } else if ("duration".equalsIgnoreCase(header[j])) {
                        meetingData[j] = meetingData[j].replace("min", "");
                        duration = Duration.ofMinutes(Long.parseLong(meetingData[j]));
                    } else if ("date".equalsIgnoreCase(header[j])) {
                        date = LocalDateTime.parse(meetingData[j]);
                    }
                }
                if (!title.isEmpty() && date.isAfter(LocalDateTime.now())) {
                    meetings.add(new Meeting(title, note, place, date, duration));
                }
            }
        } catch (IndexOutOfBoundsException ex) {
        }
    }
}
