package org.Debug;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.CLI.CEExpress;
import org.Datas.DataLists;
import org.Readers.FileRead;

public class TestRequests {

	public static void main(String[] args) {
	
		
		String dir="C:\\Users\\pukug\\Documents\\SEGA\\PHANTASYSTARONLINE2\\log\\ChatLog20200720_00.txt";
		
		String format = "";
		
		try {
			FileRead fr = new FileRead(dir, Charset.forName("UTF-16LE"));
			ArrayList<DataLists> list = fr.formatRead(6);
			
			for(int i = 0; i < list.size(); i++) {
				System.out.println(String.format("date:%s\tno:%s\tgroup:%s\tsirial:%s\tuser:%s\tcomment:%s",
						list.get(i).getDate(), list.get(i).getNo(), list.get(i).getGroup(), list.get(i).getSirial(),
						list.get(i).getUser(), list.get(i).getComment()));
				format = String.format("user:%s\tcomment:%s",
						list.get(i).getUser(), list.get(i).getComment());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		try {
			YomiageSocketConnect ysc = new YomiageSocketConnect();
			ysc.RequestPacket("voice test");
			ysc.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		*/
		
		CEExpress cee = new CEExpress();
		System.out.println(format);
		cee.ConsoleCommand(String.valueOf(50001), format);
		
	}
}
