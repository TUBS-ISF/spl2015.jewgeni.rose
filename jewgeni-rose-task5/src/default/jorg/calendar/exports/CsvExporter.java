package jorg.calendar.exports; 

import jorg.calendar.Meeting; 

import java.time.format.DateTimeFormatter; 

import static jorg.App.app; 

/**
 * @author rose
 */
public  class  CsvExporter  extends Exporter {
	

    public CsvExporter() {
        super("csv");
    }

	

    @Override
    public String format() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Title;Place;Date;Duration;Note;").append("\n");
        for (final Meeting m : app().calendar().meetings()) {
            sb.append("\"").append(m.title())
                    .append("\"; \"").append(m.place())
                    .append("\"; ").append(m.date().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .append("; ").append(m.duration().toMinutes()).append("min")
                    .append("; \"").append(m.note()).append("\";\n");
        }
        return sb.toString();
    }


}
