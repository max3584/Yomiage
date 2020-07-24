package org.Encode;

import java.nio.charset.Charset;

public class CharDecode {

	private String defaultCode;
	private byte[] bytes;
	
	public CharDecode(String src) {
		this.defaultCode = src;
		this.bytes = this.defaultCode.getBytes();
	}
	
	public CharDecode(String src, String encode) {
		this.defaultCode = src;
		this.bytes = this.defaultCode.getBytes(Charset.forName(encode));
	}
	
	public String result() {
		String result = "";
		
		for(byte recoding : this.bytes) {
			result += String.format("%%%s%H", ((recoding & 0xFF) > 0x10)? "" : "0", recoding & 0xFF);
		}
		
		return result;
	}
}
