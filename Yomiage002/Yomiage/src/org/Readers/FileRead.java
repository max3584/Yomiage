package org.Readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.util.ArrayList;

import org.Datas.DataLists;
import org.Datas.TabDatas;
import org.Readers.Directory.DirectoryUseSearch;

public class FileRead {
	
	// ディレクトリ保存用
	private File dir;
	//メインで使用するファイル読み込み
	private BufferedReader br;
	
	//コンストラクタ
	public FileRead(File dir, String fileName, Charset encode) throws IOException {
		this.dir = new File(dir.getPath() + "\\" + fileName);
		this.br = Files.newBufferedReader(this.dir.toPath(),  encode);
	}
	
	// コンストラクタ２
	public FileRead(File dir, Charset encode) throws IOException {
		this.dir = dir;
		this.br = Files.newBufferedReader(this.dir.toPath(),  encode);
	}
	
	// @Overload
	public FileRead(String dir, Charset encode) throws IOException {
		this(new File(dir),  encode);
	}
	
	//引数なしコンストラクタ
	public FileRead() {
		this.dir = null;
		this.br = null;
	}
	
	public ArrayList<String> Reads() throws IOException, InterruptedException {
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
	
	public ArrayList<DataLists> formatRead(int col) throws IOException {
		if(this.dir.equals(null)) {
			new IOException("ディレクトリに何も入っていないため");
		}
		
		ArrayList<DataLists> result = new ArrayList<DataLists>();
		TabDatas td = new TabDatas();
		try {
			for(String line : this.Reads()) {
				String[] dump = td.TabInsert(line);
				if(dump.length == col) {
					result.add(new DataLists(dump));
				} else {
					if("".equals(line)) {
						continue;
					}
					String prevComment = result.get(result.size() - 1).getComment();
					String dumpComment = "";
					for (int i = 0; i < dump.length; i++) {
						dumpComment += dump[i];
					}
					result.get(result.size() - 1).setComment(prevComment + dumpComment);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.br.close();
		return result;
		
	}
	
	public ArrayList<String> WhatRead() throws FileNotFoundException, IOException, InterruptedException{
		
		DirectoryUseSearch dus = new DirectoryUseSearch();
		
		String dir = dus.search();
		
		this.dir = new File(dir);
		this.br = Files.newBufferedReader(this.dir.toPath());
		
		return Reads();
	}
}
