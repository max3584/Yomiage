package org.CLI;

import java.util.ArrayList;
import java.util.HashMap;

public class Menus  implements ConsoleMenus{

	private HashMap<String, Object> menu;
	
	public Menus(HashMap<String, Object> menus) {
		this.menu = menus;
	}
	
	public Menus() {
		this.menu = new HashMap<String, Object>();
	}
	
	public void MenuInserts(String[] keys, Object[] values) {
		for (int i = 0; i < keys.length & i < values.length; i++)
			this.MenuInsert(keys[i], values[i]);
	}
	
	public void MenuInsert(String key, Object value) {
		this.menu.put(key, value);
	}

	public HashMap<String, Object> getMenu() {
		return menu;
	}

	public void setMenu(HashMap<String, Object> menu) {
		this.menu = menu;
	}

	@Override
	public void PrintMenu() {
		System.out.println("menus");
		
	}

	@Override
	public void CallMenu(String use) {
		
		System.out.println(this.menu.get(use) instanceof ArrayList ? "Array" : "CallMenu :");
	}
	
}
