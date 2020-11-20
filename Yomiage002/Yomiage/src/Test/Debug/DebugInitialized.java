package Test.Debug;

import java.io.FileNotFoundException;
import java.io.IOException;

import old.Execution.Initialized;

public class DebugInitialized {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Initialized init = new Initialized(args[0]);
	}
}
