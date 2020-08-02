package org.Readers.Directory;

public class Folders {
	
	private String directoryName;
	private final String msg = "Directory\t";

	public Folders() {
		
	}
	
	public Folders(String dir) {
		this.directoryName = dir;
	}

	// getter and setter
	public String getDirectoryName() {
		return this.directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public String getMsg() {
		return msg;
	}
	
}