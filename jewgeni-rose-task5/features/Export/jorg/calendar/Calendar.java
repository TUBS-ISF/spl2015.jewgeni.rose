package jorg.calendar;

import jorg.calendar.exports.ExportMenu;

/**
 * @author rose
 */
public class Calendar extends BasicFeature {

	public Calendar(final String key, final String desc) {
		original(key, desc);
		addFeature(new ExportMenu());
	}
}
