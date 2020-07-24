package org.Readers.Directory;

public class Files {

	private String FileName;
	private final String msg = "File\t\t";
	
	public Files() {
		
	}
	
	public Files(String fileName) {
		this.FileName = fileName;
	}

	// getter and setter
	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}
	
	public String getMsg() {
		return this.msg;
	}
}
