package org.Execution.subject;

import org.CLI.CEExpress;
import org.DataBase.DocumentDatabase;
import org.DataBase.NoSQLDocumentFormatXml;

public class CGYomiage extends Yomiage {

	private NoSQLDocumentFormatXml xml;

	public CGYomiage(NoSQLDocumentFormatXml xml) {
		this.xml = xml;
	}

	@Override
	public void Request(int port, String user, String comment) {
		String[] key = { "handle", "no", "owner", "service", "time" };
		String[] value = { user, "0", "0", "PSO2", String.valueOf(System.currentTimeMillis() / 1000) };
		this.xml.addNode(((DocumentDatabase) this.xml).getDocument().createElement("comment"), key, value, comment);
		CEExpress cee = new CEExpress();
		cee.ConsoleCommand(port, String.format("%s %s", user, comment));
	}

}
