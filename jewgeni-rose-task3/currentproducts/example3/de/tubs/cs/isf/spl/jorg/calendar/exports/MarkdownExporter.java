// #condition MarkdownExport
//@
//@package de.tubs.cs.isf.spl.jorg.calendar.exports;
//@
//@import static de.tubs.cs.isf.spl.jorg.App.app;
//@import de.tubs.cs.isf.spl.jorg.calendar.Meeting;
//@
//@/**
//@ * @author rose
//@ */
//@public class MarkdownExporter extends Exporter {
//@
//@	public MarkdownExporter(final String name) {
//@		super(name);
//@	}
//@
//@	@Override
//@	public String format() {
//@		final StringBuilder sb = new StringBuilder();
//@
//@		sb.append("# Meetings for '").append(app().currentUser()).append("'\n");
//@		sb.append("---  \n");
//@		sb.append("| Title | Place | Date | Duration | Note |").append("\n");
//@		sb.append("| ----- | ----- | ---- | -------- | ---- |").append("\n");
//@		for (final Meeting m : app().calendar().meetings()) {
//@			sb.append("| ").append(m.title()).append(" | ").append(m.place()).append(" | ")
//@							.append(String.format("%1$te. %1$tB %1$tY", m.date())).append(" | ")
//@							.append(m.duration().toMinutes()).append(" min").append(" | ").append(m.note())
//@							.append(" |\n");
//@		}
//@		return sb.toString();
//@	}
//@}
