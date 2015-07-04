package jorg.calendar.exports;

import jorg.App;
import jorg.BasicFeature;

import java.util.HashSet;
import java.util.Set;

import static jorg.App.EXIT;
import static jorg.App.clear;

/**
 * @author rose
 */
public class ExportMenu extends BasicFeature {

    private final Set<Exporter> exporters;
    private final StringBuilder menuString;

    public ExportMenu() {
        super("export", "export function for meetings");
        exporters = new HashSet<Exporter>();
       
        menuString = new StringBuilder();
        menuString.append(App.PROMPT_BOLD + "export menu:\n" + App.PROMPT_NORMAL);
        menuString.append(String.format("%10s - Exits export menu\n", "[exit]"));
    }
    
    private void addExporter(final Exporter exp) {
    	exporters.add(imp);
		menuString.append(String.format("%10s - %s%n", "[" + exp.name() + "]",
						"save meetings into file with " + exp.name() + "-format (add '_p' for preview only)"));
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
