// #condition ShareViaGoogle
//@package de.tubs.cs.isf.spl.jorg.calendar.exports.share;
//@
//@/**
//@ * @author rose
//@ * @date 26.05.15.
//@ */
//@public class GoogleSharer extends Sharer {
//@
//@	protected GoogleSharer(final String name) {
//@		super(name);
//@	}
//@
//@	@Override
//@	protected String script() {
//@		return "<script type=\"text/javascript\">\n" + "\tfunction " + name + "() {\n"
//@						+ "\t\tvar url = \"https://plus.google.com/share?url=\" + document.URL;\n"
//@						+ "\t\twindow.open(url);\n" + "\t}\n" + "</script>";
//@	}
//@}
