package old.Readers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.Datas.ChatData;
import org.Datas.TabDatas;
import old.Encode.Sanitization;

public class FileReadThred implements Runnable {

	private String dir;
	private ArrayList<String> sqls;

	public FileReadThred(String fileName) {
		this.dir = fileName;
		this.sqls = new ArrayList<String>();
	}
	
	@Override
	public void run() {
		
		this.fileReaders();
	}

	public void fileReaders() {

		//inits
		//sanitization class
		Sanitization san = new Sanitization();
		// datalist init(一時保存用)
		ArrayList<ChatData> dl = new ArrayList<ChatData>();
		try {
			FileRead fr = new FileRead(this.dir, StandardCharsets.UTF_16LE);
			TabDatas td = new TabDatas();
			// 読み取り機構
			try {
				for (String msg : fr.Reads()) {
					String[] dump = td.TabInsert(msg);

					if (dump.length == 6) {
						dl.add(new ChatData(dump[0], dump[1], dump[2], dump[3], dump[4], dump[5]));
					} else {
						if (dl.size() == 0) {
							continue;
						}
						String prevComment = dl.get(dl.size() - 1).getComment();
						String dumpComment = "";
						for (int i = 0; i < dump.length; i++) {
							dumpComment += dump[i];
						}
						dl.get(dl.size() - 1).setComment(prevComment + dumpComment);
					}
				}
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * SQLを一括処理に変更
		 */
		String AllSql="";
		
		for (ChatData dtl : dl) {
			// sqlformat
			String username = san.sanitiza2(dtl.getUser());
			String comment = san.sanitiza2(dtl.getComment());

			AllSql += String.format(
					"insert into Natu_data values ('%s', %s, '%s', %s, '%s', '%s');",
					dtl.getDate(), dtl.getNo(), dtl.getGroup(), dtl.getSirial(), username,
					comment);

			// sqldebug
			// System.out.println("sqlDebug:" + sqlFormat);
		}
		this.sqls.add(AllSql);
	}

	// getter and setter
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public ArrayList<String> getSqls() {
		return sqls;
	}

	public void setSqls(ArrayList<String> sqls) {
		this.sqls = sqls;
	}
}
