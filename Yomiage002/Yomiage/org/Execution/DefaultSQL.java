package org.Execution;

public class DefaultSQL {

	private String sql;
	
	public DefaultSQL() {
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
				"create view referenceDataView (username, comments, percent) as \r\n" + 
				"	select b.username, b.comments, round(cast(b.counter as real) / cast(a.use_comments as real), 2) as num\r\n" + 
				"		from \r\n" + 
				"			countData b,\r\n" + 
				"			(\r\n" + 
				"				select count(comments) as use_comments, username\r\n" + 
				"					from Natu_data\r\n" + 
				"						group by username\r\n" + 
				"			) a\r\n" + 
				"		where \r\n" + 
				"			a.username = b.username\r\n" + 
				"		group by\r\n" + 
				"			b.username, b.comments\r\n" + 
				"		having \r\n" + 
				"			num < 1 and\r\n" + 
				"			num > 0.25;";
	}

	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
