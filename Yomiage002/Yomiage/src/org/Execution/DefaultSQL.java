package org.Execution;

public class DefaultSQL {

	private String sql;
	private String prevDelete;
	private String updateTables;

	public DefaultSQL() {
		/**
		 * ＳＱＬファイルを別途で用意しておくので中身を知りたい場合はそちらを参照してください
		 * 
		*/
		this.prevDelete = "drop view referenceDataView;" 
				+ "drop table referencedata;"
				+ "create table RecoveryTable (\r\n" + 
				"	comments text,\r\n" + 
				"	flg number(1) default 0 not null,\r\n" + 
				"	priority number(100) not null\r\n" + 
				"	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n" + 
				"	constraint use_com_ork primary key (comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"insert into RecoveryTable (comments, flg, priority) select * from exceptionreferenceData;"
				+ "drop table exceptionreferencedata;";
		this.updateTables = "create table referenceData (\r\n" + 
				"	comments text,\r\n" + 
				"	totals text,\r\n" + 
				"	percent number(3,2) not null,\r\n" + 
				"	constraint use_com_prk primary key (comments)\r\n" + 
				");"
				+ "create table exceptionreferenceData (\r\n" + 
				"	comments text,\r\n" + 
				"	flg number(1) default 0 not null,\r\n" + 
				"	priority number(100) not null\r\n" + 
				"	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n" + 
				"	constraint use_com_ork primary key (comments)\r\n" + 
				");"
				+ "create view referenceDataView (comments, totals, percent) as\r\n" + 
				"select * \r\n" + 
				"	from\r\n" + 
				"		(\r\n" + 
				"			select a.comments, sum(b.counter) as totals, round(cast(a.counter as real) / sum(b.counter),10) as persent\r\n" + 
				"				from \r\n" + 
				"					countdata a,\r\n" + 
				"					countdata b\r\n" + 
				"				where\r\n" + 
				"					a.username = b.username\r\n" + 
				"				group by\r\n" + 
				"					a.comments\r\n" + 
				"		) t1\r\n" + 
				"	where\r\n" + 
				"		t1.persent > 0.0001 and\r\n" + 
				"		t1.totals > 7\r\n" + 
				"		/*t1.comments glob '/[a-zA-Z]*?*'*/\r\n" + 
				"	order by\r\n" + 
				"		t1.persent desc;"
				+ "insert into referenceData (comments, totals, percent)\r\n" + 
				"	select rf.comments, rf.totals, rf.percent * 100\r\n" + 
				"		from referencedataview rf\r\n" + 
				"	where \r\n" + 
				"		not exists (\r\n" + 
				"			select *\r\n" + 
				"				from referencedata\r\n" + 
				"		);"
				+ "insert into exceptionreferenceData (comments, flg, priority) select * from RecoveryTable;";
		this.sql = "/* データベースのテーブル作成用のデータ */\r\n" + 
				"\r\n" + 
				"drop table Natu_data;\r\n" + 
				"\r\n" + 
				"create table Natu_data(\r\n" + 
				"	tmstamp text,\r\n" + 
				"	number NUMERIC(5),\r\n" + 
				"	groups text not null,\r\n" + 
				"	sirial NUMERIC(8) not null,\r\n" + 
				"	username text not null,\r\n" + 
				"	comments text not null,\r\n" + 
				"	constraint tm_num_prk primary key (tmstamp, number),\r\n" + 
				"	constraint group_chk check (groups in ('PUBLIC', 'REPLY', 'GUILD', 'PARTY', 'GROUP'))\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"CREATE VIEW countData (\r\n" + 
				"	username,\r\n" + 
				"	comments,\r\n" + 
				"	counter\r\n" + 
				") as \r\n" + 
				"select username, comments, count(comments) as counter\r\n" + 
				"	from Natu_data\r\n" + 
				"		group by username, comments\r\n" + 
				"		having count(comments) > 1\r\n" + 
				"		order by counter;\r\n" + 
				"\r\n" + 
				"drop table referencedata;\r\n" + 
				"\r\n" + 
				"create table referenceData (\r\n" + 
				"	comments text,\r\n" + 
				"	totals text,\r\n" + 
				"	percent number(3,2) not null,\r\n" + 
				"	constraint use_com_prk primary key (comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"drop table exceptionreferencedata;\r\n" + 
				"\r\n" + 
				"create table exceptionreferenceData (\r\n" + 
				"	comments text,\r\n" + 
				"	flg number(1) default 0 not null,\r\n" + 
				"	priority number(100) not null\r\n" + 
				"	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n" + 
				"	constraint use_com_ork primary key (comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"/* \r\n" + 
				"解析データを簡易的に出力するＳＱＬ \r\n" + 
				"	統計データからのもので、そんな複雑な式は書いていない\r\n" + 
				"*/\r\n" + 
				"\r\n" + 
				"create view referenceDataView (comments, totals, percent) as\r\n" + 
				"select * \r\n" + 
				"	from\r\n" + 
				"		(\r\n" + 
				"			select a.comments, sum(b.counter) as totals, round(cast(a.counter as real) / sum(b.counter),10) as persent\r\n" + 
				"				from \r\n" + 
				"					countdata a,\r\n" + 
				"					countdata b\r\n" + 
				"				where\r\n" + 
				"					a.username = b.username\r\n" + 
				"				group by\r\n" + 
				"					a.comments\r\n" + 
				"		) t1\r\n" + 
				"	where\r\n" + 
				"		t1.persent > 0.0001 and\r\n" + 
				"		t1.totals > 5\r\n" + 
				"		/*t1.comments glob '/[a-zA-Z]*?*'*/\r\n" + 
				"	order by\r\n" + 
				"		t1.persent desc;\r\n" + 
				"\r\n" + 
				"/* データ挿入時の・・・sql(念のため) */\r\n" + 
				"\r\n" + 
				"insert into referenceData (comments, totals, percent)\r\n" + 
				"	select rf.comments, rf.totals, rf.percent * 100\r\n" + 
				"		from referencedataview rf\r\n" + 
				"	where \r\n" + 
				"		not exists (\r\n" + 
				"			select *\r\n" + 
				"				from referencedata\r\n" + 
				"		);"
				+ "create table RecoveryTable (\r\n" + 
				"	comments text,\r\n" + 
				"	flg number(1) default 0 not null,\r\n" + 
				"	priority number(100) not null\r\n" + 
				"	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n" + 
				"	constraint use_com_ork primary key (comments)\r\n" + 
				");";
	}

	// getter and setter
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getPrevDelete() {
		return prevDelete;
	}

	public void setPrevDelete(String prevDelete) {
		this.prevDelete = prevDelete;
	}

	public String getUpdateTables() {
		return updateTables;
	}

	public void setUpdateTables(String updateTables) {
		this.updateTables = updateTables;
	}
}
