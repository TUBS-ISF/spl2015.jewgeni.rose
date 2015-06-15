package de.tubs.cs.isf.spl.jorg.calendar.exports.share;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import de.tubs.cs.isf.spl.jorg.calendar.exports.HtmlExporter;

/**
 * @author rose
 * @date 25.05.15.
 */
public class HtmlShareExporter extends HtmlExporter {

	private static final String FORMAT = "html";

    private static final String FACEBOOK = "facebook";
    private static final String GOOGLE = "google";
    private static final String TWITTER = "twitter";
    private static final String LINKEDIN = "linkedin";
    private static final String EMAIL = "email";

    private final Set<Sharer> sharers;

	public HtmlShareExporter() {
		super(FORMAT);
        sharers = new HashSet<Sharer>();

		// TODO load sharers
		for (final String sharer : "".split(",")) {
            if (FACEBOOK.equals(sharer)) {
                sharers.add(new FacebookSharer(FACEBOOK));
            } else if (GOOGLE.equals(sharer)) {
                sharers.add(new GoogleSharer(GOOGLE));
            } else if (TWITTER.equals(sharer)) {
                sharers.add(new TwitterSharer(TWITTER));
            } else if (LINKEDIN.equals(sharer)) {
                sharers.add(new LinkedInSharer(LINKEDIN));
            } else if (EMAIL.equals(sharer)) {
                sharers.add(new EmailSharer(EMAIL));
            }
        }
    }

    @Override
    public String format() {
        final StringBuilder sb = new StringBuilder();
        sb.append(generateHtmlTemplate());
        sb.append(generateHtmlHeader());

        sb.append(generateShareTemplate());
        for (final Sharer sharer : sharers) {
            sb.append(sharer.content()).append("\n\n");
        }
        sb.append(generateShareFooter());

        sb.append(generateTableHeader());
        sb.append(generateTableContent());
        sb.append(generateHtmlFooter());
        return sb.toString();
    }

    protected String generateShareTemplate() {
        final StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResource
                    ("media/html_share_template").openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
        }
        return sb.toString();
    }

    protected String generateShareFooter() {
        return "<hr></div>";
    }
}
