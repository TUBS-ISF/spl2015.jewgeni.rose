package jorg.calendar.imports;

import static jorg.App.EXIT;
import static jorg.App.clear;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import jorg.App;
import jorg.BasicFeature;
import jorg.calendar.Calendar;

public abstract aspect Importer {

	after(Calendar cal): target(cal) && execution(Calendar.new(String, String)) {
		cal.addFeature(new ImportMenu());
	}

	protected final String name, ext;

	protected Importer(final String name) {
		this.name = name;
		this.ext = name;
	}

	public String name() {
		return name;
	}

	protected abstract void writeIntoCal(final List<String> data);

	public void loadFromFile(final String path) {
		try {
			if (path.endsWith(ext)) {
				writeIntoCal(Files.readAllLines(Paths.get(path)));
			}
		} catch (final IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Couldn't read from '" + path + "'", ex);
		}
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Importer) {
			final Importer other = (Importer) obj;
			return name.equals(other.name);
		}
		return false;
	}
}

class ImportMenu extends BasicFeature {

	private final Set<Importer> importers;
	private final StringBuilder menuString;

	public ImportMenu() {
		super("import", "import function for meetings");
		importers = new HashSet<Importer>();

		menuString = new StringBuilder();
		menuString.append(App.PROMPT_BOLD + "import menu:\n" + App.PROMPT_NORMAL);
		menuString.append(String.format("%10s - Exits export menu\n", "[exit]"));
	}

	public void addImporter(final Importer imp) {
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
	public void println(final Object obj) {
		App.println(obj, "calendar/" + key);
	}

	@Override
	protected String readLine() {
		return App.readLine("", "calendar/" + key);
	}
}