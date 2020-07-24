package org.Request;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class InitPropertiesFile {
	
	private String fileName;
	private Properties properties;
	
	public InitPropertiesFile(String filename) {
		this.fileName = filename;
		this.properties = new Properties();
		try {
			FileInputStream iostream = new FileInputStream(this.fileName);
			this.properties.load(iostream);
			iostream.close();
		}catch(FileNotFoundException e) {
			// 読み込めなかった場合の処理
			this.properties.setProperty("Read", "none");
			try {
				this.properties.store(new FileOutputStream(this.fileName), "Yomiage Properties");
			} catch (FileNotFoundException e1) {
			} catch (IOException e1) {
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Change(String value) {
		this.properties.setProperty("Read", value);
	}

	// getter and setter
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
