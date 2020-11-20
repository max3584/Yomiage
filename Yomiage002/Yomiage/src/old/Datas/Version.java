package old.Datas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Version {

	private String fileName;
	private Properties properties;
	
	public Version(String filename) {
		this.fileName = filename;
		this.properties = new Properties();
		try {
			FileInputStream iostream = new FileInputStream(this.fileName);
			this.properties.load(iostream);
			iostream.close();
		}catch(FileNotFoundException e) {
			// 読み込めなかった場合の処理
			this.properties.setProperty("version", "v0.0.8");
			try {
				this.properties.store(new FileOutputStream(this.fileName), "Version");
			} catch (FileNotFoundException e1) {
			} catch (IOException e1) {
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void StampVersion(String version) throws FileNotFoundException, IOException {
		this.properties.setProperty("version", version);
		this.properties.store(new FileOutputStream(this.fileName), "Version");
	}
	
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
