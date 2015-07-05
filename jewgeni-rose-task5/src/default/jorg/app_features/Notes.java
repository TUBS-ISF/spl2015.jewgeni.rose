package jorg.app_features; 

import jorg.App; 
import jorg.BasicFeature; 

import java.util.Stack; 

import static jorg.App.EXIT; 
import static jorg.App.clear; 

/**
 *
 * @author rose
 */
public   class  Notes  extends BasicFeature {
	

	private static final String ADD_NOTE = "add";

	
	private static final String VIEW_LAST_NOTE = "show";

	

	private final Stack<Note> notes;

	
	private final StringBuilder menuString;

	

	public Notes  () {
		super("notes", "write down some notes");
		notes = new Stack<Note>();

		menuString = new StringBuilder();
		menuString.append(App.PROMPT_BOLD + "notes menu:\n" + App.PROMPT_NORMAL);
		menuString.append(String.format("%10s - exits notes menu\n", "[" + EXIT + "]"));
		menuString.append(String.format("%10s - add a new note\n", "[" + ADD_NOTE + "]"));
		menuString.append(String.format("%10s - view last note\n", "[" + VIEW_LAST_NOTE + "]"));
	
		
		menuString.append(String.format("%10s - remove a note\n", "[" + REMOVE_NOTE + "]"));
		menuString.append(String.format("%10s - list all notes\n", "[" + LIST_ALL_NOTES + "]"));
	}

	

	public void add  (final String title, final String desc) {
		final Note note = new Note(title, desc);
		if (notes.contains(note)) {
			notes.remove(note);
		}
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

	

	 private boolean  choose__wrappee__Notes  (final String input) {
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
		} 
		return choose__wrappee__Notes(input);
	}

	

	public static  class  Note {
		

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

	

	private static final String REMOVE_NOTE = "remove";

	
	private static final String LIST_ALL_NOTES = "list";

	

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


}
