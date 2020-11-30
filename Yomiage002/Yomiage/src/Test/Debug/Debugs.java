package Test.Debug;

import org.Readers.Directory.*;
import org.Readers.*;
import org.Datas.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Debugs {

	public static void main(String[] args) {
		// DirectoryUseSearch のデバッグ
		DirectoryUseSearch dus = new DirectoryUseSearch();
		Date date = new Date();
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMdd");
		
		//insert datas
		TabDatas td = new TabDatas();
		ArrayList<ChatData> dl = new ArrayList<ChatData>();
		
		System.out.println(sdf.format(date));
		
		String dir;

		try {
			dir = dus.search(
					String.format("%s\\ChatLog%s_00.txt", args[0], sdf.format(date)));
			// System.out.println(dir);
			
			// 読み取りに必要なクラス
			FileRead fr = new FileRead(dir, StandardCharsets.UTF_8);
			// 読み取り機構
			try {
				for (String msg : fr.Reads()) {
					String[] dump = td.TabInsert(msg);
					System.out.println(msg);
					// debug
					for(int i = 0; i < dump.length; i++) {
						System.out.println(String.format("debug%d:%s", i, dump[i]));
					}
					System.out.println("debug size :" + dump.length);
					
					if (dump.length == 6) {
						dl.add( new ChatData(dump[0], dump[1], dump[2], dump[3], dump[4], dump[5]));
					} else {
						String prevComment = dl.get(dl.size() - 1).getComment();
						String dumpComment = "";
						for(int i = 0; i < dump.length; i++) {
							dumpComment += dump[i];
						}
						dl.get(dl.size() - 1).setComment(prevComment + dumpComment);
					}
					
					System.out.println();
				}
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//debug
			for(int i = 0; i < dl.size(); i++) {
				System.out.println(String.format("Date:%s\tNo:%s\tGroup:%s\tSirial:%s\nuser:%s\tcomment:%s", 
						dl.get(i).getDate(), dl.get(i).getNo(), dl.get(i).getGroup(), dl.get(i).getSirial(),dl.get(i).getUser(), dl.get(i).getComment()));
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
