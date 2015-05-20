package de.tubs.cs.isf.spl.jorg.calendar.reminder;

import de.tubs.cs.isf.spl.jorg.calendar.Meeting;
import java.io.File;
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

/**
 *
 * @author rose
 */
class SoundReminder extends MuteReminder {

    SoundReminder(final Meeting meeting, final Duration before) {
        super(meeting, before);
    }

    SoundReminder(final Reminder reminder) {
        this(reminder.meeting, reminder.before);
    }

    @Override
    protected void play() {
        Sequencer sequencer = null;
        try {
            sequencer = MidiSystem.getSequencer();
            Sequence sequence = MidiSystem.getSequence(new File("media/sunshine.mid"));

            sequencer.open();
            sequencer.setSequence(sequence);

            sequencer.start();

            JOptionPane.showMessageDialog(null, "Your meeting '" + meeting.getTitle() + "' is about to begin in "
                                          + before.toHours() + " hours", "Reminder", JOptionPane.OK_OPTION);
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
