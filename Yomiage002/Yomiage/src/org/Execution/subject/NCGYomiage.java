package org.Execution.subject;

import org.CLI.CEExpress;

public class NCGYomiage extends Yomiage {

	public NCGYomiage() {
		
	}

	@Override
	public void Request(int port, String user, String comment) {
		CEExpress cee = new CEExpress();
		cee.ConsoleCommand(port, String.format("%s %s", user, comment));
	}

}
