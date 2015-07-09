package jorg.calendar.imports;

import jorg.calendar.Calendar;

public aspect Imports {
	
	after(Calendar cal): target(cal) && execution(Calendar.new(String, String)) {
		cal.addFeature(new ImportMenu());
	}
}