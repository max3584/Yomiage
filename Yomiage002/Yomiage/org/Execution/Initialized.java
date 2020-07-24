package org.Execution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.DataBase.DBAccess;

/**
 * ふと思ったけど必要ない・・・？
 * @author max
 *
 */

public class Initialized {

	private File file;
	private DBAccess db;

	public Initialized() {
		try {
			this.file = new File(".\\ExtendFiles\\");
			// mkdir
			if (this.file.mkdir()) {
				System.out.println("ExtendFile Create Complete!");
			}
			
			File DatabaseFile = new File(".\\ExtendFiles\\controlData.db");
			DatabaseFile.createNewFile();
			
			this.db = new DBAccess(DatabaseFile.toString());
			
		} catch (Exception e) {
			
		}
		this.file = new File(".\\ExtendFiles\\");
	}
}
