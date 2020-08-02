package org.Execution;

public class DefaultSQL {

	private String sql;
	private String prevDelete;
	private String updateTables;
	
	public DefaultSQL() {
		this.prevDelete = "drop view referenceDataView;";
		this.updateTables = "create view referenceDataView (username, comments, percent) as\r\n" + 
				"select * \r\n" + 
				"	from\r\n" + 
				"		(\r\n" + 
				"			select a.username, a.comments, round(cast(a.counter as real) / sum(b.counter),4) as persent\r\n" + 
				"				from \r\n" + 
				"					countdata a,\r\n" + 
				"					countdata b\r\n" + 
				"				where\r\n" + 
				"					a.username = b.username\r\n" + 
				"				group by\r\n" + 
				"					a.username, a.comments\r\n" + 
				"		)\r\n" + 
				"	where\r\n" + 
				"		persent > 0.01 and\r\n" + 
				"		persent < 1;";
		this.sql = "\r\n" + 
				"/* データベースのテーブル作成用のデータ */\r\n" + 
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
				"create table referenceData (\r\n" + 
				"	username text,\r\n" + 
				"	comments text,\r\n" + 
				"	percent number(3,2) not null,\r\n" + 
				"	constraint use_com_prk primary key (username, comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"create table exceptionreferenceData (\r\n" + 
				"	username text,\r\n" + 
				"	comments text,\r\n" + 
				"	percent number(3,2) not null,\r\n" + 
				"	flg number(1) default 0 not null,\r\n" + 
				"	constraint flg_chk check(flg >= 0 and flg <= 1),\r\n" + 
				"	constraint use_com_ork primary key (username, comments)\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"/* 解析データを簡易的に出力するＳＱＬ \r\n" + 
				"	統計データからのもので、そんな複雑な式は書いていない\r\n" + 
				"*/\r\n" + 
				"\r\n" + 
				"create view referenceDataView (username, comments, percent) as\r\n" + 
				"select * \r\n" + 
				"	from\r\n" + 
				"		(\r\n" + 
				"			select a.username, a.comments, round(cast(a.counter as real) / sum(b.counter),4) as persent\r\n" + 
				"				from \r\n" + 
				"					countdata a,\r\n" + 
				"					countdata b\r\n" + 
				"				where\r\n" + 
				"					a.username = b.username\r\n" + 
				"				group by\r\n" + 
				"					a.username, a.comments\r\n" + 
				"		)\r\n" + 
				"	where\r\n" + 
				"		persent > 0.01 and\r\n" + 
				"		persent < 1;";
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
