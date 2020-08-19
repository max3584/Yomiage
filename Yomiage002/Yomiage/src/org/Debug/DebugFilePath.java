package org.Debug;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

public class DebugFilePath {

	public static void main(String[] args) throws FileAlreadyExistsException {
		System.out.println(System.getProperty("java.home"));
		String dir = System.getProperty("java.home");
		File run_java = new File(String.format("%s\\lib\\ext\\", dir));
		File pkg = new File(".\\lib\\sqlite-jdbc-3.27.2.1.jar");
		try {
			Files.copy(pkg.toPath(), run_java.toPath());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
