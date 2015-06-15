package de.tubs.cs.isf.spl.jorg.calendar.imports;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.clear;

import java.util.HashSet;
import java.util.Set;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;

/**
 * @author rose
 * @date 24.05.15.
 */
public class ImportMenu extends Feature {

	private static final String FEATURE_KEY = "import";
	private static final String FEATURE_DESC = "import function for meetings";

    private static final String ICS_FORMAT = "ics";
    private static final String CSV_FORMAT = "csv";

    private final Set<Importer> importers;
    private final String menuString;

	public ImportMenu() {
		super(FEATURE_KEY, FEATURE_DESC);
        importers = new HashSet<Importer>();

		// TODO load exports 
		for (final String opt : "".split(",")) {
            if (ICS_FORMAT.equals(opt)) {
                importers.add(new IcsImporter(ICS_FORMAT));
            } else if (CSV_FORMAT.equals(opt)) {
                importers.add(new CsvImporter(CSV_FORMAT));
            }
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "import menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - Exits export menu\n", "[exit]"));
        for (final Importer in : importers) {
            sb.append(String.format("%10s - %s%n", "[" + in.name() + "]",
                    "load meetings from file with " + in.name() + "-format (add '_p' for preview only)"));
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
