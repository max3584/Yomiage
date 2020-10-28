package org.Execution.subject;

import org.CLI.CEExpress;

public abstract class Yomiage {

	private String[] regexs = {
			"(^|\\s)/(f|m|c)?la\\s\\w*(\\ss\\d)?",	//la
			"/(sr|s\\w*)?\\s[(r|l|R|C|L)+(\\w|\\W)*]+\\s?", //sr
			"(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4})", //ci
			 "/\\w+\\s*(on|off)\\s*\\d*\\s*", //on off
			 "(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\W]+)){1,2}\\s*", //cmf
			 "\\{[a-zA-Z+-]*\\}", // {color Change}
			 "/\\w+", // /[a-zA-Z]*
			 "\\s" // 空白
			};

	private String sanitiza = "&27";
	
	private CEExpress cee;

	public String regexs(String text) {
		for (int i = 0; i < this.regexs.length; i++) {
			text = text.replaceAll(this.regexs[i], "");
		}
		return text;
	}
	
	public String sanitiza(String text) {
		return text.replaceAll("'", this.sanitiza);
	}
	
	public abstract void Request(int port, String user, String comment); 

	public CEExpress getCee() {
		return cee;
	}

	public void setCee(CEExpress cee) {
		this.cee = cee;
	}
}
