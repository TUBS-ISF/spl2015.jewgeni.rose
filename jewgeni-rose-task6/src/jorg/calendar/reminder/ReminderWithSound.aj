package jorg.calendar.reminder;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JOptionPane;

import jorg.calendar.Meeting;

public aspect ReminderWithSound {

	AbstractReminder around(ReminderMenu menu, final Meeting meeting, final Duration bef): 
		target(menu) && execution(* ReminderMenu.build(Meeting, Duration)) && args(meeting, bef) {
		return new SoundReminder(meeting, bef);
	}
}

class SoundReminder extends MuteReminder {

	SoundReminder(final Meeting meeting, final Duration bef) {
		super(meeting, bef);
	}

	@Override
	protected void play() {
		Sequencer sequencer = null;
		try {
			sequencer = MidiSystem.getSequencer();
			Sequence sequence = MidiSystem
							.getSequence(getClass().getClassLoader().getResource("media/sunshine.mid").openStream());

			sequencer.open();
			sequencer.setSequence(sequence);

			sequencer.start();

			JOptionPane.showMessageDialog(null,
							"Your meeting '" + meeting.title() + "' is about to begin in " + bef.toHours() + " hours",
							"Reminder", JOptionPane.OK_OPTION);
			sequencer.stop();
		} catch (final InvalidMidiDataException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (final IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (final MidiUnavailableException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (sequencer != null) {
				sequencer.close();
			}
		}
	}
}
