package org.Encode;

import java.util.Arrays;
import java.util.ArrayList;

public class CharEncode {
	
	private char[] escape = {'\'', '"', '%', '<', '>', '\\', '|' };
	private String escapeChangeStr = ":escapeedit";

	public CharEncode() {
		
	}
	
	public String Encode(String src) {
		
		ArrayList<Byte> binary = new ArrayList<Byte>();
		
		int a = src.indexOf("%");
		
		do {
			
			String replacesrc = src.substring(a, a + 2).replace("%", "");
			src = src.replace(String.format("%%%s" , replacesrc), this.escapeChangeStr);
			binary.add(Byte.parseByte(replacesrc));
			a += 3;
			
		}while(a > 0);
		
		return "";
	}
}
