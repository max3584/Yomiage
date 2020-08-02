package org.CLI.MenuData.MenuExcute;

import java.util.ArrayList;

import org.CLI.MenuData.MenuData;
import org.Request.UserRequest;

public class MenuControler extends MenuData  {
	
	/**
	 * 標準入力
	 */
	private UserRequest stdIn;

	public MenuControler() {
		super();
		this.stdIn = new UserRequest();
	}
	
	public MenuControler(String[] optionBear, ArrayList<ArrayList<String>> menus, ArrayList<Runnable> localClass) {
		super(optionBear, menus, localClass);
		this.stdIn = new UserRequest();
	}
	
	public MenuControler(String[] optionBear, String[][] menus, Runnable[] localClass) {
		super(optionBear, menus, localClass);
		this.stdIn = new UserRequest();
	}
	
	@Override
	public void CallMenu(String menu) {
		super.CallMenu(menu);
		String use = stdIn.UserInputRequest(">");
		
	}
}
