package org.Request;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * 
 * @author max
 *
 * プロパティファイルを自動で生成または読み込みを行うクラスです。
 * プロパティファイルは主に読み込みのグループ分けや初期起動時なのかなどに使用します
 *
 *@param fileName		プロパティファイルの名前を格納するためのフィールド
 *@param properties		プロパティファイルを操作するためのフィールド
 */

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
			this.properties.setProperty("date", new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()));
			this.properties.setProperty("first", "true");
			try {
				this.properties.store(new FileOutputStream(this.fileName), "Yomiage Properties");
			} catch (FileNotFoundException e1) {
			} catch (IOException e1) {
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Change(String value) throws FileNotFoundException, IOException {
		this.properties.setProperty("Read", value);
		this.properties.store(new FileOutputStream(this.fileName), "Yomiage Propeties");
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
