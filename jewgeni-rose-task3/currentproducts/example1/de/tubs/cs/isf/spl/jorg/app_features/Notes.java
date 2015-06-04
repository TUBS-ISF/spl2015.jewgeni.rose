// #condition NOTES
//@package de.tubs.cs.isf.spl.jorg.app_features;
//@
//@import static de.tubs.cs.isf.spl.jorg.App.EXIT;
//@import static de.tubs.cs.isf.spl.jorg.App.clear;
//@
//@import java.util.Stack;
//@
//@import de.tubs.cs.isf.spl.jorg.App;
//@import de.tubs.cs.isf.spl.jorg.Feature;
//@
//@/**
//@ *
//@ * @author rose
//@ */
//@public class Notes extends Feature {
//@
//@	private static final String ADD_NOTE = "add";
//@	private static final String VIEW_LAST_NOTE = "show";
//@
	// #ifdef NotesWithHistory
//@	private static final String REMOVE_NOTE = "remove";
//@	private static final String LIST_ALL_NOTES = "list";
	// #endif
//@
//@	private final Stack<Note> notes;
//@	private final String menuString;
//@
//@	public Notes(final String key, final String desc) {
//@		super(key, desc);
//@		notes = new Stack<Note>();
//@
//@		final StringBuilder sb = new StringBuilder();
//@		sb.append(App.PROMPT_BOLD + "notes menu:\n" + App.PROMPT_NORMAL);
//@		sb.append(String.format("%10s - exits notes menu\n", "[" + EXIT + "]"));
//@		sb.append(String.format("%10s - add a new note\n", "[" + ADD_NOTE + "]"));
//@		sb.append(String.format("%10s - view last note\n", "[" + VIEW_LAST_NOTE + "]"));
//@
		// #ifdef NotesWithHistory
//@		sb.append(String.format("%10s - remove a note\n", "[" + REMOVE_NOTE + "]"));
//@		sb.append(String.format("%10s - list all notes\n", "[" + LIST_ALL_NOTES + "]"));
		// #endif
//@		menuString = sb.toString();
//@	}
//@
//@	private void add(final String title, final String desc) {
//@		final Note note = new Note(title, desc);
//@
		// #ifndef NotesWithHistory
		// @ notes.clear();
		// #else
//@		if (notes.contains(note)) {
//@			notes.remove(note);
//@		}
		// #endif
//@		notes.push(note);
//@	}
//@
	// #ifdef NotesWithHistory
//@	private boolean remove(final String title) {
//@		return notes.remove(new Note(title, null));
//@	}
//@
//@	private void list() {
//@		final StringBuilder sb = new StringBuilder();
//@		for (final Note note : notes) {
//@			sb.append(note.toString()).append("\n").append("-----------------------\n");
//@		}
//@		println(sb);
//@	}
//@
	// #endif
//@
//@	private void viewLast() {
//@		if (!notes.empty()) {
//@			println(notes.peek());
//@		}
//@	}
//@
//@	@Override
//@	public void action() {
//@		String input;
//@		clear();
//@		println(menuString);
//@		while (true) {
//@			input = readLine();
//@
//@			if (EXIT.equals(input)) {
//@				break;
//@			} else if (ADD_NOTE.equals(input)) {
//@				final String title = readLine("Title: ");
//@				final String note = readLine("Note: ");
//@
//@				add(title, note);
//@			} else if (VIEW_LAST_NOTE.equals(input)) {
//@				viewLast();
//@			}
			// #if NotesWithHistory
//@			else if (REMOVE_NOTE.equals(input)) {
//@				final String title = readLine("Title: ");
//@
//@				if (remove(title)) {
//@					println("Note successfully removed");
//@				} else {
//@					println("There is no such note as '" + title + "'!");
//@				}
//@			} else if (LIST_ALL_NOTES.equals(input)) {
//@				list();
//@			}
			// #endif
//@			else {
//@				printErr("Invalid option");
//@			}
//@		}
//@	}
//@
//@	public static class Note {
//@
//@		final String note, title;
//@
//@		public Note(final String title, final String note) {
//@			this.title = title;
//@			this.note = note;
//@		}
//@
//@		@Override
//@		public int hashCode() {
//@			return title.hashCode();
//@		}
//@
//@		@Override
//@		public boolean equals(final Object obj) {
//@			if (obj == null) {
//@				return false;
//@			}
//@			if (getClass() != obj.getClass()) {
//@				return false;
//@			}
//@			final Note other = (Note) obj;
//@			return !((this.title == null) ? (other.title != null) : !this.title.equals(other.title));
//@		}
//@
//@		@Override
//@		public String toString() {
//@			return App.PROMPT_BOLD + title + App.PROMPT_NORMAL + ":\n  " + note();
//@		}
//@
//@		private String note() {
//@			final StringBuilder sb = new StringBuilder();
//@			for (int i = 0; i < note.length(); i++) {
//@				if ((i + 1) % 40 == 0) {
//@					sb.append("\n  ");
//@				}
//@				sb.append(note.charAt(i));
//@			}
//@			return sb.toString();
//@		}
//@	}
//@}
