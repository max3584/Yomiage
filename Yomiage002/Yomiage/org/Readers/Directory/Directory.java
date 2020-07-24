package org.Readers.Directory;

import java.io.FileNotFoundException;

public interface Directory {

	/**
	 * そのままFullPathを返すためのメソッド
	 * 
	 * @param directory 決まったPATHが存在する場合に使用します
	 * @return FullPathを返します。
	 */
	
	public String search(String directory) throws FileNotFoundException;
	
	/**
	 * ユーザが自分で入力していって最終的に決定したものをFullPathで出力するためのもの
	 * @return FullPathを返します
	 */
	public String search();
	
	/**
	 * ディレクトリファイルに入っているファイルパスすべてを出力
	 * @return FullPathを返します
	 */
	public String[] Files();
	
	/**
	 * ディレクトリのパスを出力(フィールドに入っているもののファイルフルパスではなく、それの1つ前を表示を行います。)
	 * @return ディレクトリパスを返します
	 */
	public String DirectoryPath();
	
}
