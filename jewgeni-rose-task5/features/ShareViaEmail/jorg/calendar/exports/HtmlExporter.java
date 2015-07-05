package jorg.calendar.exports;

import jorg.calendar.exports.share.*;

/**
 * @author rose
 */
public class HtmlExporter extends Exporter {

	public HtmlExporter() {
		sharers.add(new EmailSharer());
	}
}
