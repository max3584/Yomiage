package Test.Debug;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.CLI.CEExpress;
import org.Datas.ChatData;
import org.Readers.FileRead;

public class TestRequests {

	public static void main(String[] args) {

		String dir = args[0] + "\\ChatLog20200720_00.txt";

		String format = "";

		// 読み上げない文字列を消すための領域
		// la
		String regex1 = "(^|\\s)/(f|m|c)?la\\s\\w*(\\ss\\d)?";
		// sr
		String regex2 = "/(sr|s\\w*)?\\s[(r|l|R|C|L)+(\\w|\\W)*]+\\s";
		// ci
		String regex3 = "(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4})";
		// on off
		String regex4 = "/\\w+\\s*(on|off)\\s*\\d*\\s*;(^|\\s)/\\w*";
		// cmf
		String regex5 = "(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\W]+)){1,2}\\s*";
		// {colorchanges}
		String regex6 = "\\{[a-zA-Z+-]*\\}";
		// 伏字
		String regex7 = "[\\\\\\{\\}\\|\\[\\]'\\(\\)<>#\\%*\\+\\-\\?_？ω＿／＼]+";

		String regex8 = "^\\s";
		String regex9 = "(^|\\s)?/\\w+";

		try {
			FileRead fr = new FileRead(dir, Charset.forName("UTF-16LE"));
			ArrayList<ChatData> list = fr.formatRead(6);

			for (int i = 0; i < list.size(); i++) {
				System.out.println(String.format("date:%s\tno:%s\tgroup:%s\tsirial:%s\tuser:%s\tcomment:%s",
						list.get(i).getDate(), list.get(i).getNo(), list.get(i).getGroup(),
						list.get(i).getSirial(), list.get(i).getUser(),
						list.get(i).getComment().replaceAll(regex1, "").replaceAll(regex2, "")
								.replaceAll(regex3, "").replaceAll(regex4, "")
								.replaceAll(regex5, "").replaceAll(regex6, "")
								.replaceAll(regex7, "").replaceAll(regex8, "")
								.replaceAll(regex9, "").replace("\'", "").replace("\"", "")));
				format = String.format("user:%s\tcomment:%s", list.get(i).getUser(),
						list.get(i).getComment().replaceAll(regex1, "").replaceAll(regex2, "")
								.replaceAll(regex3, "").replaceAll(regex4, "")
								.replaceAll(regex5, "").replaceAll(regex6, "")
								.replaceAll(regex7, "").replaceAll(regex8, "")
								.replaceAll(regex9, "").replace("\'", "").replace("\"", ""));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * try { YomiageSocketConnect ysc = new YomiageSocketConnect();
		 * ysc.RequestPacket("voice test"); ysc.close(); } catch (IOException e) { //
		 * TODO 自動生成された catch ブロック e.printStackTrace(); }
		 */
		/*
		 * CEExpress cee = new CEExpress(); System.out.println(format);
		 * cee.ConsoleCommand(String.valueOf(50001), format);
		 */
	}
}
