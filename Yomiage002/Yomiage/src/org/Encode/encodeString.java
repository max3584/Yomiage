package org.Encode;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class encodeString {

	public byte[] UTF_16LEencodeString(String message) {

		byte[] tmp = null;
		try {
			tmp = message.getBytes("UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should not possible
			AssertionError ae = new AssertionError("Could not encode UTF-16LE");
			ae.initCause(e);
			throw ae;
		}

		// use brute force method to add BOM
		byte[] utf16lemessage = new byte[2 + tmp.length];
		utf16lemessage[0] = (byte) 0xFF;
		utf16lemessage[1] = (byte) 0xFE;
		System.arraycopy(tmp, 0, utf16lemessage, 2, tmp.length);
		return utf16lemessage;
	}

	public byte[] encodeUTF16LEWithBOM(final String s) {
		ByteBuffer content = Charset.forName("UTF-16LE").encode(s);
		byte[] bom = { (byte) 0xff, (byte) 0xfe };
		return ByteBuffer.allocate(content.capacity() + bom.length).put(bom).put(content).array();
	}

	public String encodeSJIS(final String src) {
		try {
			final String encodeSelect = "Shift-JIS";
			byte[] encode = src.getBytes(Charset.forName(encodeSelect));

			return new String(encode, encodeSelect);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public String encodeUTF8(final String src) {
		try {
			byte[] encode = src.getBytes(StandardCharsets.UTF_8.toString());
			
			return new String(encode, "UTF-8");
		} catch(UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public String encodeWindows31J(final String src) {
		try {
			final String encodeSelect = "windows-31J";
			byte[] encode = src.getBytes(Charset.forName(encodeSelect));
			
			return new String(encode, encodeSelect);
		}catch(UnsupportedEncodingException e) {
			return null;
		}
	}
}
