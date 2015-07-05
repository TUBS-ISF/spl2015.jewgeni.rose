package jorg.calendar.exports;

import jorg.calendar.exports.share.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author rose
 */
public class HtmlExporter extends Exporter {

	private final Set<Sharer> sharers;

	public HtmlExporter() {
		sharers = new HashSet<Sharer>();
	}

	@Override
	public String format() {
		final StringBuilder sb = new StringBuilder();
		sb.append(generateHtmlTemplate());
		sb.append(generateHtmlHeader());

		sb.append(generateShareTemplate());
		for (final Sharer sharer : sharers) {
			sb.append(sharer.content()).append("\n\n");
		}
		sb.append(generateShareFooter());

		sb.append(generateTableHeader());
		sb.append(generateTableContent());
		sb.append(generateHtmlFooter());
		return sb.toString();
	}

	private String generateShareTemplate() {
		final StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
							.getResource("media/html_share_template").openStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
		}
		return sb.toString();
	}

	private String generateShareFooter() {
		return "<hr></div>";
	}
}
