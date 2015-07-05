package jorg.calendar.exports.share; 

import static jorg.App.app; 

/**
 * @author rose
 */
public  class  TwitterSharer  extends Sharer {
	

    public TwitterSharer() {
        super("twitter");
    }

	

    @Override
    protected String script() {
        return "<script type=\"text/javascript\">\n" +
                "\tfunction " + name + "() {\n" +
                "\t\tvar url = \"http://twitter.com/share?url=\" + document.URL + \"&text=Meetings for " + app()
                .currentUser() + "\"&hashtags=jOrganizer\n" +
                "\t\twindow.open(url);\n" +
                "\t}\n" +
                "</script>";
    }


}
