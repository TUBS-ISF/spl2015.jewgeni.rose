package jorg.calendar.imports; 

import jorg.App; 
import jorg.BasicFeature; 

import java.util.HashSet; 
import java.util.Set; 

import static jorg.App.EXIT; 
import static jorg.App.clear; 

/**
 * @author rose
 */
public   class  ImportMenu  extends BasicFeature {
	

	private final Set<Importer> importers;

	
	private final StringBuilder menuString;

	

	public ImportMenu  () {
		super("import", "import function for meetings");
		importers = new HashSet<Importer>();

		menuString = new StringBuilder();
		menuString.append(App.PROMPT_BOLD + "import menu:\n" + App.PROMPT_NORMAL);
		menuString.append(String.format("%10s - Exits export menu\n", "[exit]"));
	
		original();
		addImporter(new CsvImporter());
	
		original();
		addImporter(new IcsImporter());
	}

	

	private void addImporter(final Importer imp) {
		importers.add(imp);
		menuString.append(String.format("%10s - %s%n", "[" + imp.name() + "]",
						"load meetings from file with " + imp.name() + "-format (add '_p' for preview only)"));
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
			for (final Importer importer : importers) {
				if (importer.name().equals(input)) {
					final String path = readLine("enter path to data: ");
					importer.loadFromFile(path);
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
