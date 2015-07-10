package jorg.app_features;

import jorg.app_features.Notes.Note;

public aspect NotesWithHistory {

	private static final String REMOVE_NOTE = "remove";
	private static final String LIST_ALL_NOTES = "list";

	after(Notes n): target(n) && execution(Notes.new()) {
		n.menuString.append(String.format("%10s - remove a note\n", "[" + REMOVE_NOTE + "]"));
		n.menuString.append(String.format("%10s - list all notes\n", "[" + LIST_ALL_NOTES + "]"));
	}

	void around(Notes n, String title, String desc): target(n) && execution(void Notes.add(String, String)) && args(title, desc) {
		final Note note = new Note(title, desc);
		if (n.notes.contains(note)) {
			n.notes.remove(note);
		}
		n.notes.push(note);
	}

	private boolean Notes.remove(final String title) {
		return notes.remove(new Note(title, null));
	}

	private void Notes.list() {
		final StringBuilder sb = new StringBuilder();
		for (final Note note : notes) {
			sb.append(note.toString()).append("\n").append("-----------------------\n");
		}
		println(sb);
	}

	boolean around(Notes n, String input): target(n) && execution(boolean Notes.choose(String)) && args(input) {
		if (REMOVE_NOTE.equals(input)) {
			final String title = n.readLine("Title: ");
			if (n.remove(title)) {
				n.println("Note successfully removed");
			} else {
				n.println("There is no such note as '" + title + "'!");
			}
		} else if (LIST_ALL_NOTES.equals(input)) {
			n.list();
		}
		return proceed(n, input);
	}
}
