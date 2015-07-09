package jorg.app_features;

import jorg.App;
import jorg.BasicFeature;

import javax.management.timer.Timer;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public aspect Alarm extends BasicFeature {

	pointcut alarm(App app): target(app) && execution(void App.init());

	after(App app): alarm(app) {
		app.addFeature(this);
	}

	private final LocalTime DEFAULT_TIME = LocalTime.of(8, 0);
	private final LocalDate DEFAULT_DATE = LocalDate.now().plusDays(1);
	private final Duration DEFAULT_SLEEP_TIME = Duration.ofMinutes(5);

	public Alarm() {
		super("alarm", "alarm function");
	}

	@Override
	public void action() {
		println("Setting up alarm ... \n");
		final String dateStr = readLine("Date [2015-04-30]: ");
		final LocalDate date;
		if (!dateStr.isEmpty()) {
			date = LocalDate.parse(dateStr);
		} else {
			date = DEFAULT_DATE;
		}

		final String beginStr = readLine("Start [08:00]: ");
		final LocalTime time;
		if (!beginStr.isEmpty()) {
			time = LocalTime.parse(beginStr);
		} else {
			time = DEFAULT_TIME;
		}

		final String mins = readLine("Sleep time [min]: ");
		Duration duration = DEFAULT_SLEEP_TIME;
		if (!mins.isEmpty()) {
			duration = Duration.ofMinutes(Long.parseLong(mins));
		}
		final LocalDateTime dateTime = date.atTime(time);

		if (LocalDateTime.now().isAfter(dateTime)) {
			printErr("Event is already over!");
		} else {
			final AlarmWorker alarm = new AlarmWorker(dateTime, duration);
			println("Set up alarm at '" + alarm + "'");
			SwingUtilities.invokeLater(alarm);
		}
	}
}

class AlarmWorker implements Runnable {

	private LocalDateTime time;
	private Duration sleep;

	AlarmWorker(final LocalDateTime time, final Duration sleep) {
		this.sleep = sleep;
		this.time = time;
	}

	private boolean play() {
		Sequencer sequencer = null;
		try {
			sequencer = MidiSystem.getSequencer();
			Sequence sequence = MidiSystem
							.getSequence(getClass().getClassLoader().getResource("media/sunshine.mid").openStream());
			sequencer.open();
			sequencer.setSequence(sequence);

			sequencer.start();

			if (JOptionPane.showConfirmDialog(null, "Sleep for another " + sleep.toMinutes() + " minutes?", "Alarm",
							JOptionPane.OK_OPTION) == JOptionPane.YES_OPTION) {
				return true;
			}
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
		return false;
	}

	public void run() {
		LocalDateTime now;
		while (true) {
			now = LocalDateTime.now();
			if (now.equals(time) || now.isAfter(time)) {
				if (!play()) {
					break;
				}
				time = time.plus(sleep);
			}
			try {
				Thread.sleep(Timer.ONE_MINUTE);
			} catch (InterruptedException ex) {
				Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("%1$te. %1$tB %1$tY, %1$tR o'clock", time);
	}
}