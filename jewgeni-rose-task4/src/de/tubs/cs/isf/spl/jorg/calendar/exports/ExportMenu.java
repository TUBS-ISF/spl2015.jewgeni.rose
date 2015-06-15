package de.tubs.cs.isf.spl.jorg.calendar.exports;

import static de.tubs.cs.isf.spl.jorg.App.EXIT;
import static de.tubs.cs.isf.spl.jorg.App.clear;

import java.util.HashSet;
import java.util.Set;

import de.tubs.cs.isf.spl.jorg.App;
import de.tubs.cs.isf.spl.jorg.Feature;

/**
 * @author rose
 */
public class ExportMenu extends Feature {

	private static final String FEATURE_KEY = "export";
	private static final String FEATURE_DESC = "export function for meetings";

    private final Set<Exporter> exporters;
    private final String menuString;

	public ExportMenu(final String printrs, final String sharers) {
		super(FEATURE_KEY, FEATURE_DESC);
        exporters = new HashSet<Exporter>();

		// TODO load printers to export
		for (final String featureClass : "".split(",")) {
			try {
				exporters.add((Exporter) Class.forName(featureClass).newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(App.PROMPT_BOLD + "export menu:\n" + App.PROMPT_NORMAL);
        sb.append(String.format("%10s - Exits export menu\n", "[exit]"));
        for (final Exporter p : exporters) {
            sb.append(String.format("%10s - %s%n", "[" + p.name() + "]",
                    "save meetings into file with " + p.name() + "-format (add '_p' for preview only)"));
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
