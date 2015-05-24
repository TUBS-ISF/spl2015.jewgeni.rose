package de.tubs.cs.isf.spl.jorg.calendar.exports;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static de.tubs.cs.isf.spl.jorg.App.app;

/**
 * @author rose
 */
public class HtmlExporter extends Exporter {

    private static final String HTML_FOOTER = "\n</tbody></table></article></body></html>";

    public HtmlExporter(final String name) {
        super(name);
    }

    @Override
    public String format() {
        final StringBuilder sb = new StringBuilder();
        // load beautifier template
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource
                    ("media/html_export_template").openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
        } catch (IOException e) {
        }
        sb.append(generateHtmlHeader());
        sb.append(generateTableHeader());

        for (final Meeting m : app().calendar().meetings()) {
            sb.append("<tr>");
            sb.append("<td>").append(m.title()).append("</td>").append("\n");
            sb.append("<td>").append(m.place()).append("</td>").append("\n");
            sb.append("<td>").append(String.format("%1$te. %1$tB %1$tY", m.date())).append("</td>").append("\n");
            sb.append("<td>").append(m.duration().toMinutes()).append(" min").append("</td>").append("\n");
            sb.append("<td>").append(m.note()).append("</td>").append("\n");
            sb.append("</tr>");
        }
        sb.append(HTML_FOOTER);
        return sb.toString();
    }

    private static String generateHtmlHeader() {
        final String user = app().currentUser().toString();
        final StringBuilder sb = new StringBuilder();
        sb.append("<title>").append(user).append("_calendar").append("</title>");
        sb.append("</head><body><article class=\"jorg-table-body\">");
        sb.append("<h1>").append("Meetings for '").append(user).append("'</h1>");
        sb.append("\n<hr>\n");
        return sb.toString();
    }

    private static String generateTableHeader() {
        final StringBuilder sb = new StringBuilder();
        sb.append("<table>").append("\n").append("<thead>").append("\n").append("<tr>").append("\n");
        sb.append("<th>").append("Title").append("</th>").append("\n");
        sb.append("<th>").append("Place").append("</th>").append("\n");
        sb.append("<th>").append("Date").append("</th>").append("\n");
        sb.append("<th>").append("Duration").append("</th>").append("\n");
        sb.append("<th>").append("Note").append("</th>").append("\n");
        sb.append("</tr>").append("\n").append("</thead>").append("\n").append("<tbody");
        return sb.toString();
    }
}
