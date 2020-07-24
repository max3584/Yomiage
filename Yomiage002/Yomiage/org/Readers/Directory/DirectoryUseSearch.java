package org.Readers.Directory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.Request.UserRequest;
import org.Readers.Directory.Files;
import org.Readers.Directory.Folders;

public class DirectoryUseSearch implements Directory {

	private File dir;

	private ArrayList<Files> files;
	private ArrayList<Folders> folders;

	public DirectoryUseSearch() {
		this.files = new ArrayList<Files>();
		this.folders = new ArrayList<Folders>();
	}

	public DirectoryUseSearch(String dir) {
		this();
		this.dir = new File(dir);
		if (this.dir.isFile()) {
			this.files.add(new Files(this.dir.getName()));
		} else {
			this.folders.add(new Folders(this.dir.getName()));
		}
	}

	@Override
	public String search(String directory) throws FileNotFoundException {

		this.dir = new File(directory);
		if (!this.dir.exists())
			throw new FileNotFoundException();
		
		if(this.dir.isFile()) {
			this.files.add( new Files(this.dir.getName()));
			return this.dir.toString();
		}

		// 一覧格納
		for (File list : this.dir.listFiles()) {
			if (list.isFile()) {
				this.files.add(new Files(list.getName()));
			} else {
				this.folders.add(new Folders(list.getName()));
			}
		}

		return this.dir.getPath();
	}

	@Override
	public String search() {
		try {
			if (this.dir instanceof File) {
				return this.search(this.dir.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// 入力処理用
		UserRequest ur = new UserRequest();

		// 一時保存用のディレクトリです。
		this.dir = new File(ur.UserInputRequest("ディスクドライブ+ディレクトリを記入してください"));
		// ユーザ入力用のもの
		String use = null;
		
		// flag
		boolean flg = true;
		System.out.printf("現在ディレクトリの一覧です。\n path:%s\n", this.dir.getPath());

		// 一覧を出力
		for (File list : this.dir.listFiles()) {
			String msg = list.getName();
			System.out.println((list.isFile() ? new Files().getMsg() : new Folders().getMsg()) + msg);
		}

		// ユーザー入力でディレクトリのフルパスを取る
		do {
			

			// ユーザ入力
			use = ur.UserInputRequest(">");
			
			String stack = this.dir.getPath();

			// 存在するのかの確認
			if (new File(String.format("%s\\%s", this.dir.getPath(), use)).exists()) {
				this.dir = new File(String.format("%s\\%s", this.dir.getPath(), use));
				if (this.dir.isFile()) {
					do {
						System.out.println(String.format("現在のパスは%sです。", this.dir.toString()));
						System.out.println("これでよろしいですか？[y/n]");
						use = ur.UserInputRequest(">");
					}while(!use.equals("y") & !use.equals("n"));
					if(use.equals("n")) {
						this.dir = new File(stack);
					} else {
						flg = false;
						break;
					}
				}
			} else  {	//ディレクトリ・ファイルでエラーで、なおファイル名には存在する場合
				System.out.printf("現在ディレクトリの一覧です。\n path:%s\n", this.dir.getPath());
				boolean msgflg = false;
				// 一覧を出力
				for (File list : this.dir.listFiles()) {
					if(list.getName().contains(use)) {
						String msg = list.getName();
						System.out.println((list.isFile() ? new Files().getMsg() : new Folders().getMsg()) + msg);
						msgflg = (msg.equals(""))? false: true;
					}
				}
				if(msgflg) {
					continue;
				}
				
				System.out.println(String.format("%sの様なファイル・ディレクトリは存在しません\n再度入力をお願いします。", use));
				
			} 
			
			System.out.printf("現在ディレクトリの一覧です。\n path:%s\n", this.dir.getPath());

			// 一覧を出力
			for (File list : this.dir.listFiles()) {
				String msg = list.getName();
				System.out.println((list.isFile() ? new Files().getMsg() : new Folders().getMsg()) + msg);
			}

		} while (flg);

		return this.dir.toString();
	}
	

	@Override
	public String[] Files() {
		String dir  = this.DirectoryPath();
		ArrayList<String> dump = new ArrayList<String>();
		
		for(File FileLists : new File(dir).listFiles()) {
			if(FileLists.isFile())
				dump.add(FileLists.getName());
		}
		String[] Temp_Size = new String[dump.size()];
		String[] result = dump.toArray(Temp_Size);
		return result;
	}

	@Override
	public String DirectoryPath() {
		if(this.dir.isFile()) {
			String dim = this.dir.getPath();
			String[] names = dim.split("\\");
			
			StringBuffer sb = new StringBuffer();
			
			for(int i = 0; i < names.length - 2; i++) {
				sb.append(String.format("%s\\", names[i]));
			}
			return sb.substring(0);
		}
		return this.dir.toString();
	}

	// getter and setter
	public File getDir() {
		return dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}

	public void setDir(String dir) {
		this.dir = new File(dir);
	}

	public ArrayList<Files> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<Files> files) {
		this.files = files;
	}

	public ArrayList<Folders> getFolders() {
		return folders;
	}

	public void setFolders(ArrayList<Folders> folders) {
		this.folders = folders;
	}

}
