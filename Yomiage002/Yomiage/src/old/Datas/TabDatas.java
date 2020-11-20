package old.Datas;

public class TabDatas {
	
	//引数なしコンストラクタ
	public TabDatas() {

	}
	
	// タブ文字を入れるためのもの
	public String[] TabInsert(String line) {
		return line.split("\t");
	}
	
}
