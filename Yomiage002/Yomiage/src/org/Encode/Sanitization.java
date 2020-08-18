package org.Encode;

public class Sanitization {

	public String sanitiza(String src) {
		if(src.indexOf("'") > 0) {
			src = String.format("\"%s\"", src);
		}
		return src;
	}
	
	public String sanitiza2(String src) {
		return src.replaceAll("^\"|\"$", "").replaceAll("\'", "&27");
	}
}
