package jorg.calendar.imports;

/**
 * @author rose
 */
public class ImportMenu extends BasicFeature {

	public ImportMenu() {
		addImporter(new IcsImporter());
	}
}
