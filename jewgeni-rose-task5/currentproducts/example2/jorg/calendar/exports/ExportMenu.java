package jorg.calendar.exports; 

/**
 * @author rose
 */
public   class  ExportMenu  extends BasicFeature {
	

    public ExportMenu  () {
        original();
        addExporter(new HtmlExporter());
    
        original();
        addExporter(new CsvExporter());
    }


}
