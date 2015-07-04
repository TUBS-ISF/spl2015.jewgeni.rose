package jorg.calendar;

import jorg.calendar.imports.ImportMenu;

/**
 * @author rose
 */
public class Calendar extends BasicFeature {

	public Calendar(final String key, final String desc) {
		original(key, desc);
		addFeature(new ImportMenu());
	}
}
