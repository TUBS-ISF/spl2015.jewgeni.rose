package jorg.calendar.exports;

/**
 * @author rose
 */
public class HtmlExporter extends Exporter {

	public HtmlExporter() {
		original();
		sharers.add(new GoogleSharer());
	}
}
