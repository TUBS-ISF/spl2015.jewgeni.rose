// #condition SHARE
//@package de.tubs.cs.isf.spl.jorg.calendar.exports.share;
//@
//@/**
//@ * @author rose
//@ * @date 25.05.15.
//@ */
//@public abstract class Sharer {
//@
//@	protected final String name;
//@
//@	protected Sharer(final String name) {
//@		this.name = name;
//@	}
//@
//@	public String content() {
//@		return script() + "\n" + link();
//@	}
//@
//@	protected abstract String script();
//@
//@	private String link() {
//@		return "<!-- " + name + " -->\n" + "<a href=\"javascript:void(0)\" onclick=\"" + name + "();\">"
//@						+ "<img src=\"http://www.simplesharebuttons.com/images/somacro/" + name + ".png\" " + "alt=\""
//@						+ name + "\" /></a>";
//@	}
//@}
