package jorg.calendar.exports;

/**
 * @author rose
 */
public class ExportMenu extends BasicFeature {

    public ExportMenu() {
        addExporter(new IcsExporter());
    }
}
