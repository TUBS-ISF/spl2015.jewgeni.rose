package jorg.app_features;

/**
 *
 * @author rose
 */
public class Notes extends BasicFeature {

	private static final String REMOVE_NOTE = "remove";
	private static final String LIST_ALL_NOTES = "list";

	public Notes() {
		original();
		menuString.append(String.format("%10s - remove a note\n", "[" + REMOVE_NOTE + "]"));
		menuString.append(String.format("%10s - list all notes\n", "[" + LIST_ALL_NOTES + "]"));
	}

	public void add(final String title, final String desc) {
		final Note note = new Note(title, desc);
		if (notes.contains(note)) {
			notes.remove(note);
		}
		notes.push(note);
	}

	public boolean remove(final String title) {
		return notes.remove(new Note(title, null));
	}

	public void list() {
		final StringBuilder sb = new StringBuilder();
		for (final Note note : notes) {
			sb.append(note.toString()).append("\n").append("-----------------------\n");
		}
		println(sb);
	}

	private boolean choose(final String input) {
		if (REMOVE_NOTE.equals(input)) {
			final String title = readLine("Title: ");
			if (remove(title)) {
				println("Note successfully removed");
			} else {
				println("There is no such note as '" + title + "'!");
			}
		} else if (LIST_ALL_NOTES.equals(input)) {
			list();
		} else
			original(input);
	}
}
