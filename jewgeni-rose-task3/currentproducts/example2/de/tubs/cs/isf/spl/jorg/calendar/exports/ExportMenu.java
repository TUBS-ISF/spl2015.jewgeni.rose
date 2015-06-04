// #condition EXPORT
package de.tubs.cs.isf.spl.jorg.calendar.exports;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.clear;

import java.util.HashSet;
import java.util.Set;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;
import de.tubs.cs.isf.spl.jorg.calendar.exports.share.HtmlShareExporter;

/**
 * @author rose
 * 
 */
public class ExportMenu extends Feature {

	// #if ShareViaEmail || ShareViaFacebook || ShareViaTwitter || ShareViaLinkedIn || ShareViaGoogle
	// #define SHARE
	// #endif

	// #ifdef PlainExport
	// #define PLAIN_TXT_FORMAT="txt"
	// #expand private static final String PLAIN_TXT_FORMAT = "%PLAIN_TXT_FORMAT%";
//@	private static final String PLAIN_TXT_FORMAT = "%PLAIN_TXT_FORMAT%";
	// #endif

	// #ifdef MarkdownExport
	// #define MARKDOWN_FORMAT="md"
	// #expand private static final String MARKDOWN_FORMAT = "%MARKDOWN_FORMAT%";
//@	private static final String MARKDOWN_FORMAT = "%MARKDOWN_FORMAT%";
	// #endif

	// #ifdef IcsExport
	// #define ICS_FORMAT="ics"
	// #expand private static final String ICS_FORMAT = "%ICS_FORMAT%";
//@	private static final String ICS_FORMAT = "%ICS_FORMAT%";
	// #endif

	// #ifdef HtmlExport
	// #define HTML_FORMAT="html"
	// #expand private static final String HTML_FORMAT = "%HTML_FORMAT%";
	private static final String HTML_FORMAT = "html";
	// #endif

	// #ifdef CsvExport
	// #define CSV_FORMAT="csv"
	// #expand private static final String CSV_FORMAT = "%CSV_FORMAT%";
	private static final String CSV_FORMAT = "csv";
	// #endif

	private final Set<Exporter> exporters;
	private final String menuString;

	public ExportMenu(final String key, final String desc) {
		super(key, desc);
		exporters = new HashSet<Exporter>();

		// #ifdef PLAIN_TXT_FORMAT
//@		exporters.add(new PlainExporter(PLAIN_TXT_FORMAT));
		// #endif

		// #ifdef MARKDOWN_FORMAT
//@		exporters.add(new MarkdownExporter(MARKDOWN_FORMAT));
		// #endif

		// #ifdef ICS_FORMAT
//@		exporters.add(new IcsExporter(ICS_FORMAT));
		// #endif

		// #ifdef CSV_FORMAT
		exporters.add(new CsvExporter(CSV_FORMAT));
		// #endif

		// #if HTML_FORMAT && !SHARE
		exporters.add(new HtmlExporter(HTML_FORMAT));
		// #elif HTML_FORMAT && SHARE
//@		exporters.add(new HtmlShareExporter(HTML_FORMAT));
		// #endif

		final StringBuilder sb = new StringBuilder();
		sb.append(App.PROMPT_BOLD + "export menu:\n" + App.PROMPT_NORMAL);
		sb.append(String.format("%10s - Exits export menu\n", "[exit]"));
		for (final Exporter p : exporters) {
			sb.append(String.format("%10s - %s%n", "[" + p.name() + "]", "save meetings into file with " + p.name()
							+ "-format (add '_p' for preview only)"));
		}
		menuString = sb.toString();
	}

	@Override
	public void action() {
		clear();
		println(menuString);
		String input;
		while (true) {
			input = readLine();
			if (EXIT.equals(input)) {
				break;
			}
			for (final Exporter exporter : exporters) {
				if (exporter.name().equals(input)) {
					exporter.toFile();
				} else if ((exporter.name() + "_p").equals(input)) {
					println(exporter.format());
				}
			}
		}
	}

	@Override
	protected void println(final Object obj) {
		App.println(obj, "calendar/" + key);
	}

	@Override
	protected String readLine() {
		return App.readLine("", "calendar/" + key);
	}
}
