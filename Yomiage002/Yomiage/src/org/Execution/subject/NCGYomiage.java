package org.Execution.subject;

import org.CLI.CEExpress;
/**
 * コメントジェネレータを使用しない場合のクラス
 * @author max
 *
 */
public class NCGYomiage extends Yomiage {

	@Override
	public void Request(int port, String user, String comment) {
		CEExpress cee = new CEExpress();
		cee.ConsoleCommand(port, String.format("%s %s", user, comment));
	}

}
