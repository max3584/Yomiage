package org.Readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.Readers.Directory.DirectoryUseSearch;

public class FileRead {
	
	// ディレクトリ保存用
	private File dir;
	//ファイルリードのバッファーをかませるためのもの
	private FileReader fr;
	//メインで使用するファイル読み込み
	private BufferedReader br;
	
	//コンストラクタ
	public FileRead(File dir, String fileName) throws FileNotFoundException {
		this.dir = dir;
		this.fr = new FileReader(this.dir.getPath() + fileName);
		this.br = new BufferedReader(fr);
	}
	
	// コンストラクタ２
	public FileRead(File dir) throws FileNotFoundException {
		this.dir = dir;
		this.fr = new FileReader(this.dir);
		this.br = new BufferedReader(fr);
	}
	
	// @Overload
	public FileRead(String dir) throws FileNotFoundException {
		this(new File(dir));
	}
	
	//引数なしコンストラクタ
	public FileRead() {
		this.dir = null;
		this.fr = null;
		this.br = null;
	}
	
	public ArrayList<String> Reads() throws IOException {
		
		if (this.dir.equals(null)) {
			return WhatRead();
		}
		
		ArrayList<String> result = new ArrayList<String>();
		while (br.ready()) {
			result.add(br.readLine());
		}
		this.br.close();
		return result;
	}
	
	public ArrayList<String> WhatRead() throws FileNotFoundException, IOException{
		
		DirectoryUseSearch dus = new DirectoryUseSearch();
		
		String dir = dus.search();
		
		this.dir = new File(dir);
		this.fr = new FileReader(this.dir.getName());
		this.br = new BufferedReader(fr);
		
		return Reads();
	}
}
