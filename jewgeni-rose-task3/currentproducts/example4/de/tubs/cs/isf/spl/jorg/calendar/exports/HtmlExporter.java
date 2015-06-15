// #condition HtmlExport
//@package de.tubs.cs.isf.spl.jorg.calendar.exports;
//@
//@import static de.tubs.cs.isf.spl.jorg.App.app;
//@
//@import java.io.BufferedReader;
//@import java.io.IOException;
//@import java.io.InputStreamReader;
//@
//@import de.tubs.cs.isf.spl.jorg.calendar.Meeting;
//@
//@/**
//@ * @author rose
//@ */
//@public class HtmlExporter extends Exporter {
//@
//@	public HtmlExporter(final String name) {
//@		super(name);
//@	}
//@
//@	@Override
//@	public String format() {
//@		final StringBuilder sb = new StringBuilder();
//@		sb.append(generateHtmlTemplate());
//@		sb.append(generateHtmlHeader());
//@		sb.append(generateTableHeader());
//@		sb.append(generateTableContent());
//@		sb.append(generateHtmlFooter());
//@		return sb.toString();
//@	}
//@
//@	protected String generateHtmlHeader() {
//@		final String user = app().currentUser().toString();
//@		final StringBuilder sb = new StringBuilder();
//@		sb.append("<title>").append(user).append("_calendar").append("</title>");
//@		sb.append("</head><body><article class=\"jorg-table-body\">").append("\n");
//@		return sb.toString();
//@	}
//@
//@	protected String generateHtmlFooter() {
//@		return "\n</tbody></table></article></body></html>";
//@	}
//@
//@	protected String generateHtmlTemplate() {
//@		final StringBuilder sb = new StringBuilder();
//@		try {
//@			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
//@							.getResource("media/html_export_template").openStream()));
//@			String line;
//@			while ((line = reader.readLine()) != null) {
//@				sb.append(line).append("\n");
//@			}
//@			reader.close();
//@		} catch (IOException e) {
//@		}
//@		return sb.toString();
//@	}
//@
//@	protected String generateTableHeader() {
//@		final String user = app().currentUser().toString();
//@		final StringBuilder sb = new StringBuilder();
//@		sb.append("<h1>").append("Meetings for '").append(user).append("'</h1>");
//@		sb.append("\n<hr>\n");
//@		sb.append("<table>").append("\n").append("<thead>").append("\n").append("<tr>").append("\n");
//@		sb.append("<th>").append("Title").append("</th>").append("\n");
//@		sb.append("<th>").append("Place").append("</th>").append("\n");
//@		sb.append("<th>").append("Date").append("</th>").append("\n");
//@		sb.append("<th>").append("Duration").append("</th>").append("\n");
//@		sb.append("<th>").append("Note").append("</th>").append("\n");
//@		sb.append("</tr>").append("\n").append("</thead>").append("\n").append("<tbody");
//@		return sb.toString();
//@	}
//@
//@	protected String generateTableContent() {
//@		final StringBuilder sb = new StringBuilder();
//@		for (final Meeting m : app().calendar().meetings()) {
//@			sb.append("<tr>");
//@			sb.append("<td>").append(m.title()).append("</td>").append("\n");
//@			sb.append("<td>").append(m.place()).append("</td>").append("\n");
//@			sb.append("<td>").append(String.format("%1$te. %1$tB %1$tY", m.date())).append("</td>").append("\n");
//@			sb.append("<td>").append(m.duration().toMinutes()).append(" min").append("</td>").append("\n");
//@			sb.append("<td>").append(m.note()).append("</td>").append("\n");
//@			sb.append("</tr>");
//@		}
//@		return sb.toString();
//@	}
//@}
