package jorg.app_features;

import jorg.App;
import jorg.BasicFeature;

import java.util.Stack;

import static jorg.App.EXIT;
import static jorg.App.clear;

public aspect Notes extends BasicFeature {

	after(App app): target(app) && execution(void App.init()) {
		app.addFeature(this);
	}

	private static final String ADD_NOTE = "add";
	private static final String VIEW_LAST_NOTE = "show";

	final Stack<Note> notes;
	final StringBuilder menuString;

	public Notes() {
		super("notes", "write down some notes");
		notes = new Stack<Note>();

		menuString = new StringBuilder();
		menuString.append(App.PROMPT_BOLD + "notes menu:\n" + App.PROMPT_NORMAL);
		menuString.append(String.format("%10s - exits notes menu\n", "[" + EXIT + "]"));
		menuString.append(String.format("%10s - add a new note\n", "[" + ADD_NOTE + "]"));
		menuString.append(String.format("%10s - view last note\n", "[" + VIEW_LAST_NOTE + "]"));
	}

	public void add(final String title, final String desc) {
		final Note note = new Note(title, desc);
		notes.clear();
		notes.push(note);
	}

	public void view() {
		if (!notes.empty()) {
			println(notes.peek());
		}
	}

	@Override
	public void action() {
		String input;
		clear();
		println(menuString);
		while (true) {
			input = readLine();

			if (choose(input))
				break;
		}
	}

	private boolean choose(final String input) {
		if (EXIT.equals(input)) {
			return true;
		} else if (ADD_NOTE.equals(input)) {
			final String title = readLine("Title: ");
			final String note = readLine("Note: ");
			add(title, note);
		} else if (VIEW_LAST_NOTE.equals(input)) {
			view();
		} else {
			printErr("Invalid option");
		}
		return false;
	}

	public static class Note {

		final String note, title;

		public Note(final String title, final String note) {
			this.title = title;
			this.note = note;
		}

		@Override
		public int hashCode() {
			return title.hashCode();
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Note other = (Note) obj;
			return !((this.title == null) ? (other.title != null) : !this.title.equals(other.title));
		}

		@Override
		public String toString() {
			return App.PROMPT_BOLD + title + App.PROMPT_NORMAL + ":\n  " + note();
		}

		private String note() {
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < note.length(); i++) {
				if ((i + 1) % 40 == 0) {
					sb.append("\n  ");
				}
				sb.append(note.charAt(i));
			}
			return sb.toString();
		}
	}
}
