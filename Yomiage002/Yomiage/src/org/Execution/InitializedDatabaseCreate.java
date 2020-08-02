package org.Execution;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class InitializedDatabaseCreate {

	private File file;
	private File sqlfile;
	private ArrayList<String> list;
	private StringBuffer sb;
	
	public InitializedDatabaseCreate(File file) throws IOException {
		this.file = file;
		this.sqlfile = new File(String.format("%s\\default\\createSQL.sql", this.file.getPath()));
		this.list = (ArrayList<String>) Files.lines(this.sqlfile.toPath(), StandardCharsets.UTF_8).collect(Collectors.toList());
		for(int i = 0; i < this.list.size(); i++) {
			sb.append(this.list.get(i));
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getSqlfile() {
		return sqlfile;
	}

	public void setSqlfile(File sqlfile) {
		this.sqlfile = sqlfile;
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	public StringBuffer getSb() {
		return sb;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
	
}
