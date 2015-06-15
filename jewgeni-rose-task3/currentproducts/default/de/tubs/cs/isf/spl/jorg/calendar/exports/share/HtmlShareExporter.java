// #condition SHARE
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

	// #ifdef ShareViaFacebook
	// #define FACEBOOK="facebook"
	// #expand private static final String FACEBOOK = "%FACEBOOK%";
	private static final String FACEBOOK = "facebook";
	// #endif

	// #ifdef ShareViaGoogle
	// #define GOOGLE="google"
	// #expand private static final String GOOGLE = "%GOOGLE%";
	private static final String GOOGLE = "google";
	// #endif

	// #ifdef ShareViaTwitter
	// #define TWITTER="twitter"
	// #expand private static final String TWITTER = "%TWITTER%";
	private static final String TWITTER = "twitter";
	// #endif

	// #ifdef ShareViaLinkedIn
	// #define LINKEDIN="linkedin"
	// #expand private static final String LINKEDIN = "%LINKEDIN%";
	private static final String LINKEDIN = "linkedin";
	// #endif

	// #ifdef ShareViaEmail
	// #define EMAIL="email"
	// #expand private static final String EMAIL = "%EMAIL%";
	private static final String EMAIL = "email";
	// #endif

	private final Set<Sharer> sharers;

	public HtmlShareExporter(final String key) {
		super(key);
		sharers = new HashSet<Sharer>();

		// #ifdef FACEBOOK
		sharers.add(new FacebookSharer(FACEBOOK));
		// #endif

		// #ifdef GOOGLE
		sharers.add(new GoogleSharer(GOOGLE));
		// #endif

		// #ifdef TWITTER
		sharers.add(new TwitterSharer(TWITTER));
		// #endif

		// #ifdef LINKEDIN
		sharers.add(new LinkedInSharer(LINKEDIN));
		// #endif

		// #ifdef EMAIL
		sharers.add(new EmailSharer(EMAIL));
		// #endif		
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader()
							.getResource("media/html_share_template").openStream()));
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
