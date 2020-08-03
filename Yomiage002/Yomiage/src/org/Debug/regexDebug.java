package org.Debug;

public class regexDebug {

	public static void main(String[] args) {
		String text = "max:/p /cmf ＊神刀スサノオ /mpal1 /spal6 /sr Ｒ／Ｃストライク打撃 Cスト打";
		String text2 = "test: /la ichitaro2";
		// String regex =
		// "(^|\\s)/[a-zA-Z]*;(^|\\s)/la\\s\\w?(\\ss+\\d.\\d)?;(^|\\s)/ci\\d+?(\\s(\\d+|nw|t\\d|s\\d+)){1,4};(^|\\s)/cmf?\\b\\w?";
		// /la ichitaro;

		// la
		String regex1 = "(^|\\s)/(f|m|c)?la\\s\\w*(\\ss\\d)?";
		// sr
		String regex2 = "/(sr|s\\w*)?\\s[(r|l|R|C)+\\w\\W]+\\s";
		// ci
		String regex3 = "(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4})";
		// on off
		String regex4 = "/\\w+\\s*(on|off)\\s*\\d*\\s*;(^|\\s)/\\w*";
		// cmf
		String regex5 = "(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\W]+)){1,2}\\s*";
		// {colorchanges}
		String regex6 = "\\{[a-zA-Z+-]*\\}";
		//伏字
		String regex7 = "[\\\\\\{\\}\\|\\[\\]'\\(\\)<>#\\%*\\+\\-\\?_？ωー＿／＼]+";
		
		String regex8 = "^\\s";		
		String regex9 = "(^|\\s)?/\\w*";
		// String regex =
		// "^/[a-zA-Z]*;(^|\\s)/(m|s)pal?\\d;(^|\\s)/ci\\d+?((\\s(\\d+|nw|t\\d|s\\d+)){1,4});(^|\\s)/cmf?\\b\\w?;(^|\\s)/(f|m|c)?la\\s([_a-zA-Z0-9'-]+)(\\ss\\d)?;(^|\\s)/((ca?o?(s|mo?u?f(lage)?)\\w*)\\s([*＊・\\w]+)){1,2}\\s*;/\\w+\\s*(on|off)\\s*\\d*\\s*;(^|\\s)/\\w*;^\\s";
		// ^\";(^|\s)/ci\d+?((\s(\d+|nw|t\d|s\d+)){1,4});(^|\s)/cmf?\\b\\w?;(^|\s)/(f|m|c)?la\s([_a-zA-Z0-9'-]+)(\ss\d)?;(^|\s)/((ca?o?(s|mo?u?f(lage)?)\w*)\s([*＊・\w]+)){1,2}\s*;/\w+\s*(on|off)\s*\d*\s*;(^|\s)/\w*;^\s

		System.out.println(String.format("regex1: %s", text.replaceAll(regex1, "")));
		System.out.println(String.format("regex2: %s", text.replaceAll(regex2, "")));
		System.out.println(String.format("regex3: %s", text.replaceAll(regex3, "")));
		System.out.println(String.format("regex4: %s", text.replaceAll(regex4, "")));
		System.out.println(String.format("regex5: %s", text.replaceAll(regex5, "")));
		System.out.println(String.format("regex6: %s", text.replaceAll(regex6, "")));
		System.out.println(String.format("regex7: %s", text.replaceAll(regex7, "")));
		System.out.println(String.format("regex8: %s", text.replaceAll(regex8, "")));
		System.out.println(String.format("regex9: %s", text.replaceAll(regex9, "")));
		System.out.println(text.replaceAll(regex1, "").replaceAll(regex2, "").replaceAll(regex3, "")
				.replaceAll(regex4, "").replaceAll(regex5, "").replaceAll(regex6, "").replaceAll(regex7, "")
				.replaceAll(regex8, "").replaceAll(regex9, ""));

		System.out.println();
		System.out.println(String.format("regex1: %s", text2.replaceAll(regex1, "")));
		System.out.println(String.format("regex2: %s", text2.replaceAll(regex2, "")));
		System.out.println(String.format("regex3: %s", text2.replaceAll(regex3, "")));
		System.out.println(String.format("regex4: %s", text2.replaceAll(regex4, "")));
		System.out.println(String.format("regex5: %s", text2.replaceAll(regex5, "")));
		System.out.println(String.format("regex6: %s", text2.replaceAll(regex6, "")));
		System.out.println(String.format("regex8: %s", text2.replaceAll(regex8, "")));
		System.out.println(String.format("regex9: %s", text2.replaceAll(regex9, "")));
		System.out.println(text2.replaceAll(regex1, "").replaceAll(regex3, "").replaceAll(regex4, "")
				.replaceAll(regex5, "").replaceAll(regex6, "").replaceAll(regex2, "").replaceAll(regex8, "")
				.replaceAll(regex9, ""));

	}

}
