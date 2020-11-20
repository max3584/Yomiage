package old.CLI.MenuData;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.CLI.ConsoleMenus;
//RunnableClass
import org.Readers.Directory.DirectoryUseSearch;
import org.Request.UserRequest;

import old.Execution.TodayReadExecution;

public class MenuData implements ConsoleMenus {

	private ArrayList<String> OptionBear;
	private HashMap<String, ArrayList<String>> menus;

	private ArrayList<Runnable> LocalClass;

	public MenuData() {
		// init Default data
		// init object instance
		this.OptionBear = new ArrayList<String>();
		this.menus = new HashMap<String, ArrayList<String>>();

		this.LocalClass = new ArrayList<Runnable>();

		// init datas
		String[] optionBears = { "File", "Option" };
		String[][] menus = { { "openFile", "exit" }, { "Profile Edit", "LeadOption", "FontSize" } };

		// inserts
		for (int i = 0; i < optionBears.length; i++) {
			this.OptionBear.add(optionBears[i]);
			this.menus.put(optionBears[i], new ArrayList<String>());
			for (String msg : menus[i]) {
				this.menus.get(optionBears[i]).add(msg);
			}
		}
		// end

		// localclass absrute!!!!!!
		/**
		 * めっちゃ長いです・・・(スパゲッティーコード化するため読み進め注意)
		 */

		this.LocalClass.add(new Runnable() {

			private String bear;
			private ArrayList<String> menuList;

			@Override
			public void run() {
				// init setup
				this.setBear("File");
				this.setMenuList(new ArrayList<String>());
				this.getMenuList().add("openFile");
				this.getMenuList().add("exit");

				UserRequest ur = new UserRequest();

				if (ur.UserInputRequest(">").equalsIgnoreCase(this.getMenuList().get(0))) {
					// 機能実装工程
					DirectoryUseSearch dus = new DirectoryUseSearch();
					String pso = dus.search();
					String bouyomi = dus.search();
					try {
						TodayReadExecution.main(pso, bouyomi);
					} catch (IndexOutOfBoundsException e) {
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
				} else {
					throw new RuntimeException("アプリケーションの終了命令");
				}
			}

			protected void setBear(String bear) {
				this.bear = bear;
			}

			protected String getBear() {
				return this.bear;
			}

			protected void setMenuList(ArrayList<String> menulist) {
				this.menuList = menulist;
			}

			protected ArrayList<String> getMenuList() {
				return this.menuList;
			}
		});
		
		this.LocalClass.add(new Runnable() {
			private String bear;
			private ArrayList<String> menuList;

			@Override
			public void run() {
				// init setup
				this.setBear("File");
				this.setMenuList(new ArrayList<String>());
				this.getMenuList().add("Profile Edit");
				this.getMenuList().add("LeadOption");
				this.getMenuList().add("FontSize");
				
				UserRequest ur = new UserRequest();
				// 機能実装工程
				
				String use = ur.UserInputRequest(">");
				if (use.equalsIgnoreCase(this.getMenuList().get(0))) {
					// Profile Edit
				}
				if (use.equalsIgnoreCase(this.getMenuList().get(1))){
					// LeadOptions
				}
				if (use.equalsIgnoreCase(this.getMenuList().get(2))) {
					// FontSize
				}
			}

			protected void setBear(String bear) {
				this.bear = bear;
			}

			protected String getBear() {
				return this.bear;
			}

			protected void setMenuList(ArrayList<String> menulist) {
				this.menuList = menulist;
			}

			protected ArrayList<String> getMenuList() {
				return this.menuList;
			}
		});

	}

	public MenuData(String[] optionBears, ArrayList<ArrayList<String>> menus, ArrayList<Runnable> commands) {
		// inserts
		for (int i = 0; i < optionBears.length; i++) {
			this.OptionBear.add(optionBears[i]);
			this.menus.put(optionBears[i], menus.get(i));
		}

		this.LocalClass = commands;
	}

	public MenuData(String[] optionBears, String[][] menus, Runnable[] commands) {
		// inserts
		for (int i = 0; i < optionBears.length; i++) {
			this.OptionBear.add(optionBears[i]);
			this.menus.put(optionBears[i], new ArrayList<String>());
			this.LocalClass.add(commands[i]);
			for (String msg : menus[i]) {
				this.menus.get(optionBears[i]).add(msg);
			}
		}
	}

	@Override
	public void PrintMenu() {
		String result = "";

		for (int i = 0; i < this.OptionBear.size(); i++) {
			result += String.format("[%s]\t", this.OptionBear.get(i));
		}
		result += "\r";
		System.out.println(result);
	}

	@Override
	public void CallMenu(String use) {

		String result = "";
		String tabs = "";

		this.PrintMenu();

		this.tabs(this.OptionBear.indexOf(use), tabs);

		for (int j = 0; j < this.menus.get(use).size(); j++) {
			result += String.format("\n%s%s", tabs, this.menus.get(use).get(j));
		}
		System.out.println(result);
	}

	protected String tabs(int num, String tab) {
		return num > 0 ? tabs(num - 1, tab += "\t") : tab;
	}

	// getter and setter
	public ArrayList<String> getOptionBear() {
		return OptionBear;
	}

	public void setOptionBear(ArrayList<String> optionBear) {
		OptionBear = optionBear;
	}

	public HashMap<String, ArrayList<String>> getMenus() {
		return menus;
	}

	public void setMenus(HashMap<String, ArrayList<String>> menus) {
		this.menus = menus;
	}

}
