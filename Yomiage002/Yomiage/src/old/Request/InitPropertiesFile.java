package old.Request;

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
 */

public class InitPropertiesFile {
	
	/**
	 * @param fileName		プロパティファイルの名前を格納するためのフィールド
	 */
	private String fileName;
	 /**
	  * @param properties		プロパティファイルを操作するためのフィールド
	 */
	private Properties properties;
	
	/**
	 * プロパティファイル初期設定コンストラクタ
	 * @param filename
	 */
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
	
	/**
	 * プロパティの値を変更するための関数
	 * @param value 変更する値
	 * @throws FileNotFoundException　プロパティファイルが存在しない場合に発生します
	 * @throws IOException　プロパティファイルが読み込み書き込みができない場合に発生します
	 */
	public void Change(String value) throws FileNotFoundException, IOException {
		this.properties.setProperty("Read", value);
		this.properties.store(new FileOutputStream(this.fileName), "Yomiage Propeties");
	}

	// getter and setter
	/**
	 * ファイル名取得
	 * @return　ファイル名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * ファイル名設定
	 * @param fileName　ファイル名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * プロパティファイル取得
	 * @return プロパティクラス
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * プロパティクラス設定
	 * @param properties プロパティクラス
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
